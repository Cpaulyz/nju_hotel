<template>
    <a-modal
        :visible="true"
        title="添加网站工作人员账号"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
    <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="用户邮箱">
                <a-input
                    v-decorator="[
                        'email',
                        { rules: [{validator:this.checkEmail}] }
                    ]"
                />
            </a-form-item>        
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input
                    v-decorator="[
                        'userName',
                        { rules: [{required: true, message: '请输入用户名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                    v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码', }] }
                    ]"
                />
            </a-form-item >            
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                    v-decorator="[
                        'phoneNumber',
                        {rules:[{validator:this.checkPhoneNum}]}
                    ]"
                />
            </a-form-item >
    </a-Form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addManagerModal',
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
        }
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addManagerModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addManagerModalVisible',
        ]),
        ...mapActions([
            'addManager',
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
        cancel() {
            this.set_addManagerModalVisible(false)
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        email: this.form.getFieldValue('email'),
                        password: this.form.getFieldValue('password'),
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                    }
                    this.addManager(data)
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
    }
}
</script>