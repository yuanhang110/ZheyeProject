var ws = require("nodejs-websocket");
let test = {};
let users = [];
console.log("开始链接");
console.log(test);
function boardCast(obj) {
  //群聊
  // server.connections.forEach(function (conn) {
  //   conn.sendText(JSON.stringify(obj));
  // })
}
var server = ws.createServer(function (conn) {
  conn.on("text", function (res) {
    //处理前端发来的请求
    data = JSON.parse(res);
    console.log("来人了");
    console.log(data);
    //把所有uid对应的链接conn存到一个对象里面
    if (data.type == 1) {
      //如果是新用户就走这里添加到user
      if (users.indexOf(data.uid) == -1) {
        users.push(data.uid);
        test[`${data.uid}`] = conn;
      }
    } else {
      console.log('发送')
      //没有type就代表是发送信息
      data.bridge.forEach(item => {
        test[item].sendText(JSON.stringify(data));
    });
    }
  })

  conn.on("close", function (code, reason) {
    console.log("关闭连接")
  });
  conn.on("error", function (code, reason) {
    console.log("异常关闭")
  });
}).listen(8001)
console.log("websocket建立完毕")