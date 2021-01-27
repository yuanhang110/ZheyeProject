<template>
  <div class="RichContent">
    <div class="AuthorInfo">
      <img :src="content.answer.headUrl"  class="AuthorAvatar"/>
      <span class="AuthorName">{{content.answer.name}}</span>
    </div>
    <div class="RichContent-votes">{{content.actions.agreeNum}}人赞同了该回答</div>
    <div 
      v-html="content.content"
      class="RichContent-text"
    ></div>
  <div class="RichContent-time">编辑于{{content.createdDate}}</div>
  <div class="CollapsedText" @click="$emit('collapseText')">
    <span >收起</span>
    <span class="iconfont icon-arrow-up"></span>
  </div>
 
  </div>
</template>
<script>
export default {
  name: "rich-content",
  props:{
    content:Object
  }
};
</script>
<style lang='scss' scoped>
@import "../assets/css/config";
.RichContent {
  position: relative;
  font-size: 15px;
  .AuthorInfo {
    display: flex;
    align-items: center;
    font-weight: 600;
    color: #444444;
    .AuthorAvatar {
      width: 24px;
      height: 24px;
      margin-right: 10px;
    }
  }
  .RichContent-votes{
    padding-top: 10px;
    font-size: 14px;
    color:$fontColor;
  }
  .RichContent-text{
    margin-top: 9px;
  }
  .RichContent-time{
    font-size: 14px;
    color: $fontColor;
    margin-top: 10px;
  }
  .CollapsedText{
    position: absolute;
    right: 10px;
    bottom: -35px;
    color: $fontColor;
    font-size: 14px;
    cursor: pointer;
  }
}
</style>