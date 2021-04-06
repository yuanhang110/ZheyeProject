  <!-- 顶部导航 -->
<template>
  <div class="AppHeader">
    <div class="AppHeader-inner">
      <p class="webfont">哲也</p>
      <!-- 导航栏 -->
      <ul class="AppHeader-tabs">
        <li class="Tabs-item">
          <router-link to="/" replace class="Tabs-link isActive">首页</router-link>
        </li>
      </ul>
      <!-- 搜索框 -->
      <div class="SearchBar" :class="{IsFocus:isFocus}">
        <input
          @focus="inputFocus"
          @blur="inputBlur"
          type="text"
          class="SearchBar-input"
          placeholder="计算机学生应该如何学习"
          @keyup.enter="search"
          v-model="searchItem"
        />
        <span class="iconfont icon-icon161603" :class="{Icons:isFocus}"></span>
      </div>
      <button
        class="AskButton"
        :class="{HideButton:isFocus}"
        @click="showModal=true"
        v-show="isLogin"
      >提问</button>
      <!-- 用户区域 -->
      <div class="AppHeader-userInfo">
        <div class="AppHeader-msg">
          <el-popover
            v-if="isLogin"
            placement="bottom"
            width="360"
            trigger="click"
            popper-class="PriviteMessage-wrap"
          >
            <div class="PriviteMessage">
              <div class="MessageTab">我的私信</div>
              <div class="MessageList">
                <div v-for="(item,index) in priviteMsg" :key="index">
                  <div class="MessageItem" @click="toChat(item.user,index)">
                    <el-badge :value="item.unreadNum" :max="99" :hidden="item.unreadNum==0">
                      <img :src="item.user.headUrl" alt class="Avatar" />
                    </el-badge>
                    <div class="MessageUsers">
                      <div class="MessageUsers-name">{{item.user.name}}</div>
                      <div class="MessageUsers-content">{{item.content}}</div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="MessageFooter">
                <router-link to="/contact">查看全部私信</router-link>
              </div>
            </div>
            <el-badge :value="totalNum" :max="99" slot="reference" :hidden="totalNum==0">
              <span class="iconfont icon-weibiaoti-_fuzhi"></span>
            </el-badge>
          </el-popover>
        </div>
        <div class="AppHeader-profile">
          <el-popover v-if="isLogin" placement="bottom" width="150" trigger="click">
            <div class="Quit" @click="quitLogin">退出登录</div>
            <img slot="reference" :src="user.headUrl" class="AppHeader-profileAvatar" />
          </el-popover>
          <button v-else class="LoginButton" @click="showLoginModal=true">登录 / 注册</button>
        </div>
      </div>
    </div>
    <!-- 提问遮罩层 -->
    <modal :showModal="showModal" @cancel="showModal = false">
      <template v-slot:dialog>
        <div class="QuesModal-content">
          <!-- 回答问题区域 -->
          <div class="Ask-item">
            <img
              src="http://img2.imgtn.bdimg.com/it/u=1354268575,1268995723&fm=26&gp=0.jpg"
              class="Avatar"
            />
            <div class="AskTitle-wrap">
              <textarea
                @input="listenChange"
                v-model="askTitle"
                class="AskTitle"
                rows="3"
                placeholder="写下你的问题，准确地描述问题更容易得到解答"
              ></textarea>
              <div class="AskFieldTip">{{askTip}}</div>
            </div>
          </div>
          <textarea class="AskDetial" placeholder="输入问题背景、条件等详细信息（选填）" v-model="askDetail"></textarea>
          <!-- 发布问题 -->
          <button class="AskButton" :disabled="disabled" @click="publishQues">发布问题</button>
        </div>
      </template>
    </modal>
    <!-- 登录遮罩层 -->
    <modal :showModal="showLoginModal" @cancel="showLoginModal = false">
      <template v-slot:dialog>
        <div class="Login-wrap">
          <el-tabs v-model="activeName">
            <el-tab-pane label="立即注册" name="register">
              <div class="Sign-wrap">
                <input class="SignInput" type="text" placeholder="用户名" v-model="regUser" />
              </div>
              <div class="Sign-wrap">
                <input class="SignInput" type="password" placeholder="密码" v-model="regPass" />
              </div>
              <div class="RemeberMe"></div>
              <button class="SubmitButton" @click="register">注册</button>
            </el-tab-pane>
            <el-tab-pane label="密码登陆" name="login">
              <div class="Sign-wrap">
                <input class="SignInput" type="text" placeholder="用户名" v-model="loginUser" />
              </div>
              <div class="Sign-wrap">
                <input class="SignInput" type="password" placeholder="密码" v-model="loginPass" />
              </div>
              <div class="RemeberMe">
                <el-checkbox v-model="checked">7天内免登录</el-checkbox>
              </div>
              <button class="SubmitButton" @click="login">登陆</button>
            </el-tab-pane>
          </el-tabs>
        </div>
      </template>
    </modal>
  </div>
</template>
<script>
import Modal from "@/components/Modal.vue";
import util from "@/utils/index.js";
import { mapGetters } from "vuex";

