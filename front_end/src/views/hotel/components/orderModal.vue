<template>
    <div>
        <a-modal
            :visible="true"
            title="预定详情"
            cancelText="取消"
            okText="下单"
            :width="600"
            @cancel="cancelOrder"
            @ok="handleSubmit"
        >
            <a-form :form="form" @submit="handleSubmit">
                <a-form-item v-bind="formItemLayout" label="房型信息">
                    <span>{{ roomType_map[currentOrderRoom.roomType] }}</span>
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="入住人姓名">
                    <a-input
                        v-decorator="[
                            'clientName',
                            { rules: [{required: true, message: '请填写入住人姓名', }] }
                        ]"
                    />
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="手机号">
                    <a-input
                        v-decorator="[
                            'phoneNumber',
                            { rules: [{validator:this.checkPhoneNum}] }
                        ]"
                    />
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="入住日期">
                    <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                            'date',
                            {initialValue:currentHotelRoomDate}
                        ]"
                        disabled
                    />
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="入住人数">
                    <a-select
                        v-decorator="[
                            'peopleNum',
                            { rules: [{ required: true, message: '请选择入住人数' }] },
                        ]"
                        placeholder="请选择入住人数"
                        @change="changePeopleNum"
                    >
                        <a-select-option :value="1">
                        1
                        </a-select-option>
                        <a-select-option :value="2">
                        2
                        </a-select-option>
                        <a-select-option :value="3">
                        3
                        </a-select-option>
                        <a-select-option :value="4">
                        4
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="有无儿童">
                    <a-radio-group
                        v-decorator="[
                            'haveChild',
                            { rules: [{required: true, message: '请选择有无儿童入住', }] }
                        ]"
                    >
                        <a-radio :value="1">有</a-radio>
                        <a-radio :value="0">无</a-radio>
                    </a-radio-group>
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="房间数">
                    <a-select
                        v-decorator="[
                            'roomNum',
                            { rules: [{ required: true, message: '请选择房间数' }] },
                        ]"
                        placeholder="请选择房间数"
                        @change="changeRoomNum"
                    >
                        <a-select-option :value="1">
                        1
                        </a-select-option>
                        <a-select-option :value="2" v-if="currentOrderRoom.curNum>=2">
                        2
                        </a-select-option>
                        <a-select-option :value="3" v-if="currentOrderRoom.curNum>=3" >
                        3
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="房间单价">
                    <span>{{ currentOrderRoom.price }}</span>
                </a-form-item>
                <a-form-item v-bind="formItemLayout" label="总价">
                    <span>￥{{ totalPrice }}</span>
                </a-form-item>
                <a-divider></a-divider>
                <h2 v-if="orderMatchCouponList.length>0">优惠</h2>
                <a-checkbox-group v-model="checkedList" @change="onchange">
                    <a-table
                        :columns="columns"
                        :dataSource="orderMatchCouponList"
                        :showHeader="true"
                        bordered
                        v-if="orderMatchCouponList.length>0"
                    >
                        <a-checkbox slot="id"  slot-scope="record"  :value="record">
                        </a-checkbox>
                        <span slot="type" slot-scope="type">
                            <a-tag :color="type == 'Target' ? 'volcano' :  type=='Multi'?'geekblue':'orange'" :key="type">
                                {{ type_map[type] }}
                            </a-tag>
                        </span>                        
                        <span slot="discount" slot-scope="discount">
                            <span v-if="discount==0">/</span>
                            <span v-if="discount!=0">{{discount}}</span>
                        </span>                        
                        <span slot="discountMoney" slot-scope="discountMoney">
                            <span v-if="discountMoney==0">/</span>
                            <span v-if="discountMoney!=0">{{discountMoney}}</span>
                        </span>
                    </a-table>
                </a-checkbox-group>
                <a-form-item v-bind="formItemLayout" label="结算后总价">
                    <span>￥{{ finalPrice }}</span>
                </a-form-item>
            </a-form>
            <!-- <button @click="showSuccess">test</button> -->
        </a-modal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
