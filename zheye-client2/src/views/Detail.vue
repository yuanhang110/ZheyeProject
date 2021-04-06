<template>
  <div id="detail">
    <!-- 头部 -->
    <div class="QuestionHeader">
      <div class="QuestionHeader-content">
        <div class="QuestionHeader-main">
          <div class="QuestionHeader-title">{{msg.title}}</div>
          <div class="QuestionHeader-detail">
            {{msg.summary}}
            <div class="QuestionHeader-more" @click="showMore" v-if="isShowMore&&msg.summary">
              <span>显示全部</span>
              <span class="iconfont icon-arrow-down"></span>
            </div>
          </div>
        </div>
        <!-- 侧边 -->
        <div class="QuestionHeader-side">
          <div class="QuestionStatus-item">
            <div class="QuestionStatus-name">关注者</div>
            <div class="QuestionStatus-num">1,203</div>
          </div>
          <div class="QuestionStatus-split"></div>
          <div class="QuestionStatus-item">
            <div class="QuestionStatus-name">被浏览</div>
            <div class="QuestionStatus-num">{{msg.lookNum}}</div>
          </div>
        </div>
      </div>
      <!-- 底部 -->
      <div class="QuestionHeader-footer">
        <div class="QuestionHeader-footer-inner">
          <div class="QuestionButton-groups">
            <button
              class="QuestionButton-follow"
              :class="{AttentionButton:msg.isFollow}"
              @click="focusQuestion"
            >{{followTip}}</button>
            <button class="QuestionButton-ask" @click="writeAnswer">
              <span class="iconfont icon-pen"></span>
              写回答
            </button>
          </div>
          <div class="QuestionHeader-actions">
            <div class="ActionsWrap">
              <span class="iconfont icon-zan1"></span>
              <span class="ActionsText">好问题</span>
            </div>
            <div class="ActionsWrap">
              <span class="iconfont icon-pinglun1"></span>
              <span class="ActionsText">评论</span>
            </div>
            <div class="ActionsWrap">
              <span class="iconfont icon-xiaofeiji"></span>
              <span class="ActionsText">分享</span>
            </div>
            <div class="ActionsWrap">
              <span class="iconfont icon-shenglvehao"></span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 编辑器 -->
    <div class="AnswerWrap" v-if="isAsk">
      <div class="AnswerHeader">
        <img :src="user.headUrl" alt class="AuthorAvator" />
        <span class="AuthorName">{{user.name}}</span>
      </div>
      <editor @change="contentChange" :isClear="isClear"></editor>
      <button class="AnswerButton" @click="submitAnswer">提交回答</button>
    </div>

    <!-- 回答 -->
    <div class="QuestionMain">
      <div class="List">
        <div class="ListHeader">{{msg.answerNum}}个回答</div>
        <div v-for="(item,index) in msg.answerList" :key="index">
          <question-list :answerList="item"></question-list>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import QuestionList from "@/components/QuestionList.vue";
