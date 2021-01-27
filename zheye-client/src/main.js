import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import axios from 'axios';
import VueAxios from 'vue-axios';
import VueCookies from 'vue-cookie';
import 'element-ui/lib/theme-chalk/index.css';

axios.interceptors.response.use(function(response){
  return response
},(error)=>{
  let res = error.response;
   if(res.status == 400){
    ElementUI.Message.error(res.data.msg)
   }else if(res.status == 401){
    ElementUI.Message.error("请先登陆")
   }
});

Vue.use(ElementUI);
Vue.use(VueAxios,axios);
Vue.use(VueCookies)

new Vue({
  router,
  store, 
  render: h => h(App)
}).$mount('#app')
