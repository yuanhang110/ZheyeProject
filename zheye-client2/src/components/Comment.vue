<!-- 评论功能 -->
<template>
  <div class="Comment">
    <div class="CommentTopBar">{{commentList.totalNum}}条评论</div>
    <div class="CommentItem" v-for="(item,index) in commentList.data" :key="index">
      <!-- 评论头 -->
      <div class="CommentItem-meta">
        <!-- 个人信息 -->
            <user-card :user="item.user"></user-card>
        <span class="CommentItem-name">{{item.user.name}}</span>
        <span class="CommentItem-time">{{item.time}}</span>
      </div>
      <!-- 评论内容 -->
      <comment-body :commentMsg="item" @addReply="addReply"></comment-body>
      <template v-if="item.replies.data.length>0">
        <div
          class="CommentItem-reply"
          v-for="(reply,replyIndex) in item.replies.data"
          :key="replyIndex"
        >
          <!-- 回复头 -->
          <div class="CommentItem-meta">
            <!-- 个人信息 -->
            <user-card :user="reply.user"></user-card>
            <div class="CommentReply-users">
              <span class="CommentReply-name">{{reply.user.name}}</span>
              回复
              <span class="CommentReply-name">{{reply.responder.name}}</span>
            </div>
            <span class="CommentItem-time">{{reply.time}}</span>
          </div>
          <!-- 评论内容 -->
          <comment-body :commentMsg="reply" @addReply="addReply"></comment-body>
        </div>
      </template>
    </div>
    <!-- 分页 -->
    <div class="PaginationWrap">
      <el-pagination
        layout="prev, pager, next"
        :total="commentList.totalNum"
        :hide-on-single-page="true"
        @current-change="changePage"
      ></el-pagination>
    </div>
    <div class="Comment-footer">
      <el-input type="textarea" resize="none" autosize placeholder="写下你的评论……" v-model="comment"></el-input>
      <button class="CommentReply-button" :disabled="comment.length==0" @click="publishComment">发布</button>
    </div>
  </div>
</template>
<script>
import CommentBody from "@/components/CommentBody.vue";
import UserCard from "@/components/UserCard.vue";

