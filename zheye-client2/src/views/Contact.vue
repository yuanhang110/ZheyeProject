<template>
  <div>
    <nav-header ref="header" @toChat="chooseUser"></nav-header>
    <div class="ContactWrap">
      <div class="Contact">
        <div class="ContactSide">
          <div class="ContactSide-tip">联系人</div>
          <div v-for="(item,index) in userList" :key="index">
            <div
              class="ContactItem"
              @click="chooseUser(item.user,index)"
              :class="{ChooseItem : item.user.id == currentUser.id}"
            >
              <!-- 红点提示 -->
              <el-badge :value="item.unreadNum" :max="99" :hidden="item.unreadNum==0">
                <img class="UserAvator" :src="item.user.headUrl" alt="头像" />
              </el-badge>
              <div class="UserContent">
                <div class="UserMsg">
                  <span class="UserName">{{item.user.name}}</span>
                  <span class="MsgTime">{{item.time}}</span>
                </div>
                <div class="UserSnippet">{{item.content}}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="ContactBox">
          <div v-show="currentUser.id!=0">
            <div class="ContactBox-header">{{currentUser.name}}</div>
            <div class="MessageBox" ref="MessageBox">
              <div
                v-for="(item,index) in chatList"
                :key="index"
                class="Message"
                :style="item.fromUser.id == uid?'flex-direction:row-reverse':''"
              >
                <img :src="item.fromUser.headUrl" class="UserAvator" />
                <div class="UserMsg" :class="item.fromUser.id == uid?'RightMessage':'LeftMessage'">
                  <span :style="item.fromUser.id == uid?' float: right;':''">{{item.content}}</span>
                </div>
              </div>
            </div>
            <!-- 输入框 -->
            <div class="InputBox">
              <textarea v-model="msg" class="InputTextarea" rows="3" @keyup.enter="send"></textarea>
              <div class="InputBox-footer">
                <div class="FooterDesc">按Enter键发送</div>
                <button class="sendButton" @click="send">发送</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import NavHeader from "@/components/NavHeader.vue";

import util from "@/utils/index.js";
import { mapGetters, mapState } from "vuex";

