 import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Index from '../views/Index.vue'
import Contact from '../views/Contact.vue'
import Detail from '../views/Detail.vue'
Vue.use(VueRouter)
  const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    children:[
      {
        path:'/',
        name:'index',
        component:Index
      },
      {
        path: '/detail/:id', 
        name: 'detail',
        component:Detail
      },
    ]
  },
  {
    path: '/contact', 
    name: 'contact',
    component:Contact
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