export default {
  name: "comment",
  components: {
    CommentBody,
    UserCard
  },
  props: {
    id: Number
  },
  data() {
    return {
      commentList: [
        {
          name: "英雄登场",
          time: "04-11",
          commentMsg: {
            id: 1,
            name: "英雄登场",
            content: "你在说什么玩意",
            likeNum: "12",
            isLike: true,
            isDislike: false
          },
          replys: [
            {
              name: "回复的人",
              responded: "被回复的人",
              time: "04-11",
              commentMsg: {
                id: 2,
                name: "回复的人",
                content: "你管我说什么玩意",
                likeNum: "0",
                isLike: false,
                isDislike: false
              }
            }
          ]
        },
        {
          name: "英雄登场",
          time: "04-11",
          commentMsg: {
            id: 3,
            name: "英雄登场",
            content: "你在说什么玩意",
            likeNum: "12",
            isLike: true,
            isDislike: false
          },
          replys: [
            {
              name: "回复的人",
              responded: "被回复的人",
              time: "04-11",
              commentMsg: {
                id: 4,
                name: "回复的人",
                content: "你管我说什么玩意",
                likeNum: "0",
                isLike: false,
                isDislike: false
              }
            }
          ]
        },
        {
          name: "英雄登场",
          time: "04-11",
          commentMsg: {
            id: 5,
            name: "英雄登场",
            content: "你在说什么玩意",
            likeNum: "12",
            isLike: true,
            isDislike: false
          },
          replys: [
            {
              name: "回复的人",
              responded: "被回复的人",
              time: "04-11",
              commentMsg: {
                name: "回复的人",
                id: 6,
                content:
                  "你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意你管我说什么玩意",
                likeNum: "12",
                isLike: true,
                isDislike: false
              }
            }
          ]
        }
      ],
      comment: "",
      currentPage: 1
    };
  },
  mounted() {
    this.getComment(this.currentPage);
  },
  methods: {
    //获取评论
    getComment(page) {
      this.axios
        .get(`/comment/list?id=${this.id}&type=${1}&page=${page}`)
        .then(res => {
          console.log(res);
          this.commentList = res.data;
        });
    },
    //改变评论页面
    changePage(pageNum) {
      this.currentPage = pageNum;
      this.getComment(this.currentPage);
    },
    //回复评论内容
    addReply() {
      this.getComment(this.currentPage);
    },
    //发表评论
    publishComment(id) {
      let param = {
        content: this.comment,
        id: this.id,
        type: 1
      };
      this.axios.post("/comment/add", param).then(res => {
        if (res.status == 200) {
          this.$message({
            message: "评论成功",
            type: "success"
          });
        }
        this.comment = "";
        this.getComment(this.currentPage);
      });
    }
  }
};
</script>
<style lang='scss' scoped>
@import "../assets/css/config";
.Comment {
  transition: all 0.3s;
  border: 1px solid #ebebeb;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.1);
  background: #fff;
  margin-top: 12px;
  border-radius: 4px;

  &TopBar {
    background: #fff;
    border-bottom: 1px solid #f6f6f6;
    height: 50px;
    padding: 0 20px;
    line-height: 50px;
  }
  .CommentItem {
    padding: 12px 20px 10px;
    font-size: 15px;
    position: relative;
  }
  .CommentItem-meta {
    display: flex;
    height: 24px;
    margin-bottom: 4px;
    align-items: center;
    width: 100%;
    .CommentItem-avatar {
      width: 24px;
      height: 24px;
      margin-right: 8px;
    }
    .CommentItem-name {
      font-weight: 500;
      flex: 1;
    }
    .CommentItem-time {
      font-size: 14px;
      color: $fontColor;
    }
  }
  // 评论
  .CommentItem-reply {
    padding: 20px 0 12px 20px;
    color: $fontColor;
    .CommentReply-users {
      flex: 1;
    }
    .CommentReply-name {
      color: #1a1a1a;
      font-weight: 500;
    }
  }
  //写评论
  .Comment-footer {
    transition: all 0.5s;
    padding: 12px 0 12px 20px;
    display: flex;
    align-items: center;
    .el-textarea {
      width: 86%;
    }
    .CommentReply-input {
      padding: 6px 12px;
      font-size: 14px;
      border: 1px solid $fontColor;
      border-radius: 4px;
    }
    .CommentReply-button {
      height: 36px;
      line-height: 36px;
      padding: 0 12px;
      margin-left: 20px;
      color: #ffffff;
      background: $mainColor;
    }
  }
}
//用户卡片
.UserCard-wrap {
  background-color: #fff;
  padding: 0 10px;
  position: relative;
  .UserMessage {
    display: flex;
    font-size: 16px;
    font-weight: 600;
    border-bottom: 1px solid #ebebeb;
  }
  .UserAvatar-wrap {
    width: 60px;
    height: 40px; //只是为了撑起一定高度，不一定要跟图片一样高
    margin-right: 20px;
  }
  .UserAvatar {
    width: 60px;
    height: 60px;
    position: absolute;
    top: -30px;
  }
  .UserContent {
    margin-top: 12px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    .UserContent-detail {
      display: flex;
      flex-direction: column;
      align-items: center;
      font-weight: 600;
      .UserContent-title {
        font-size: 14px;
        color: $fontColor;
      }
      .UserContent-num {
        font-size: 16px;
      }
    }
  }
  .UserButton {
    display: flex;
    align-items: center;
    justify-content: space-around;
    font-size: 15px;
    margin-top: 10px;
    .AttentionBtn {
      background-color: $mainColor;
      color: #fff;
      width: 140px;
      height: 36px;
      line-height: 36px;
      text-align: center;
      border-radius: 6px;
      border: 1px solid $mainColor;
      cursor: pointer;
    }
    .ChatBtn {
      color: $fontColor;
      width: 140px;
      height: 36px;
      line-height: 36px;
      text-align: center;
      border-radius: 6px;
      border: 1px solid $fontColor;
      cursor: pointer;
    }
  }
}
//分页
.PaginationWrap {
  padding: 0 40px;
}
</style>