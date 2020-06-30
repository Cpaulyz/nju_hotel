import { axios } from '@/utils/request'

const api = {
    userPre: '/api/user'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}

export function getUserCreditChangeListAPI(id){
    return axios({
        url: `${api.userPre}/${id}/checkAllCredit`,
        method: 'GET'
    })
}

export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}

export function updateCreditAPI(data){
    return axios({
        url: `${api.userPre}/updateCredit`,
        method: 'POST',
        data
    })
}

export function updatePasswordAPI(data){
    return axios({
        url: `${api.userPre}/updatePassword`,
        method: 'POST',
        data
    })
}

export function updateAvatarAPI(data){
    return axios({
        url: `${api.userPre}/updateAvatar/`,
        method: 'POST',
        data
    })
}