import Vue from 'vue'
import router from '@/router'
import { message } from 'ant-design-vue'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
    getOrderDetailAPI,
    getManagerOrdersAPI,
    executeOrderAPI,
    getAllOrdersAPI
} from '@/api/order'

const getDefaultState = () => {
    return {
        
        orderList: [],
        currentOrderInfo:[],
        // currentOrderId:''
    }
}

const order = {
    state : getDefaultState(),
    mutations: {
        set_orderList: (state, data) => {
            state.orderList = data
        },
        set_currentOrderInfo:(state,data)=>{
            state.currentOrderInfo = data
        },
        // set_currentOrderId:(state,data)=>{
        //     state.currentOrderId = data
        // },
    },

    actions: {
        getAllOrders: async({ state, commit }) => {
            const res = await getAllOrdersAPI()
            if(res){
                commit('set_orderList', res)
            }
        },
        getUserOrders: async({ state, commit },userId) => {
            const data = {
                userId: Number(userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_orderList', res)
                // console.log(state.orderList)
            }
        },
        getManagerOrders: async({ state, commit },userId) => {
            const data = {
                userId: Number(userId)
            }
            const res = await getManagerOrdersAPI(data)
            if(res){
                commit('set_orderList', res)
                // console.log(state.orderList)
            }
            
        },
        cancelOrder: async({ state, dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                // dispatch('getUserOrders')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        executeOrder:async({state,dispatch},orderId)=>{
            const res = await executeOrderAPI(orderId)
            if(res) {
                // dispatch('getUserOrders')
                message.success('入住成功')
            }else{
                message.error('入住失败')
            }  
        },
        getOrderDetail:async({commit},orderId)=>{
            const res = await getOrderDetailAPI(orderId)
            if (res){
                commit('set_currentOrderInfo', res)
            }
        },
    }
}
export default order