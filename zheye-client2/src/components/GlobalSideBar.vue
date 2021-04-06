  <!-- 首页侧边栏 -->
<template>
  <div class="GlobalSideBar-navList">
    <div class="GlobalSideBar-navItem">
      <span class="iconfont icon-shoucang"></span>
      <span class="GlobalSideBar-navText">我的收藏</span>
      <span class="GlobalSideBar-navNumber">2</span>
    </div>
    <div class="GlobalSideBar-navItem">
      <span class="iconfont icon-wenti"></span>
      <span class="GlobalSideBar-navText">我关注的问题</span>
      <span class="GlobalSideBar-navNumber">3</span>

    </div>
    <div class="GlobalSideBar-navItem">
      <span class="iconfont icon-bangzhuzhongxin"></span>
      <span class="GlobalSideBar-navText">帮助中心</span>
    </div>
  </div>
</template>
<script>
export default {
  name: "global-side-bar"
};
</script>
<style lang='scss' scoped>
@import '../assets/css/config.scss';
.GlobalSideBar-navList {
  width: 100%;
  padding: 8px 0;
  border-radius: 2px;
  background: #ffffff;
  font-size: 14px;
  color: $fontColor;
  .GlobalSideBar-navItem {
    padding: 0 20px;
    display: flex;
    align-items: center;
    height: 40px;
  }
  .GlobalSideBar-navText{
    flex:1;
    margin: 0 10px;
  }
  .GlobalSideBar-navNumber{
    padding: 6px 10px;
    border-radius: 2px;
    background: #f6f6f6;
    font-size: 12px;
  }
}
</style>