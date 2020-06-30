<template>
<div class="wrapper">
  <div class="main">
      <div class="top">
        <div class="header">
          <div>
            <span class="title">酒店管理系统</span>
          </div>
        </div>
        <div class="desc">
          
        </div>
      </div>
    <a-form
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
      :form="form"
    >
      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
        @change="handleTabClick"
      >
        <a-tab-pane key="tab1" tab="账号密码登录">
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="邮箱"
              v-decorator="[
                'username',
                {rules: [{ required: true, message: '请输入邮箱地址' }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input-password
              size="large"
              autocomplete="false"
              placeholder="密码"
              v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input-password>
          </a-form-item>
          <a-form-item style="margin-top:24px">
            <a-button
              size="large"
              type="primary"
              class="login-button"
              :loading="loginLoading"
              @click="handlelogin()"
            >确定</a-button>
          </a-form-item>
        </a-tab-pane>

        <a-tab-pane key="tab2" tab="注册新账号">
          <a-form-item>
            <a-input
              size="large"
              type="email"
              placeholder="邮箱"
              v-decorator="[
              'registerUserMail', 
              {rules: [{ required: true, type: 'email', message: '请输入邮箱' }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input
              size="large"
              placeholder="用户名"
              v-decorator="[
              'registerUsername', 
              {rules: [{ required: true, message: '请输入用户名' }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
           <a-form-item>
            <a-input
              size="large"
              placeholder="手机号"
              v-decorator="[
              'registerPhoneNumber', 
              {rules: [{ required: true, message: '请输入手机号' },{ validator: this.checkPhoneNum }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="book" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input
              size="large"
              type="password"
              placeholder="密码"
              v-decorator="[
                'registerPassword', 
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input
              size="large"
              type="password"
              placeholder="确认密码"
              v-decorator="[
                'registerPasswordconfirm', 
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePasswordCheck }], validateTrigger: 'blur'}]">
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
           <a-form-item style="margin-top:24px">
            <a-button
              size="large"
              type="primary"
              class="login-button"
              :loading="registerLoading"
              @click="handleRegister()"
            >确定</a-button>
          </a-form-item>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </div>
</div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'

export default {
  name: 'login',
  components: {
    
  },
  data () {
    return {
      customActiveKey: 'tab1',
      loginLoading: false,
      registerLoading: false,
      form: this.$form.createForm(this),
    }
  },
  computed: {
    ...mapGetters([
      'token'
    ])
  },
  mounted() {

  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    },
  },
  methods: {
    ...mapActions([
      'login',
      'register'
      ]),

    // handler
    handleUsernameOrEmail (rule, value, callback) {
      const { state } = this
      const regex = /^([a-zA-Z0-9_-])+@(([a-zA-Z0-9_-])+\.)+(([a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        callback()
      } else {
        callback(new Error('请输入有效用户名或邮箱'))
      }
      callback()
    },
    checkEmail(rule, value, callback) {
        const re = /^([a-zA-Z0-9_-])+@(([a-zA-Z0-9_-])+\.)+(([a-zA-Z0-9_-]{2,3}){1,2})$/
        if (re.test(value)) {
            callback();
        } else {
            callback(new Error('请输入有效邮箱'));
        }
        callback()
    },
    handlePassword(rule, value, callback) {
      if (value.length < 6) {
        callback(new Error('密码长度至少6位'))
      }
      callback()
    },
    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('registerPassword')
      console.log(password)
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
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
    handleTabClick (key) {
      this.customActiveKey = key
    },
    handlelogin() {
      const validateFieldsKey = this.customActiveKey === 'tab1' ? ['username', 'password'] : ['registerUsername', 'registerUserMail','registerPassword','registerPasswordconfirm']
      this.form.validateFields(validateFieldsKey, { force: true }, async (err, values) => {
        if(!err){
          this.loginLoading = true
          const data = {
            email: this.form.getFieldValue("username"),
            password: this.form.getFieldValue("password")
          }
          await this.login(data)
          this.loginLoading = false
        }
      })
    },

    handleRegister() {
      const { form: { validateFields } } = this
      const validateFieldsKey = this.customActiveKey === 'tab1' ? ['username', 'password'] : ['registerUsername','registerPhoneNumber','registerUserMail','registerPassword','registerPasswordconfirm']
      validateFields(validateFieldsKey, { force: true }, async (err, values) => {
        if (!err) {
          this.registerLoading = true
          const data = {
            email: this.form.getFieldValue('registerUserMail'),
            password: this.form.getFieldValue('registerPassword'),
            phoneNumber: this.form.getFieldValue('registerPhoneNumber'),
            userName: this.form.getFieldValue('registerUsername'),
            credit: 100,
            userType: 0
          }
          await this.register(data).then(() => {
            this.customActiveKey = 'tab1'
            this.form.setFieldsValue({
              'registerUserMail': '',
              'registerPassword': '',
              'registerPasswordconfirm': ''
            })
          })
          this.registerLoading = false
        }
      });
    }
  }
}
</script>

<style lang="less" scoped>
.wrapper{
  height: 100%;
  background: url('../assets/white-waves.png');
  padding-top: 100px;
}
.main{
    width: 484px;
    height: 541px;
    margin: 0 auto;
    background: url('../assets/login.png');
    .top {
        text-align: center;
        height: 114px;
        .header {
          height: 114px;
          line-height: 114px;
          margin-left: 250px;
          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }

          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }

          .title {
            font-size: 31px;
            color: #ffffff;
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
        .desc {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.45);
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }
}
.user-layout-login {
  padding: 0px 50px;
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
