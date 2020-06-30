import Vue from 'vue'
import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {hex_md5} from '../../utils/md5'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    updateCreditAPI,
    getUserCreditChangeListAPI,
    updatePasswordAPI,
    updateAvatarAPI
} from '@/api/user'
const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {

        },
        creditChangeList:[],
        roles:[], // 存储用户身份,
        avatar:''
    }
}

const user = {
    state : getDefaultState(),

    mutations: {
        set_avatar:function(state,data){
            state.avatar =data
        },
        set_roles:function(state,data){
            state.roles = data
        },
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.userInfo = {
                
            },
            state.userOrderList = []
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_creditChangeList:(state,data)=>{
            state.creditChangeList = data
        }
    },

    actions: {
        login: async ({ dispatch, commit }, userData) => {
            userData.password = hex_md5(userData.password)
            console.log(userData);
            const res = await loginAPI(userData)
            if(res){
                setToken(res.id)
                commit('set_userId', res.id)
                dispatch('getUserInfo')
                router.push('/hotel/hotelList')
            }
        },
        register: async({ commit }, data) => {
            data.password = hex_md5(data.password)
            console.log(data);
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(state.userId).then(response => {
                const data = response
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                commit('set_roles', data.userType)
                commit('set_avatar',data.avatar)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
          // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },

        updateCredit: async({commit},data)=>{
            const res = await updateCreditAPI(data)
            if(res){
                message.success('信用值已更新')
            }
        },
        getUserCredit:async({state},userId)=>{
            const res = await getUserInfoAPI(userId)
            if(res){
                return res.credit
            }
        },
        getUserCreditChangeList:async({commit,state})=>{
            const res = await getUserCreditChangeListAPI(state.userId)
            if(res){
                commit('set_creditChangeList',res)
            }
        },
        updatePassword:async ({commit},data)=>{
            data.oldPassword = hex_md5(data.oldPassword)
            data.newPassword = hex_md5(data.newPassword)
            const res = await updatePasswordAPI(data)
            if(res){
                message.success('修改成功')
            }
        },
        updateAvatar:async({state,commit},url)=>{
            var data ={
                id:state.userId,
                avatar:url
            }
            console.log(data);
            
            const res = await updateAvatarAPI(data)
            if(res){
                message.success('修改成功')
                commit('set_avatar',url)
            }

        }
    }
}

export default user