const columns = [
    {  
        title: '使用',
        dataIndex: 'id',
        scopedSlots: {customRender: 'id'}
    },    
    {
        title: '名称',
        dataIndex: 'couponName',
        scopedSlots: {customRender: 'couponName'}
    },
    {
        title: '类型',
        dataIndex: 'couponType',
        key:'type',
        scopedSlots: {customRender: 'type'}
    },
    {
        title: '折扣',
        dataIndex: 'discount',
        key:'discount',
        scopedSlots:{customRender:'discount'}
    },

    {
        title: '简介',
        dataIndex: 'description',
        
    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney',
        key:'discountMoney',
        scopedSlots:{customRender:'discountMoney'}
    },
  ];
export default {
    name: 'orderModal',
    data() {
        return {  
            type_map:{
                'Target':'满减优惠',
                'Festival':'节日优惠',
                'Multi':'多间优惠'
            },
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
            totalPrice: 0,
            columns,
            checkedList: [],
            finalPrice: 0,
            roomNum:0,
            roomType_map:{
                'BigBed':"大床房",
                'DoubleBed':"双床房",
                'Family':"家庭房"
            }
        }
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'currentOrderRoom',
            'currentHotelId',
            'currentHotelInfo',
            'userId',
            'orderMatchCouponList',
            'orderSuccessModalVisible',
            'currentHotelRoomDate'
        ]),
        
    },
    mounted(){
        this.set_orderMatchCouponList([])
        // this.form.setFieldsValue({date:this.currentHotelRoomDate})
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_orderMatchCouponList'
        ]),
        ...mapActions([
            'addOrder',
            'getOrderMatchCoupons'
        ]),
        cancelOrder() {
            this.set_orderModalVisible(false)
        },
        changePeopleNum(v){

        },
        changeRoomNum(v) {
            var stayTime = moment(this.currentHotelRoomDate[1]).diff(moment(this.currentHotelRoomDate[0]), 'day')
            this.roomNum = Number(v)
            this.totalPrice = this.roomNum * stayTime * Number(this.currentOrderRoom.price)
            if(this.totalPrice!=0){
                this.refreshCoupon() // 刷新优惠
            }
        },
        onchange() {
            this.finalPrice = this.totalPrice
            if(this.checkedList.length>0){
                // 先满减后打折
                this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id)!=-1).forEach((item) => {
                    console.log(item);
                    if(item.couponType=='Target')
                        this.finalPrice= this.finalPrice-item.discountMoney
                })
                this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id)!=-1).forEach((item) => {
                    console.log(item);
                    if(item.couponType!='Target')
                        this.finalPrice = this.finalPrice * item.discount/10
                })
                this.finalPrice = Math.floor(this.finalPrice) // 四舍五入
            }
        },
        handleSubmit(e) {
            e.preventDefault();
            // console.log("outer");
            this.form.validateFieldsAndScroll(async (err, values) => {
                // console.log("validate")
                if (!err) {
                    const data = {
                        clientName:this.form.getFieldValue('clientName'),
                        phoneNumber:this.form.getFieldValue('phoneNumber'),
                        hotelId: this.currentHotelId,
                        hotelName: this.currentHotelInfo.name,
                        userId: Number(this.userId),
                        checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                        checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                        roomType: this.currentOrderRoom.roomType,
                        roomNum: this.form.getFieldValue('roomNum'),
                        peopleNum: this.form.getFieldValue('peopleNum'),
                        haveChild: this.form.getFieldValue('haveChild'),
                        createDate: '',
                        price: this.checkedList.length > 0 ? this.finalPrice: this.totalPrice
                    }
                    await this.addOrder(data)
                }else{
                    this.$message.error('下单失败，请修改后重新提交');
                }
            });
        },
        checkPhoneNum(rule, value, callback){
            const re = /^((1[0-9]{1})+\d{9})$/
            if (re.test(value)) {
                callback();
            } else {
                callback(new Error('请输入有效的电话号码'));
            }
            callback()
        },
        async refreshCoupon(){
            let data = {
                userId: this.userId,
                hotelId: this.currentHotelId,
                orderPrice: this.totalPrice,
                roomNum: this.roomNum,
                // 不能用上面这个，因为@change的时候表单还没更新
                checkIn: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                checkOut: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
            }
            await this.getOrderMatchCoupons(data)  // 这里要加await 保证orderMatchCouponList已经更新完才跑下面的
            this.onchange()
        }
    },
}
</script>