export default {
  name: "contact",
  components: {
    NavHeader
  },
  data() {
    return {
      uid: 0,
      msg: "", //聊天信息
      wss: null,
      chatList: [], //聊天记录
      userList: [],
      currentPage: 1,
      currentUser: {
        id: 0
      } //当前聊天的对象
    };
  },
  computed: {
    ...mapGetters(["wsMsg", "ws"])
  },
  watch: {
    wsMsg: function(newVal) {
      if (newVal.command == 4) {
        console.log(newVal);
        this.clearMsg();
        if (
          newVal.fromUser.id == util.getUser().id ||
          this.currentUser.id == newVal.fromUser.id
        ) {
          console.log("更新聊天记录");
          this.chatList = this.chatList.concat(newVal);
        }
        setTimeout(() => {
          this.scrollBottm();
        }, 200);
      }
    },
    ws: function(newVal) {
      console.log(newVal);
      this.wss = newVal;
    }
  },
  mounted() {
    this.uid = util.getUser().id;
    this.wss = this.$store.state.ws;
    if (this.$route.params.user) {
      this.chooseUser(this.$route.params.user);
    }
    this.listenScroll();
    this.getUserList();
  },
  methods: {
    /**加载聊天记录
     * @param page 聊天页数
     * @param type 类别 1代表初次获取聊天记录 2代表加载聊天记录
     **/
    getChatList(page, type) {
      this.axios
        .get(`/chat_record?chatUserId=${this.currentUser.id}&page=${page}`)
        .then(res => {
          console.log(res);
          if (res.status == 200) {
            this.chatList = this.chatList.concat(res.data);
            if (type == 1) {
              this.$nextTick(() => {
                this.scrollBottm();
              });
            }
          }
        });
    },
    //发送信息
    send() {
      let msg = {
        toUserId: this.currentUser.id,
        content: this.msg,
        time: util.getTime(),
        command: 3
      };
      this.wss.send(util.encode(msg));
      this.msg = "";
      this.clearMsg();
      setTimeout(() => {
        this.scrollBottm();
      }, 200);
    },
    //选择联系人
    chooseUser(user, index) {
      console.log(user);
      this.currentUser = user;
      this.chatList = [];
      this.getChatList(1, 1);
      this.clearMsg();
      this.$refs["header"].getChatList();
    },
    //滚动条滚动到底部
    scrollBottm() {
      let el = this.$refs["MessageBox"];
      el.scrollTop = el.scrollHeight;
    },
    //监听滚动条
    listenScroll() {
      let ele = this.$refs["MessageBox"];
      let that = this;
      ele.addEventListener("scroll", function() {
        let scr = ele.scrollTop; // 向上滚动的那一部分高度
        let clientHeight = ele.clientHeight; // 屏幕高度也就是当前设备静态下你所看到的视觉高度
        let scrHeight = ele.scrollHeight; // 整个网页的实际高度，兼容Pc端
        if (scr == 0) {
          console.log("到顶了");
          that.getChatList(that.currentPage++, 2);
        }
      });
    },
    //获取联系人列表
    getUserList() {
      this.axios.get("chatList").then(res => {
        if (res.status == 200) {
          res.data.sort(util.sortByAttr("time"));
          res.data.map(item => {
            item.time = util.changeTime(item.time);
          });
          this.userList = res.data;
        }
      });
    },
    //清除未读消息
    clearMsg() {
      this.axios.get(`/clear_unread_num?fromUserId=${this.currentUser.id}`);
      this.getUserList();
      
    },
    //更新userlist
    updateUserList(newVal) {
      for (index in this.userList) {
        if (this.userList[index].user.id == newVal.fromUser.id) {
          //找到了发消息的人
          item.unreadNum = newVal.unreadNum;
          item.content = newVal.content;
          item.time = util.changeTime(newVal.time);
          break;
        }
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.Contact {
  height: 614px;
  width: 1002px;
  background-color: #fff;
  border: 1px solid #ebebeb;
  box-shadow: 0 0 4px 0 rgba(26, 26, 26, 0.1);
  border-radius: 3px;
  margin: 8px auto 0;
  display: flex;
  //联系人
  .ContactSide {
    width: 286px;
    height: 100%;
    border-right: 1px solid #ebebeb;
    .ContactSide-tip {
      height: 40px;
      line-height: 40px;
      font-weight: 600;
      padding: 0 30px;
      border-bottom: 1px solid #ebebeb;
    }
  }
  .ContactItem {
    padding: 12px 20px 12px 29px;
    cursor: pointer;
    display: flex;
    border-bottom: 1px solid #f7f8fa;
    .UserContent {
      flex: 1;
      .UserMsg {
        display: flex;
        align-items: center;
        justify-content: space-between;
        .UserName {
          font-size: 15px;
          columns: #444444;
          font-weight: 600;
        }
        .MsgTime {
          font-size: 12px;
          color: #999999;
          float: right;
        }
      }
    }
  }
  .ChooseItem {
    background: #f5f4f4;
  }
  .UserAvator {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
  }
  .ContactBox {
    width: 710px;
    &-header {
      font-size: 15px;
      margin: 0 14px;
      height: 20px;
      padding-bottom: 9px;
      padding-top: 21px;
      border-bottom: 1px solid #ebebeb;
      font-weight: 600;
      text-align: center;
    }
    //聊天框
    .MessageBox {
      height: 362px;
      overflow: scroll;
      .Message {
        display: flex;
        margin: 20px;
        .UserMsg {
          max-width: 388px;
          border-radius: 8px;
          padding: 6px 12px;
          font-size: 14px;
          position: relative;
          margin: 0 8px;
          text-align: left;
          white-space: pre-wrap;
          word-break: break-all;
        }
        .LeftMessage {
          background-color: #f6f6f6;
          color: #444;
          &::after {
            content: "";
            position: absolute;
            width: 8px;
            height: 8px;
            left: -4px;
            top: 14px;
            background: #f6f6f6;
            -webkit-transform: rotate(45deg);
            transform: rotate(45deg);
          }
        }
        .RightMessage {
          background-color: #0084ff;
          color: #fff;
          &::after {
            content: "";
            position: absolute;
            width: 8px;
            height: 8px;
            right: -4px;
            top: 14px;
            background: #0084ff;
            -webkit-transform: rotate(45deg);
            transform: rotate(45deg);
          }
        }
      }
    }
    //输入框
    .InputBox {
      padding: 0 10px;
      border-top: 1px solid #ebebeb;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      height: 170px;
      background: #fff;
      z-index: 10;
      .InputTextarea {
        margin-top: 20px;
        width: 100%;
        border: none;
        font-size: 14px;
        flex: 1;
      }
      &-footer {
        display: flex;
        align-items: center;
        justify-content: flex-end;
        height: 50px;
        .FooterDesc {
          font-size: 14px;
          color: #bfbfbf;
          padding-right: 10px;
        }
        .sendButton {
          color: #fff;
          background-color: #0084ff;
          border-radius: 6px;
          width: 72px;
          height: 32px;
          font-size: 13px;
          line-height: 32px;
          text-align: center;
        }
      }
    }
  }
}
</style>