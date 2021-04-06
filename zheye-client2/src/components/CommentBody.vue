  <!-- 评论细节 -->
<template>
  <!-- 评论内容 -->
  <div class="CommentItem-body">
    <div class="CommentItem-content">{{commentMsg.content}}</div>
    <div class="CommentItem-icons">
      <div class="CommentItem-icon" :class="{IsLike:commentMsg.isLike}" @click="changeLike">
        <span class="icon-zan1 iconfont"></span>
        <span class="CommentItem-desc">{{commentMsg.likeNum ? commentMsg.likeNum :'0'}}</span>
      </div>
      <div
        class="CommentItem-icon HoverIcon"
        :class="{showIcon:isReply}"
        @click="isReply = !isReply"
      >
        <span class="icon-pinglun1 iconfont"></span>
        <span class="CommentItem-desc">{{isReply? '取消回复':'回复'}}</span>
      </div>
      <div class="CommentItem-icon HoverIcon" :class="{showIcon:isReply}" @click="changeDislike">
        <span class="icon-cai iconfont"></span>
        <span class="CommentItem-desc">{{commentMsg.isDisLike ? "取消踩" : "踩"}}</span>
      </div>
    </div>
    <!-- 回复 -->
    <transition name="fade">
      <div class="CommentReply" v-if="isReply">
        <el-input
          type="textarea"
          resize="none"
          autosize
          :placeholder="'回复'+commentMsg.user.name"
          v-model="replyMsg"
        ></el-input>
        <button class="CommentReply-button" :disabled="replyMsg.length==0" @click="publishReply">发表</button>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  name: "comment-body",
  props: {
    commentMsg: Object,
    type:Number
  },
  data() {
    return {
      replyMsg: "",
      disabled: true,
      isReply: false //是否回复
    };
  },
  methods: {
    //   点赞
    changeLike() {
      if (this.commentMsg.isLike) {
        //取消赞
        this.axios
          .get(`support_cancel?id=${this.commentMsg.id}&type=3`)
          .then(res => {
            if (res.status == 200) {
              this.commentMsg.isLike = !this.commentMsg.isLike;
              this.commentMsg.likeNum--;
            }
          });
      } else {
        this.axios.get(`support?id=${this.commentMsg.id}&type=3`).then(res => {
          if (res.status == 200) {
            this.commentMsg.isLike = !this.commentMsg.isLike;
            if (this.commentMsg.isDisLike) {
              this.commentMsg.isDisLike = false;
            }
            this.commentMsg.likeNum++;
          }
        });
      }
    },
    //踩
    changeDislike() {
      if (this.commentMsg.isDisLike) {
        //取消踩
        this.axios
          .get(`unsupport_cancel?id=${this.commentMsg.id}&type=3`)
          .then(res => {
            if (res.status == 200) {
              this.commentMsg.isDisLike = !this.commentMsg.isDisLike;
            }
          });
      }else{
         this.axios
          .get(`unsupport?id=${this.commentMsg.id}&type=3`)
          .then(res => {
            if (res.status == 200) {
              this.commentMsg.isDisLike = !this.commentMsg.isDisLike;
              if(this.commentMsg.isLike){
                this.commentMsg.isLike=false;
                this.commentMsg.likeNum--;
              }

            }
          });
      }
    },
    //发布回复
    publishReply(){
      let param = {
        content:this.replyMsg,
        id:this.commentMsg.id,
        type:2
      }
      this.axios.post('/comment/add',param).then(res=>{
        if(res.status == 200){
           this.$message({
              message: "评论成功",
              type: "success"
            });
        }
        this.replyMsg = "";
        this.$emit('addReply');
      });
    }
  }
};
</script>
<style lang='scss' >
@import "../assets/css/config";
.fade-enter-active,
.fade-leave-active {
  opacity: 1;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
// 评论身
.CommentItem-body {
  position: relative; //为了分割线
  color: #1a1a1a;
  padding: 0 0 10px 33px;

  .CommentItem-content {
    margin-bottom: 6px;
  }
  .CommentItem-icons {
    display: flex;
    align-items: center;
    font-size: 14px;
    margin-top: 4px;
    color: $fontColor;
    .CommentItem-icon {
      display: flex;
      align-items: center;
      .CommentItem-desc {
        margin-left: 4px;
        cursor: pointer;
      }
    }
    .HoverIcon {
      margin-left: 20px;
      transition: opacity 0.2s;
      opacity: 0;
    }

    &:hover .HoverIcon {
      opacity: 1;
    }
    .showIcon {
      opacity: 1;
    }
  }
  .IsLike {
    color: $mainColor;
    &:hover {
      color: #175199;
    }
  }
  //回复
  .CommentReply {
    transition: all 0.2s;
    padding: 12px 0;
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
  &:after {
    position: absolute;
    left: 0;
    right: 0;
    bottom: -5px;
    display: block;
    margin: 0 20px;
    border-bottom: 1px solid #f6f6f6;
    content: "";
  }
}
</style>