<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>


<script>
import util from "@/utils/index.js";
export default {
  name: "app",
  mounted() {
    this.initWebsocket();
  },
  methods: {
    //创建websocket链接
    initWebsocket() {
      let that = this;
      if (window.WebSocket) {
        var ws = new WebSocket("ws://localhost:9999/chat");
        // 指定接收二进制数据类型
        ws.binaryType = "arraybuffer";
        that.$store.dispatch("setWs", ws);
        ws.onopen = function(e) {
          console.log("连接服务器成功");
          let msg = {
            userId: util.getUser().id,
            command: 1
          };
          ws.send(util.encode(msg));
        };
        ws.onclose = function(e) {
          console.log("服务器关闭");
        };
        ws.onerror = function() {
          console.log("连接出错");
        };
        // 接收服务器的消息
        ws.onmessage = function(event) {
          let data = util.decode(event.data);
          console.log(data);
          console.log('来消息了');

          that.$store.dispatch("setWsMsg", data);
         
        };
      }
    }
  }
};
</script>

<style>
</style>
<style  lang="scss">
@import "./assets/css/reset.scss";
@import "./assets/css/index.scss";
#app {
  width: 100%;
  height: 100%;
}
</style>
