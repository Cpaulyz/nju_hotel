<template>
    <a-modal
        :visible="true"
        :title="title"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
            <a-form :form="form" @submit="handleSubmit">
            <a-form-item v-bind="formItemLayout" label="适用酒店">
                <a-input
                    v-decorator="[
                    'hotelId',
                    ]"
                    :placeholder="activeHotelName"
                    :disabled ="true"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="券名">
                <a-input
                    v-decorator="[
                    'name',
                    {
                        rules: [{
                            required: true,
                            message: '请填写券名',
                        }],
                    },
                    ]"
                    placeholder="请填写券名"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="优惠券类型">
                <a-select
                    v-decorator="[
                    'type',
                    { rules: [{ required: true, message: '请选择优惠券类型' }] },
                    ]"
                    placeholder="请选择优惠券类型"
                >
                    <a-select-option :value="couponType.Target">
                    满减优惠
                    </a-select-option>
                    <a-select-option :value="couponType.Festival">
                    节日特惠
                    </a-select-option> 
                    <a-select-option :value="couponType.Multi">
                    多间特惠(三间以上)
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="优惠简介">
                <a-textarea
                    v-decorator="[
                    'description',
                    { rules: [{ required: true, message: '请填写优惠简介' }] },
                    ]"
                    placeholder="请填写优惠简介"
                >
                </a-textarea>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="达标金额" v-show="this.form.getFieldValue('type')==couponType.Target">
                <a-input
                    v-decorator="[
                    'targetMoney',
                    {rules: [{validator:this.checkTargetMoney}]},
                    ]"
                    placeholder="达到达标金额后可满减"
                />
            </a-form-item> 
            <a-form-item v-bind="formItemLayout" label="优惠金额" v-show="this.form.getFieldValue('type')==couponType.Target">
                <a-input
                    v-decorator="[
                    'discountMoney',
                    {rules: [{validator:this.checkDiscountMoney}]},
                    ]"
                    placeholder="可满减的优惠金额"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="折扣" v-show="this.form.getFieldValue('type')!=couponType.Target">
                <a-input
                    v-decorator="[
                    'discount',
                    {rules: [{validator:this.checkDiscount}]},
                    ]"
                    placeholder="折扣"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有效日期" v-show="this.form.getFieldValue('type')==couponType.Festival">
                <a-range-picker v-decorator="['date_range', {rules: [{ type: 'array',validator:this.checkDateRange }]}]" />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="优惠状态">
                <a-radio-group v-decorator="['valid',{ initialValue: 1 }]">
                    <a-radio :value="1">有效</a-radio>
                    <a-radio :value="0">失效</a-radio>
                </a-radio-group>  
            </a-form-item>  
        </a-form>   
        <!-- 这里是添加策略模态框区域，请编写表单 -->
    </a-modal>
</template>  
<script>


