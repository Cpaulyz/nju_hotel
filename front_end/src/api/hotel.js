import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getAllHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}
export function addCommentAPI(data){
    console.log(data);
    return axios({
        url:`${api.hotelPre}/addComment`,
        method:'POST',
        data
    })
}
export function getHotelCommentAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/checkComment`,
        method: 'GET',
    })
}

export function getManagerHotelsAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.managerId}/getManagerHotels`,
        method: 'GET',
    })
}

export function updateHotelAPI(data){
    return axios({
        url:`${api.hotelPre}/updateHotel`,
        method:'POST',
        data
    })
}

export function getHotelRoomsAPI(data){
    // API用来根据酒店id、入住时间、退房时间筛选可用客房
    return axios({
        url:`${api.hotelPre}/getHotelRooms`,
        method:'POST',
        data
    })
}

