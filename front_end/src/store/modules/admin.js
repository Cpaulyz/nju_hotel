import {
    // getManagerListAPI,
    addManagerAPI,
    addHotelManagerAPI,
    getUserListAPI,
    resetPasswordAPI,
    addHotelAPI,
    deleteUserAPI
} from '@/api/admin'
import { message } from 'ant-design-vue'
import { hex_md5} from '../../utils/md5'
const admin = {
    state: {
        addManagerModalVisible: false,
        addHotelModalVisible:false,
        userList:[]
    },
    mutations: {
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addHotelModalVisible:function(state,data){
            state.addHotelModalVisible = data
        },
        set_userList:function(state,data){
            state.userList = data
        }
    },
    actions: {
        addManager: async({ commit, dispatch },data) => {
            data.password = hex_md5(data.password)
            const res = await addManagerAPI(data)
            if(res) {
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getUserList')
            }else{
                message.error('添加失败')
            }
        },
        addHotelAndHotelManager:async({commit,dispatch},data)=>{
            data.hotelManager.password = hex_md5(data.hotelManager.password)
            const res = await addHotelManagerAPI(data.hotelManager)
            if(res){
                data.hotel.managerId = res
                console.log(data.hotel);
                const hotelRes = await addHotelAPI(data.hotel)
                if(hotelRes){
                    commit('set_addHotelModalVisible', false)
                    message.success('添加成功')
                }
            }
        },
        getUserList: async({ commit }) => {
            const res = await getUserListAPI()
            if(res){
                commit('set_userList', res)
            }
        },
        resetPassword: async({commit},userId) => {
            console.log('重置密码');
            
            const res = await resetPasswordAPI(userId)
            if(res){
                message.success('重置成功')
            }
        },
        deleteUser:async({commit},userId)=>{
            const res = await deleteUserAPI(userId)
            if(res){
                message.success('删除成功')
            }
        }

    }
}
export default admin