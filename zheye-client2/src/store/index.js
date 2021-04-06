import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = { // 需要维护的状态
  isAdd: false,//新增问题
  searchContent: '',//搜索的内容
  searchList: [],//搜索返回列表
  wsMsg: {},//ws返回的信息
  ws: {},//websocket
}
//getter相当于计算属性
const getters = {
  isAdd: state => state.isAdd,
  searchContent: state => state.searchContent,
  searchList: state => state.searchList,
  wsMsg: state => state.wsMsg,
  ws: state => state.ws,
}
//只有mutation 才能真的改变state，只能进行同步的操作
const mutations = {
  addItem(state, isAdd) {
    state.isAdd = isAdd;
  },
  search(state, searchContent) {
    state.searchContent = searchContent
  },
  setSearchList(state, searchList) {
    state.searchList = searchList
  },
  setWsMsg(state, wsMsg) {
    state.wsMsg = wsMsg
  },
  setWs(state, ws) {
    state.ws = ws;
  }
}
//可以进行异步的操作，commit到的是mutation
const actions = {
  setIsAdd(context, value) {
    context.commit('addItem', value)
  },
  search(context, value) {
    context.commit('search', value)
  },
  setSearchList(context, value) {
    context.commit('setSearchList', value)
  },
  setWsMsg(context, value) {
    context.commit('setWsMsg', value)
  },
  setWs(context, value) {
    context.commit('setWs', value)
  }

}
export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions
})