import Editor from "@/components/Editor.vue";
import util from "@/utils/index.js";
let detail, id;
export default {
  name: "detail",
  components: {
    Editor,
    QuestionList
  },
  data() {
    return {
      followTip: "关注问题",
      user: {},
      msg: {},
      isShowMore: true,
      isAsk: false,
      eidtor: "",
      isClear: false
    };
  },
  mounted() {
    id = this.$route.params.id;
    this.getDetailMsg();
    this.user = util.getUser();
  },
  methods: {
    //获取数据
    getDetailMsg() {
      this.axios.get(`/question/detail?qid=${id}`).then(res => {
        if (res.status == 200) {
          this.msg = res.data;
          detail = res.data.detail;
          if (res.data.isFollow) {
            this.followTip = "已关注";
          }
        }
      });
    },
    //监听编辑器
    contentChange(data) {
      console.log(data);
      this.eidtor = data;
    },
    //显示更多
    showMore() {
      this.summary = detail;
      this.isShowMore = false;
    },
    //写回答
    writeAnswer() {
      if (util.isLogin()) {
        this.isAsk = true;
      } else {
        this.$alert("请先登录", "提示");
      }
    },
    //提交回答
    submitAnswer() {
      let params = {
        content: this.eidtor,
        qid: id
      };
      this.axios.post("/answer/add", params).then(res => {
        if (res.status == 200) {
          this.$message({
            message: "回答成功",
            type: "success"
          });
          this.isClear = true;
          this.getDetailMsg();
        }
      });
    },
    //关注问题
    focusQuestion() {
      if (this.msg.isFollow) {
        this.axios.get(`/follow/question_cancel?id=${this.msg.id}`).then(res => {
          if (res.status == 200) {
            this.msg.isFollow = !this.msg.isFollow;
            this.followTip="关注问题"
          }
        });
      } else {
        this.axios.get(`/follow/question?id=${this.msg.id}`).then(res => {
          if (res.status == 200) {
            this.msg.isFollow = !this.msg.isFollow;
            this.followTip="已关注"
          }
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
@import "../assets/css/config";
// 头部
.QuestionHeader {
  width: 100%;
  background: #ffffff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.1);
  padding: 16px 0;
  &-content {
    display: flex;
    justify-content: space-between;
    width: 1000px;
    height: 100%;
    padding: 0 16px;
    margin: 0 auto;
  }
  &-main {
    width: 694px;
  }
  &-title {
    margin-top: 12px;
    margin-bottom: 4px;
    font-size: 22px;
    font-weight: 600;
    color: #1a1a1a;
  }
  &-detail {
    cursor: pointer;
    font-size: 15px;
  }
  &-more {
    display: inline-block;
    font-size: 14px;
    color: $fontColor;
  }
  &-side {
    display: flex;
    width: 296px;
    justify-content: flex-end;
    .QuestionStatus-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 0 20px;
      .QuestionStatus-name {
        font-size: 14px;
        color: $fontColor;
      }
      .QuestionStatus-num {
        font-size: 18px;
        font-weight: 600;
      }
    }
    .QuestionStatus-split {
      width: 1px;
      background: #ebebeb;
      height: 50px;
    }
  }
  &-footer {
    margin-top: 8px;
    &-inner {
      display: flex;
      align-items: center;
      width: 1000px;
      height: 100%;
      padding: 0 16px;
      margin: 0 auto;
    }
    .QuestionButton-groups {
      display: flex;
      align-items: center;
      .QuestionButton-follow {
        cursor: pointer;
        color: #ffffff;
        background: $mainColor;
        padding: 0 16px;
        line-height: 34px;
        height: 34px;
        margin-right: 10px;
        border: 1px solid $mainColor;
      }
      .AttentionButton {
        background: $fontColor;
        border: 1px solid $fontColor;
      }
      .QuestionButton-ask {
        cursor: pointer;
        padding: 0 16px;
        line-height: 34px;
        height: 34px;
        border: 1px solid $mainColor;
        background: #ffffff;
        color: $mainColor;
        &:hover {
          background: #e8f3ff;
        }
      }
      .icon-pen {
        font-size: 14px;
      }
    }
    .QuestionHeader-actions {
      display: flex;
      margin-left: 12px;
      .ActionsWrap {
        cursor: pointer;
        font-size: 14px;
        color: $fontColor;
        padding: 0 8px;
        &:hover {
          color: #677081;
        }
      }
      .ActionsText {
        margin-left: 4px;
      }
    }
  }
}
// 编辑器
.AnswerWrap {
  width: 1000px;
  margin: 10px auto;
  padding: 0 16px 60px 16px;
  background: #ffffff;
  .AnswerHeader {
    display: flex;
    align-items: center;
    font-size: 15px;
    font-weight: 600;
    padding: 16px 0;
    .AuthorAvator {
      margin: 0 10px;
      width: 30px;
      height: 30px;
    }
  }
  .AnswerButton {
    margin-top: 8px;
    padding: 0 16px;
    background: $mainColor;
    color: #ffffff;
    line-height: 36px;
    float: right;
    cursor: pointer;
  }
}
// 回答
.QuestionMain {
  width: 1000px;
  margin: 10px auto;
  padding: 0 16px;
  background: #ffffff;
  .List {
    padding: 0 20px;
    .ListHeader {
      height: 50px;
      border-bottom: 1px solid #f6f6f6;
      line-height: 50px;
      font-weight: 600;
    }
    .ListItem {
      padding: 16px 0;
      border-bottom: 1px solid #ebebeb;
    }
  }
  
}


</style>

