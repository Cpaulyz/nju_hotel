import { message } from 'ant-design-vue'
import store from '@/store'
import {
    getAllHotelsAPI,
    getHotelByIdAPI,
    addCommentAPI,
    getHotelCommentAPI,
    getManagerHotelsAPI,
    updateHotelAPI,
    getHotelRoomsAPI
} from '@/api/hotel'
import {
    reserveHotelAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [
            
        ],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {

        },
        orderModalVisible: false,
        currentOrderRoom: {

        },
        orderMatchCouponList: [

        ],
        orderSuccessModalVisible:false,
        hotelComment:[],
        roomList:[],
        currentHotelRoomDate:[] // 存酒店要的入住日期和退房日期，是一个长度为2的数组，内容的moment
    },
    mutations: {
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        },
        set_orderSuccessModalVisible:function(state,data){
            state.orderSuccessModalVisible = data
        },
        set_hotelComment:function(state,data){
            state.hotelComment = data
        },     
        set_roomList:function(state,data){
            state.roomList = data
        },
        set_currentHotelRoomDate:function(state,data){
            state.currentHotelRoomDate = data
        }

        
    },

    actions: {
        getHotelList: async({commit, state}) => {
            const res = await getAllHotelsAPI()
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getManagerHotelList: async({commit, state},managerId) => {
            const data = {
                managerId: Number(managerId)
            }
            const res = await getManagerHotelsAPI(data)
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
            
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
                // console.log(res);
                
            }
        },
        addOrder: async({ state, commit }, data) => {
            const res = await reserveHotelAPI(data)
            console.log(res)
            if(res){
                message.success('预定成功')
                commit('set_orderModalVisible', false)
                commit('set_orderSuccessModalVisible',true)
            }else{
                message.error('预定失败')
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        addComment:async({state},data)=>{
            const res = await addCommentAPI(data)
            if (res){
                message.success('评价成功')
            }else{
                message.error('评价失败')
            }
        },
        getHotelComment:async({state,commit})=>{
            const res = await getHotelCommentAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_hotelComment',res)
            }
            console.log(res);
            
        },
        updateHotel:async({state,dispatch},data)=>{
            const res = await updateHotelAPI(data)
            if(res){
                message.success('修改成功')
            }
        },
        getRoomList:async({state,commit})=>{
            const vo = {
                hotelId:state.currentHotelId,
                checkInDate:state.currentHotelRoomDate[0].format('YYYY-MM-DD'),
                checkOutDate:state.currentHotelRoomDate[1].format('YYYY-MM-DD'),
            }
            const res = await getHotelRoomsAPI(vo)
            if(res){
                // console.log(res);
                
                commit('set_roomList',res)
            }
        }
    }
}

export default hotel