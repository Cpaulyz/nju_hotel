import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}

export function updateRoomAPI(data){
    // 更新房间信息
    return axios({
        url:`${api.hotelPre}/updateRoomInfo`,
        method:'POST',
        data
    })
}

export function deleteRoomAPI(roomId){
    // 更新房间信息
    return axios({
        url:`${api.hotelPre}/${roomId}/deleteRoom`,
        method:'GET'
    })
}