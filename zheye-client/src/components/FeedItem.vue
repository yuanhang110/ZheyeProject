 
  <!-- 首页feed的每一项 -->
<template>
  <div class="TopstoryItem">
    <div class="ContentItem-title" @click="goToDetail(feedList.id)">{{feedList.title}}</div>
    <feed-content v-if="feedList.answer" :author="feedList.answer" :summary="feedList.summary" :content="feedList.detail"></feed-content>
    <feed-actions @changeReview="isReview=!isReview" :actions="feedList.actions"></feed-actions>
  </div>
</template>
<script>
import FeedContent from "@/components/FeedContent.vue";
import FeedActions from "@/components/FeedActions.vue";

export default {
  name: "feed-item",
  components: {
    FeedContent,
    FeedActions
  },
  props: {
    feedList: Object
  },
  data() {
    return {
      isReview: false
    };
  },

  methods: {
    changeReview() {
      console.log("changeReview");
    },
    //进入详情页
    goToDetail(id) {
      this.$router.push({
        path: `detail/${id}`,
        params: {
          id: id
        }
      });
      console.log(id);
    }
  }
};
</script>
<style lang='scss' scoped>
@import "../assets/css/config";
.fade-enter-active,
.fade-leave-active {
  opacity: 1;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.TopstoryItem {
  background: #fff;
  border-bottom: 1px solid #f0f2f7;
  padding: 20px;
  .ContentItem-title {
    font-size: 18px;
    font-weight: 600;
    cursor: pointer;
  }
}
</style>