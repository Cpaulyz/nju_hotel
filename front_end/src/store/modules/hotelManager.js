import {
    addRoomAPI,
    updateRoomAPI,
    deleteRoomAPI
} from '@/api/hotelManager'
import { message } from 'ant-design-vue'

const hotelManager = {
    state: {
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        activeHotelId: 0,
        activeHotelName:'',
    },
    mutations: {
        set_activeHotelName:function(state,data){
            state.activeHotelName = data;
        },
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        }
    },
    actions: {
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        updateRoom:async({state},data)=>{
            const res = await updateRoomAPI(data)
            if(res){
                message.success('修改成功')
            }
        },
        deleteRoom:async({state},data)=>{
            const res = await deleteRoomAPI(data)
            if(res){
                message.success('删除成功')
            }
        }
    }
}
export default hotelManager