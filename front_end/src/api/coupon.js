import { axios } from '@/utils/request'
const api = {
    couponPre: '/api/coupon'
}
// 添加
export function hotelTargetMoneyAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTargetMoney`,
        method: 'POST',
        data,
    })
}
export function hotelTimeAPI(data){
    return axios({
        url: `${api.couponPre}/hotelTime`,
        method: 'POST',
        data,
    })
}
export function hotelOfMultiAPI(data){
    return axios({
        url: `${api.couponPre}/hotelOfMulti`,
        method: 'POST',
        data,
    })
}
// 查看某个酒店提供的所有优惠策略（包括失效的）
export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/hotelAllCoupons`,
        method: 'GET',
        params: {hotelId: hotelId},
    })
}

// 获取网站优惠策略（包括失效的）
export function webAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/webAllCoupons`,
        method: 'GET'
    })
}

// 匹配？
export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}

export function getCouponByIdAPI(couponId){
    return axios({
        url: `${api.couponPre}/getCouponById`,
        method: 'GET',
        params:{id:couponId} 
    })
}
// 修改
export function updateHotelTargetMoneyAPI(data){
    return axios({
        url: `${api.couponPre}/updateHotelTargetMoney`,
        method: 'POST',
        data
    })
}
export function updateHotelTimeAPI(data){
    return axios({
        url: `${api.couponPre}/updateHotelTime`,
        method: 'POST',
        data
    })
}
export function updateHotelOfMultiAPI(data){
    return axios({
        url: `${api.couponPre}/updateHotelOfMulti`,
        method: 'POST',
        data
    })
}