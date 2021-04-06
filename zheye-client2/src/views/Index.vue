<template>
  <div class="Topstory-container">
    <div class="Topstory-mainColumn">
      <el-tabs v-model="activeName" @tab-click="changeItem">
        <el-tab-pane label="推荐" name="recommend">
          <div v-for="(item,index) in feedList" :key="index">
            <feed-item :feedList="item"></feed-item>
          </div>
        </el-tab-pane>
        <el-tab-pane label="关注" name="attention">
          <div v-for="(item,index) in followList" :key="index">
            <feed-item :feedList="item"></feed-item>
          </div>
        </el-tab-pane>
      </el-tabs>
      <div class="Loading" v-show="isLoad">拼命加载中</div>
      <div class="Loading" v-show="isEnd">到底了</div>
    </div>
    <div class="GlobalSideBar">
      <global-side-bar></global-side-bar>
    </div>
  </div>
</template>
<script>
import FeedItem from "@/components/FeedItem.vue";
import GlobalSideBar from "@/components/GlobalSideBar.vue";
import { mapGetters } from "vuex";
import util from "@/utils/index.js";

let Load;
export default {
  name: "index",
  components: {
    FeedItem,
    GlobalSideBar
  },
  computed: {
    //监听词条
    ...mapGetters(["isAdd", "searchList"])
  },
  watch: {
    isAdd: function(newValue) {
      this.getContent();
    },
    searchList: function(newValue) {
      this.feedList = newValue;
      console.log("更新列表");
    }
  },
  data() {
    return {
      feedList: [],
      activeName: "recommend",
      followList: [],
      isLoad: false,
      recPage: 1, //推荐页面page
      attPage: 1, //关注页面page
      isEnd:false,
    };
  },
  mounted() {
    this.getContent();
    this.listenScroll(document.documentElement);
  },
  methods: {
    //推荐页面获取数据
    getContent() {
      this.$store.dispatch("search", "");
      this.axios.get(`/index?page=${this.recPage}`).then(res => {
        if (res.status == 200) {
          this.feedList = this.feedList.concat(res.data);
          this.$store.dispatch("setIsAdd", false);
          this.isLoad = false;
          if(!res.data){
            this.isEnd = true;
          }
        }
      });
    },
    //关注页面获取数据
    getAttention() {
      this.axios.get(`/followList?page=${this.attPage}`).then(res => {
        if (res.status == 200) {
          this.followList = this.followList.concat(res.data);
           if (Load) {
            Load.close();
            Load="";
          }
          this.isLoad = false;
        }
      });
    },
    //滚动
    listenScroll(ele) {
      let that = this;
      window.addEventListener("scroll", function() {
        let scr = ele.scrollTop; // 向上滚动的那一部分高度
        let clientHeight = ele.clientHeight; // 屏幕高度也就是当前设备静态下你所看到的视觉高度
        let scrHeight = ele.scrollHeight; // 整个网页的实际高度，兼容Pc端
        if (scr + clientHeight + 5 >= scrHeight) {
          console.log("到底了");
          if (that.isLoad == false||this.isEnd == false) {
            that.isLoad = true;
            if (that.activeName == "recommend") {
              that.recPage++;
              that.getContent();
            } else {
              that.attPage++;
              that.getAttention();
            }
          }
        }
      });
    },
    //切换选项卡
    changeItem() {
      console.log(this.activeName);
      if (this.activeName == "attention") {
        //调用接口
        this.getAttention();
      } else {
        this.getContent();
      }
    }
  }
};
</script>
<style lang="scss" >
@import "../assets/css/config";
.Topstory-container {
  display: flex;
  width: 1000px;
  padding: 0 16px;
  margin: 10px auto;
  height: 100%;
}
.Topstory-mainColumn {
  width: 654px;
  height: 100%;
  .el-tabs__header {
    background: #ffffff;
    padding: 10px 20px;
    margin: 0;
    border-bottom: 1px solid #ebebeb;
  }
  .el-tabs__item {
    font-size: 16px;
  }
  .el-tabs__item.is-active {
    color: $mainColor;
  }
  .el-tabs__nav-wrap::after {
    background: transparent;
  }
  .el-tabs__active-bar {
    height: 0;
  }
}
.GlobalSideBar {
  margin-left: 10px;
  flex: 1;
}
.Loading {
  height: 50px;
  text-align: center;
  color: $mainColor;

}
</style>