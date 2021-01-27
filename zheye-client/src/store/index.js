import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = { // 需要维护的状态
  isAdd: false
}
//getter相当于计算属性
const getters = {
  isAdd: state => state.isAdd,
}
//只有mutation 才能真的改变state，只能进行同步的操作
const mutations = {
  addItem(state,isAdd){
    state.isAdd = isAdd;
  }
}
//可以进行异步的操作，commit到的是mutation
const actions = {
    setIsAdd(context,value){
      context.commit('addItem',value)
    }
}
export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions
})
