import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function getManagerOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getManagerOrders`,
        method: 'GET',
    })
}

export function executeOrderAPI(orderId){
    return axios({
        url: `${api.orderPre}/${orderId}/executeOrder`,
        method: 'GET',
    })
}
export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}
export function getOrderDetailAPI(orderId){
    return axios({
        url: `${api.orderPre}/${orderId}/getOrderDetail`,
        method: 'GET',
    })
}
