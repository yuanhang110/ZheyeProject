import VueCookies from 'vue-cookie';

// 是否登录过期
 function isLogin() {
   if(VueCookies.get('token')){
       return true
   }else{
       return false
   }
}


//监听滑动条事件
function listenScroll() {

}
//获取user信息
function getUser() {
    let user = localStorage.getItem('user');
    return JSON.parse(user)
}

export default {
    isLogin,
    listenScroll,
    getUser
}