export default {
  name: "nav-header",
  components: {
    Modal
  },
  data() {
    return {
      user: "",
      regUser: "", //注册手机号
      regPass: "", //注册密码
      loginUser: "", //登录用户
      loginPass: "", //注册密码
      isFocus: false,
      showModal: false,
      showLoginModal: false,
      disabled: true,
      askTip: "",
      askTitle: "", //提问问题
      askDetail: "", //提问细节
      isLogin: true,
      activeName: "login", //elmentui
      checked: false, //elmentui
      priviteMsg: [], //私信消息
      searchItem: "", //搜索内容
      totalNum: 0 //总未读数
    };
  },
  computed: {
    ...mapGetters(["wsMsg", "totalUnread"])
  },
  watch: {
    isLogin: function(newVal) {
      if (newVal) {
        this.getChatList();
      }
    },
    wsMsg: function(newVal) {
      if (newVal.command == 4) {
        console.log(newVal);
        this.getChatList();
      }
    },
  },
  mounted() {
    this.isLogin = util.isLogin();
    this.user = util.getUser();
    if (this.isLogin) {
      this.getChatList();
    }
  },
  methods: {
    //注册
    register() {
      let params = {
        password: this.regPass,
        username: this.regUser
      };
      if (this.regUser && this.regPass) {
        this.axios.post("/reg", params).then(res => {
          if (res.status == 200) {
            this.$message({
              message: "恭喜你，注册成功，赶紧登陆吧",
              type: "success"
            });
          }
          this.regUser = "";
          this.regPass = "";
          this.activeName = "login";
        });
      } else {
        this.$alert("请填写用户名和密码", "提示");
      }
    },
    //登录
    login() {
      let params = {
        username: this.loginUser,
        password: this.loginPass,
        rememberme: this.checked
      };
      this.axios.post("/login", params).then(res => {
        if (res.status == 200) {
          this.$message({
            message: "登录成功",
            type: "success"
          });
          localStorage.setItem("user", JSON.stringify(res.data.user));
          this.user = res.data.user;
          this.showLoginModal = false;
          this.isLogin = true;
        }
      });
    },
    //退出登录
    quitLogin() {
      this.axios.get("/layout").then(res => {
        if (res.status == 200) {
          this.$message({
            message: "退出成功",
            type: "success"
          });
          this.isLogin = false;
        }
      });
    },
    //监听input选中
    inputFocus() {
      this.isFocus = true;
    },
    inputBlur() {
      this.isFocus = false;
    },
    //监听标题文字数量
    listenChange() {
      if (this.askTitle.length < 4) {
        this.askTip = "至少输入4个字";
        this.disabled = true;
      } else {
        this.askTip = "";
        this.disabled = false;
      }
    },
    //发表问题
    publishQues() {
      let params = {
        title: this.askTitle,
        content: this.askDetail
      };
      this.axios.post("/question/add", params).then(res => {
        if (res.status == 200) {
          this.$message({
            message: "提问成功",
            type: "success"
          });
          this.showModal = false;
          this.askTitle = "";
          this.askDetail = "";
          this.$store.dispatch("setIsAdd", true);
        }
      });
    },
    //搜索问题
    search() {
      this.axios.get(`search?search=${this.searchItem}`).then(res => {
        if (res.status == 200) {
          this.$store.dispatch("search", this.searchItem);
          this.$store.dispatch("setSearchList", res.data);
          this.searchItem = "";
        }
      });
    },
    //获取聊天列表
    getChatList() {
      console.log("首页刷新列表");
      this.axios.get("chatList").then(res => {
        if (res.status == 200) {
          res.data.sort(util.sortByAttr("time"));
          let totalUnread = 0;
          res.data.forEach(item => {
            totalUnread += item.unreadNum;
          });
          this.priviteMsg =res.data;
          this.totalNum = totalUnread;
        }
      });
    },
    //跳转私信
    toChat(user, index) {
      this.priviteMsg[index].unreadNum = 0;
      if (this.$route.name != "contact") {
        this.$router.push({
          name: "contact",
          params: {
            user: user
          }
        });
      } else {
        //在concat页面
        this.$emit("toChat", user);
      }
    }
  }
};
</script>
<style lang='scss' >
@import "../assets/css/config";
.AppHeader {
  position: sticky;
  height: 52px;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  background: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.1);
  &-inner {
    display: flex;
    width: 1000px;
    height: 52px;

    padding: 0 16px;
    margin: 0 auto;
    align-items: center;
  }
  &-tabs {
    display: flex;
    margin: 0 23px;
    .Tabs-item {
      position: relative;
    }
    .Tabs-link {
      padding: 14px 0;
      text-align: center;
    }
    .isActive {
      color: #444;
      font-weight: 600;
    }
    .isActive::after {
      display: block;
      position: absolute;
      right: 0;
      bottom: -14px;
      left: 0;
      height: 3px;
      background: $mainColor;
      content: "";
    }
  }
  // 搜索提问
  .SearchBar {
    color: $fontColor;
    background: #f6f6f6;
    padding: 4px 10px;
    width: 312px;
    font-size: 14px;
    display: flex;
    border: 1px solid #ebebeb;
    align-items: center;
    border-radius: 5px;
    margin-left: 30px;
    transition: width 0.4s ease, background 0.3s ease;
    &-input {
      background: inherit;
      border: none;
      flex: 1;
      height: 24px;
      padding-left: 12px;
      font-size: 15px;
    }
    .icon-icon161603 {
      font-size: 18px;
    }
    .Icons {
      color: $mainColor;
    }
  }
  .IsFocus {
    border: 1px solid #1a1a1a;
    background: #fff;
    width: 380px;
  }
  .AskDetial {
    min-height: 78px;
    border: 1px solid #ebebeb;
    padding: 6px 12px;
    font-size: 14px;
  }
  .AskButton {
    background: $mainColor;
    color: #ffffff;
    font-size: 14px;
    cursor: pointer;
    border-radius: 3px;
    padding: 6px 14px;
    margin-left: 16px;
    transition: display 0.3s linear 0.3;
  }
  .HideButton {
    display: none;
  }
  // 用户
  &-userInfo {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .AppHeader-profile {
      display: flex;
      align-items: center;
      .AppHeader-profileAvatar {
        width: 30px;
        height: 30px;
        border-radius: 2px;
        margin-left: 30px;
      }
    }
    .AppHeader-msg {
      position: relative;
      font-size: 22px;
      color: $fontColor;
    }

    .LoginButton {
      font-size: 14px;
      padding: 6px 12px;
      color: $mainColor;
      background: #fff;
      border: 1px solid $mainColor;
      &:hover {
        cursor: pointer;
        background: rgba(0, 132, 255, 0.1);
      }
    }
  }
  .webfont {
    color: $mainColor;
  }
  //提问
  .QuesModal-content {
    width: 536px;
    padding: 24px 32px;
    display: flex;
    flex-direction: column;
    .Ask-item {
      display: flex;
      .Avatar {
        border-radius: 3px;
        width: 40px;
        height: 40px;
        margin: 3px 16px 0 0;
      }
      .AskTitle-wrap {
        flex: 1;
        min-height: 90px;
        .AskTitle {
          width: 100%;
          border: none;
          font-size: 18px;
          color: #858fa6;
        }
        .AskFieldTip {
          text-align: right;
          color: #f1403c;
          font-size: 15px;
        }
      }
    }

    .AskButton {
      color: #fff;
      background-color: #0084ff;
      width: 90px;
      padding: 8px 16px;
      font-size: 14px;
      margin-top: 10px;
      align-self: flex-end;
      &:disabled {
        opacity: 0.5;
      }
    }
  }
  // 登录
  .Login-wrap {
    width: 400px;
    padding: 0 24px 30px;
    .el-tabs__nav-wrap::after {
      background-color: transparent !important;
    }
    .el-tabs__active-bar {
      background: $mainColor;
      height: 3px;
    }
    .el-tabs__item {
      font-size: 16px;
      color: #444;
    }
    .el-tabs__item.is-active {
      color: #1a1a1a;
      font-weight: 600;
    }
    .Sign-wrap {
      width: 100%;
      height: 36px;
      padding: 12px;
      border-bottom: 1px solid #ebebeb;
      .SignInput {
        width: 96%;
        border: none;
        height: 46px;
        font-size: 14px;
      }
    }
    .RemeberMe {
      margin-top: 14px;
      text-align: right;
      .el-checkbox__input.is-checked + .el-checkbox__label {
        color: $mainColor;
      }
    }
    .SubmitButton {
      width: 100%;
      background: $mainColor;
      color: #fff;
      height: 36px;
      line-height: 36px;
      margin-top: 30px;
      font-size: 14px;
      border-radius: 4px;
    }
  }
}
.Quit {
  text-align: center;
  color: $fontColor;
  cursor: pointer;
}
//私信
.PriviteMessage-wrap {
  padding: 0 !important;
}
.PriviteMessage {
  width: 360px;
  height: 429px;
  z-index: 10;
  font-size: 14px;
  .MessageTab {
    width: 100%;
    height: 38px;
    line-height: 38px;
    text-align: center;
    border-bottom: #ebebeb solid 1px;
    background: #ffffff;
    cursor: pointer;
    z-index: 11;
  }
  .MessageList {
    height: 350px;
    overflow: scroll;
  }
  .MessageItem {
    display: flex;
    align-items: center;
    padding: 12px;
    cursor: pointer;
    // overflow: scroll;
    .Avatar {
      width: 40px;
      height: 40px;
      margin-right: 10px;
      border-radius: 6px;
    }
    .MessageUsers {
      margin-left: 10px;
      .MessageUsers-name {
        font-size: 15px;
      }
      .MessageUsers-content {
        color: $fontColor;
      }
    }

    &:hover {
      background: #ebebeb;
    }
  }
  .MessageFooter {
    width: 100%;
    height: 38px;
    line-height: 38px;
    text-align: center;
    border-top: #ebebeb solid 1px;
    background: #ffffff;
    cursor: pointer;
  }
}
</style>