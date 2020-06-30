import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    getCouponByIdAPI,
    updateHotelTargetMoneyAPI,
    hotelTimeAPI,
    updateHotelTimeAPI,
    hotelOfMultiAPI,
    webAllCouponsAPI,
    updateHotelOfMultiAPI
} from '@/api/coupon'
import { message } from 'ant-design-vue'

const coupon = {
    state: {
        couponVisible: false,
        addCouponVisible: false,
        couponList: [],
    },
    mutations: {
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        }
    },
    actions: {
        getHotelCoupon: async({commit },hotelId) => {
            const res = await hotelAllCouponsAPI(hotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                // console.log(res);
                commit('set_couponList',res)
            }
        },
        getWebCoupon: async ({commit})=>{
            const res = await webAllCouponsAPI()
            if(res) {
                commit('set_couponList',res)
            }
        },
        addHotelCoupon: async({ state, commit }, data) => {
            console.log(data);
            var res;
            if(data.type==2){
                res = await hotelTargetMoneyAPI(data)
            }else if(data.type==1){
                res = await hotelTimeAPI(data)
            }else if(data.type==0){
                res = await hotelOfMultiAPI(data)
            }
            // const res = await hotelTargetMoneyAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                message.success('添加成功')
                const list = await hotelAllCouponsAPI(data.hotelId)
                commit('set_couponList',list)
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        getCouponById: async({state,commit},data)=>{
            const res = await getCouponByIdAPI(data);
            if(res){
                // console.log(res);
                return res
            }
        },
        updateHotelCoupon: async({ state, commit }, data) => {
            var res;
            if(data.type==2){
                res = await updateHotelTargetMoneyAPI(data)
            }else if(data.type==1){
                res = await updateHotelTimeAPI(data)
            }else if(data.type==0){
                res = await updateHotelOfMultiAPI(data)
            }
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                message.success('修改成功')
                const list = await hotelAllCouponsAPI(data.hotelId)
                commit('set_couponList',list)
            }else{
                // 添加失败后的操作
                message.error('修改失败')
            }
        },

    }
}
export default coupon