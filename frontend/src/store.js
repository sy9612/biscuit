import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    loginStatus: {
      token: localStorage.getItem("token"),
      email: localStorage.getItem("email"),
      nickname: localStorage.getItem("nickname"),
      admin: localStorage.getItem("admin"),
      phone: localStorage.getItem("phone"),
      region: localStorage.getItem("region"),
      picture: localStorage.getItem("picture"),
      myStudyNum:0
    },
  },
  mutations: {
    // LOGOUT: function(state) {
    //   state.loginStatue=false
    // },
    // LOGIN: function(state) {
    //   state.loginStatus = localStorage.getItem("nickname")
    // }
  },
  actions: {
    // logout: function({commit}) {
    //   commit('LOGOUT')
    // },
    // login: function({commit}) {
    //   commit('LOGIN')
    // }
  },
  modules: {}
});
