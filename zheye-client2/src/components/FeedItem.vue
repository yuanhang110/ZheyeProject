 
  <!-- 首页feed的每一项 -->
<template>
  <div class="TopstoryItem">
    <div
      class="ContentItem-title"
      @click="goToDetail(feedList.id)"
      v-html="brightenKeyword(feedList.title,keyword)"
    ></div>
    <feed-content
    ref="feedContent"
      v-if="feedList.answer"
      :author="feedList.answer"
      :summary="feedList.summary"
      :content="feedList.detail"
      :keyword="keyword"
    ></feed-content>
    <feed-actions
      v-if="feedList.detail"
      @changeReview="isReview=!isReview"
      :actions="feedList.detail.actions"
      :id="feedList.detail?feedList.detail.id:0"
    ></feed-actions>
    <transition name="fade">
      <comment v-if="isReview" :id="feedList.detail?feedList.detail.id:0"></comment>
    </transition>
  </div>
</template>
<script>
import FeedContent from "@/components/FeedContent.vue";
import FeedActions from "@/components/FeedActions.vue";
import Comment from "@/components/Comment.vue";
import { mapGetters } from "vuex";

export default {
  name: "feed-item",
  components: {
    FeedContent,
    FeedActions,
    Comment
  },
  props: {
    feedList: Object
  },
  computed: {
    ...mapGetters(["searchContent"])
  },
  watch: {
    searchContent: function(newValue) {
      this.keyword = newValue;
      console.log("更改关键词")
      console.log(newValue)
    }
  },
  data() {
    return {
      isReview: false,
      keyword: ""
    };
  },
  methods: {
    changeReview() {
      console.log("changeReview");
    },
    //进入详情页
    goToDetail(id) {
      this.$router.push({
        path: `/detail/${id}`,
        params: {
          id: id
        }
      });
      console.log(id);
    },
    //搜索高亮
    brightenKeyword(val, keyword) {
      if (keyword.length > 0) {
        let keywordArr = keyword.split("");
        val = val + "";
        keywordArr.forEach(item => {
          if (val.indexOf(item) !== -1 && item !== "") {
            val = val.replace(
              new RegExp(item,'g'),
              '<font color="#f75353">' + item + "</font>"
            );
          }
        });
        return val;
      } else {
        return val;
      }
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