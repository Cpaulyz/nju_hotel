import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin',
    hotelPre: '/api/hotel'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'POST'
    })
}
export function getUserListAPI(){
    return axios({
        url: `${api.adminPre}/getAllUsers`,
        method: 'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function addHotelManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addHotelManager`,
        method: 'POST',
        data
    })
}
export function resetPasswordAPI(userId){
    return axios({
        url: `${api.adminPre}/${userId}/resetPassword`,
        method: 'GET',
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function deleteUserAPI(userId){
    return axios({
        url: `${api.adminPre}/deleteUser/${userId}`,
        method: 'GET',
    })
}