import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
export default {
    name: 'addCouponModal',
    props:['couponId'],
    data() {
        return {
            couponType :{
                'Multi':0,      //多间优惠
                'Festival':1,   //节日优惠
                'Target':2  // 满减优惠
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
        }
    },
    computed: {
        ...mapGetters([
            'activeHotelId',
            'addCouponVisible',
            'activeHotelName',
        ]),
        title(){
            return this.couponId==0?'添加优惠策略':'修改优惠策略'
        },
        isTargetMoney(){
            console.log(this.form.getFieldValue('type'));
            return this.form.getFieldValue('type')==3
        }
    },
    beforeCreate() {
        // 表单名默认为“form”
        this.form = this.$form.createForm(this, { 
            name: 'addHotelModal' 
        });
        this.form.getFieldDecorator("name",{})

    },
    async mounted() {
        var res
        // res里的couponType为字符串
        if(this.couponId!=0){
                res =await this.getCouponById(this.couponId)
                this.form.setFieldsValue({ name: res.couponName });
                this.form.setFieldsValue({ type: this.couponType[res.couponType] });
                this.form.setFieldsValue({ description: res.description });
                // this.form.setFieldsValue({ hotelId: res.hotelId });
                this.form.setFieldsValue({ valid: res.status });
                if(res.couponType=='Festival'){
                    this.form.setFieldsValue({ date_range: [moment(res.startTime),moment(res.endTime)] });
                    this.form.setFieldsValue({ discount: res.discount });
                }else if(res.couponType=='Target'){
                    this.form.setFieldsValue({ discountMoney: res.discountMoney });
                    this.form.setFieldsValue({ targetMoney: res.targetMoney });
                }else if (res.couponType=='Multi'){
                     this.form.setFieldsValue({ discount: res.discount });
                }
            }else{
                this.form = this.$form.createForm(this, { name: 'addCouponModal' });
            }

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponVisible',
        ]),
        ...mapActions([
            // addHotelCoupon：添加酒店策略接口
            'addHotelCoupon',
            'getCouponById',
            'updateHotelCoupon'
        ]),
        cancel(e) {
            // console.log(e.target.className=='ant-btn');
            this.set_addCouponVisible(false)
            if (e.target.className=='ant-btn'){
                 this.set_couponVisible(true)
            }
        },
        handleSubmit(e) {
            e.preventDefault();
            console.log(this.form.getFieldValue('type'));
            this.form.validateFieldsAndScroll((err, values) => {
                //  console.log("submit");
                if (this.form.getFieldValue('type')===this.couponType.Target){ // 满减型提交
                    if (!err) {
                        const data = {
                            hotelId: this.activeHotelId,
                            name: this.form.getFieldValue('name'),
                            type: this.form.getFieldValue('type'),
                            description: this.form.getFieldValue('description'),
                            targetMoney: this.form.getFieldValue('targetMoney'),
                            discountMoney: this.form.getFieldValue('discountMoney'),
                            id: this.couponId,
                            status:this.form.getFieldValue('valid')
                        }
                        // console.log(data);
                        
                        if(this.couponId==0){ // 添加
                            this.addHotelCoupon(data)
                        }else{ // 修改
                            this.updateHotelCoupon(data)
                        }
                        // console.log(res);
                        this.set_addCouponVisible(false)
                        this.set_couponVisible(true)
                    }
                }else if(this.form.getFieldValue('type')===this.couponType.Festival){ // 节日特惠
                    if (!err) {
                        const data = {
                            hotelId: this.activeHotelId,
                            name: this.form.getFieldValue('name'),
                            type: this.form.getFieldValue('type'),
                            description: this.form.getFieldValue('description'),
                            startTime: moment(this.form.getFieldValue('date_range')[0].format('YYYY-MM-DD')),
                            endTime: moment(this.form.getFieldValue('date_range')[1].format('YYYY-MM-DD')),
                            discount: this.form.getFieldValue('discount'),
                            id: this.couponId,
                            status:this.form.getFieldValue('valid')
                        }
                        // console.log(data);
                        
                        if(this.couponId==0){ // 添加
                            this.addHotelCoupon(data)
                        }else{ // 修改
                            this.updateHotelCoupon(data)
                        }
                        this.set_addCouponVisible(false)
                        this.set_couponVisible(true)

                    }
                }else if(this.form.getFieldValue('type')===this.couponType.Multi){ // 多间特惠
                    console.log("");
                    
                    if (!err) {
                        const data = {
                            hotelId: this.activeHotelId,
                            name: this.form.getFieldValue('name'),
                            type: this.form.getFieldValue('type'),
                            description: this.form.getFieldValue('description'),
                            discount: this.form.getFieldValue('discount'),
                            id: this.couponId,
                            status:this.form.getFieldValue('valid')
                        }
                        // console.log(data);
                        
                        if(this.couponId==0){ // 添加
                            this.addHotelCoupon(data)
                        }else{ // 修改
                            this.updateHotelCoupon(data)
                        }
                        this.set_addCouponVisible(false)
                        this.set_couponVisible(true)
                    }
                }
            });
        },
        checkTargetMoney(rule, value, callback){
            const re = /[0-9]+(\.[0-9]+)?$/
            if(this.form.getFieldValue('type')!=this.couponType.Target){
                callback()
            }else if (re.test(value)) {
                callback();
            } else {
                callback(new Error('请输入有效的达标金额'));
            }
            callback()
        },
        checkDiscountMoney(rule, value, callback){
            const re = /[0-9]+(\.[0-9]+)?$/
            if(this.form.getFieldValue('type')!=this.couponType.Target){
                callback()
            }else if (!re.test(value)) {
                callback(new Error('请输入有效的优惠金额'));
            }else{
                callback();
            }
            callback()
        },
        checkDiscount(rule,value,callback){
            const re = /[0-9]+(\.[0-9]+)?$/
            if(this.form.getFieldValue('type')==this.couponType.Target){
                callback()
            }else if (!re.test(value)) {
                callback(new Error('请输入有效的优惠金额'));
            }else if(value >=10 || value <=0){
                callback(new Error('折扣必须在0-10之间'));
            }else{
                callback()
            }
            callback()
        },
        checkDateRange(rule,value,callback){
            if(this.form.getFieldValue('type')!=this.couponType.Festival){
                callback()
            }else if(value==null){
                callback(new Error('请输入优惠有效时间'))
            }
            callback()
        }
    }
}
</script>