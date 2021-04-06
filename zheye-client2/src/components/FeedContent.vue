  <!-- 首页摘要 -->
<template>
  <div class="RichContent-wrapper">
    <div class="RichContent-inner">
      <template v-if="!isRead">
        <b>{{author.name}}</b>
        <span v-html="brightenKeyword(summary,keyword)"></span>
        <span class="ContentItem-more" @click="isRead = !isRead" v-if="isShow">阅读全文</span>
        <span class="iconfont icon-arrow-down" v-if="isShow"></span>
      </template>
      <rich-content v-else @collapseText="isRead = !isRead" :content="content"></rich-content>
    </div>
  </div>
</template>
<script>
import RichContent from "@/components/RichContent.vue";
import FeedActions from "@/components/FeedActions.vue";
export default {
  name: "feed-content",
  components: {
    RichContent
  },
  props:{
    author:String,
    summary:String
  },
  props: {
    author: Object,
    summary: String,
    content:Object,
    keyword:String
  },
  data() {
    return {
      isRead: false,
      isShow:true
    };
  },
  mounted(){
    if(this.summary.length<36){
      this.isShow=false;
    }
  },
  methods:{
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
.RichContent-inner {
  font-size: 15px;
  margin-top: 9px;
  .ContentItem-more {
    margin-left: 4px;
    color: #175199;
    font-size: 14px;
    cursor: pointer;
  }
  .icon-arrow-down {
    color: #175199;
    font-size: 18px;
    font-weight: 600;
  }
}
</style>