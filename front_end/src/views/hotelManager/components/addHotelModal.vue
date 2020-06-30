<template>
    <a-modal
        :visible="true"
        title="添加酒店并注册酒店工作人员"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
    <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="酒店名称">
                <a-input
                    placeholder="请填写酒店名称"
                    v-decorator="[
                        'hotelName',
                        { rules: [{required: true, message: '请输入酒店名称', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写酒店地址"
                    v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }] }]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="酒店地区">
                <a-cascader
                :options="options" placeholder="请选择所在地区"
                    v-decorator="[
                        'bizRegion',
                        { rules: [{ required: true, message: '请选择地区' }] }
                    ]"
                />
            </a-form-item >
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                    placeholder="请选择酒店星级"
                    v-decorator="[
                    'hotelStar', 
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                >
                  <a-select-option value="Three">三星级</a-select-option>
                  <a-select-option value="Four">四星级</a-select-option>
                  <a-select-option value="Five">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                    type="textarea"
                    :rows="4"
                    placeholder="请填写酒店简介"
                    v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介' }] }]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="邮箱">
                <a-input
                    placeholder="邮箱将注册为酒店工作人员账号"
                    v-decorator="[
                        'email',
                        { rules: [{validator:this.checkEmail}] }
                    ]"
                />
            </a-form-item>     
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                    placeholder="请填写酒店联系方式"
                    v-decorator="[
                        'phoneNumber',
                        { rules: [{validator:this.checkPhone}] }
                    ]"
                />
            </a-form-item >
    </a-Form>
    </a-modal>
</template>
<script>
import options from '@/utils/cities'
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addHotelModal',
    data() {
        return {
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
            options
        }
    },
    computed: {
        ...mapGetters([
            'addHotelModalVisible',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addHotelModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addHotelModalVisible',
        ]),
        ...mapActions([
            'addHotelAndHotelManager',
            'getHotelList'
        ]),
        checkEmail(rule,value,callback){
            const re = /^([a-zA-Z0-9_-])+@(([a-zA-Z0-9_-])+\.)+(([a-zA-Z0-9_-]{2,3}){1,2})$/
            if (!re.test(value)) {
                callback(new Error('请输入有效的邮箱地址'));
            }else{
                callback()
            }
            callback()
        },
        checkPhone(rule, value, callback){
            const re = /^((1[0-9]{1})+\d{9})$/
            if (re.test(value)) {
                callback();
            } else {
                callback(new Error('请输入有效的电话号码'));
            }
            callback()
        },
        cancel() {
            this.set_addHotelModalVisible(false)
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll(async(err, values) => {
                if (!err) {
                    const data = {
                        hotelManager:{
                            email: this.form.getFieldValue('email'),
                            password: '123456',
                            userName: this.form.getFieldValue('hotelName')+'工作人员',
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                        },
                        hotel:{
                            name: this.form.getFieldValue('hotelName'),
                            bizRegion:this.form.getFieldValue('bizRegion').join(' '), 
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            address: this.form.getFieldValue('address'),
                            description: this.form.getFieldValue('description'),
                            phoneNum: this.form.getFieldValue('phoneNumber'),
                            managerId:''
                        },
                    }
                    await this.addHotelAndHotelManager(data)
                    await this.getHotelList()
                }
            });
        }
    }
}
</script>