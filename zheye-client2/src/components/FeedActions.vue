  <!-- 赞同评论等图标 -->
<template>
  <div class="ContentItem-actions">
    <div class="Vote-wrap" :class="{ChangeStatus:actions.isAgree}" @click="changeAgree">
      <span class="icon-down-fill-xs iconfont"></span>
      <span
        class="Vote-desc"
      >{{ actions.isAgree ? `已赞同 ${actions.agreeNum}` : `赞同 ${actions.agreeNum}`}}</span>
    </div>
    <div class="Vote-wrap" :class="{ChangeStatus:actions.disagree}" @click="changeDisagree">
      <span class="icon-down-fill1-xs iconfont"></span>
    </div>
    <div class="Actions-wrap" @click="changeReview">
      <span class="iconfont icon-pinglun1"></span>
      <span class="Actions-desc">{{isReview? "收起评论":`${actions.reviewNum}条评论`}}</span>
    </div>
    <div class="Actions-wrap">
      <span class="iconfont icon-xiaofeiji"></span>
      <span class="Actions-desc">分享</span>
    </div>
    <div class="Actions-wrap">
      <span class="iconfont icon-shoucang"></span>
      <span class="Actions-desc">收藏</span>
    </div>
    <div class="Actions-wrap" @click="actions.like = !actions.like">
      <span class="iconfont icon-xihuan"></span>
      <span class="Actions-desc">{{actions.like?'取消喜欢':'喜欢'}}</span>
    </div>
    <div class="Actions-wrap">
      <span class="iconfont icon-shenglvehao"></span>
    </div>
  </div>
</template>
<script>
export default {
  name: "feed-action",
  props: {
    actions: Object,
    id: Number
  },
  data() {
    return {
      isReview: false
    };
  },
  methods: {
    //改变评论状态
    changeReview() {
      this.$emit("changeReview", !this.isReview);
      this.isReview = !this.isReview;
    },
    //改变赞同状态
    changeAgree() {
      if (this.actions.isAgree) {
        //取消赞
        this.axios.get(`support_cancel?type=2&id=${this.id}`).then(res => {
          if (res.status == 200) {
            this.actions.isAgree = !this.actions.isAgree;
            this.actions.agreeNum--;
          }
        });
      } else {
        // 点赞
        this.axios.get(`/support?type=2&id=${this.id}`).then(res => {
          if (res.status == 200) {
            this.actions.isAgree = !this.actions.isAgree;
             if (this.actions.disagree) {
                this.actions.disagree = false;
              }
              this.actions.agreeNum++;
          }
        });
      }
    },
    changeDisagree() {
      if (this.actions.disagree) {
        //取消踩
        this.axios.get(`/unsupport_cancel?type=2&id=${this.id}`).then(res => {
          if (res.status == 200) {
            this.actions.disagree = !this.actions.disagree;
          }
        });
      } else {
        //踩
        this.axios.get(`/unsupport?type=2&id=${this.id}`).then(res => {
          if (res.status == 200) {
            this.actions.disagree = !this.actions.disagree;
            if (this.actions.isAgree) {
              this.actions.isAgree = false;
              this.actions.agreeNum--;
            }
          }
        });
      }
    }
  }
};
</script>
<style lang='scss' scoped>
@import "../assets/css/config.scss";
.ContentItem-actions {
  display: flex;
  align-items: center;
  padding-top: 10px;
  color: #646464;
  background: #fff;
  .Vote-wrap {
    height: 32px;
    line-height: 32px;
    padding: 0 10px;
    color: $mainColor;
    background: rgba(0, 132, 255, 0.1);
    font-size: 14px;
    margin-right: 4px;
    display: flex;
    align-items: center;
    cursor: pointer;
  }
  .Actions-wrap {
    display: flex;
    align-items: center;
    color: $fontColor;
    font-size: 14px;
    margin-left: 24px;
    cursor: pointer;
  }
  .Actions-desc,
  .Vote-desc {
    margin-left: 4px;
  }
  .ChangeStatus {
    color: #fff;
    background: $mainColor;
  }
}
</style>