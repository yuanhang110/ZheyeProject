  <!-- 用户卡片 -->
<template>
  <el-popover placement="bottom-start" visible-arrow="false" width="365" trigger="hover">
    <div class="UserCard-wrap">
      <div class="UserMessage">
        <div class="UserAvatar-wrap">
          <img :src="user.headUrl" class="UserAvatar" />
        </div>
        <span class="UserName">{{user.name}}</span>
      </div>
      <div class="UserContent">
        <div class="UserContent-detail">
          <div class="UserContent-title">回答</div>
          <div class="UserContent-num">{{user.answerNum}}</div>
        </div>
        <div class="UserContent-detail">
          <div class="UserContent-title">关注者</div>
          <div class="UserContent-num">{{user.followNum}}</div>
        </div>
      </div>
      <div class="UserButton">
        <div class="AttentionBtn" :class="{followBtn:user.isFollow}" @click="followUser">
          <span class="iconfont icon-jia" v-if="!user.isFollow"></span>
          <span>{{user.isFollow?'取消关注':'关注'}}</span>
        </div>
        <div class="ChatBtn" @click="toChat()">
          <span class="iconfont icon-xiaoxi"></span>
          <span>发私信</span>
        </div>
      </div>
    </div>
    <img slot="reference" :src="user.headUrl" class="AuthorAvatar" />
  </el-popover>
</template>
<script>
export default {
  name: "user-card",
  props: {
    user: Object
  },
  data() {
    return {};
  },
  methods: {
    //关注
    followUser() {
      if (this.user.isFollow) {
        //取消关注
        this.axios.get(`/follow/user_cancel?id=${this.user.id}`).then(res => {
          this.user.isFollow = !this.user.isFollow;
        });
      } else {
        this.axios.get(`/follow/user?id=${this.user.id}`).then(res => {
          this.user.isFollow = !this.user.isFollow;
        });
      }
    },
    //私信
    toChat(){
      this.$router.push({
        name:'contact',
        params:{
          user : this.user
        }
      })
    }
  }
};
</script>
<style lang='scss' scoped>
@import "../assets/css/config";
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
    .followBtn {
      border: 1px solid $fontColor;
      background-color: $fontColor;
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
.AuthorAvatar {
  width: 24px;
  height: 24px;
  margin-right: 10px;
}
</style>