import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import hotel from './modules/hotel'
import hotelManager from './modules/hotelManager'
import admin from './modules/admin'
import order from './modules/order'
import coupon from './modules/coupon'
import getters from './getters'
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    hotel,
    user,
    hotelManager,
    admin,
    order,
    coupon
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
