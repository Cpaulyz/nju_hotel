<template>
  <div class="info-wrapper">
    
    <a-form :form="form">
      <a-form-item
        label="用户名"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <a-input
          placeholder="请填写用户名"
          v-decorator="[
            'userName',
            { rules: [{ required: true, message: '请输入用户名' }] },
          ]"
          v-if="modify"
        />
        <span v-else>{{ userInfo.userName }}</span>
      </a-form-item>
      <a-form-item
        label="邮箱"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <span>{{ userInfo.email }}</span>
      </a-form-item>

      <a-form-item
        label="手机号"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <a-input
          placeholder="请填写手机号"
          v-decorator="[
            'phoneNumber',
            { rules: [{ validator: this.checkPhoneNum }] },
          ]"
          v-if="modify"
        />
        <span v-else>{{ userInfo.phoneNumber }}</span>
      </a-form-item>
      <a-form-item
        label="信用值"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <span>{{ userInfo.credit }}</span>
        <a id="credit" @click="checkCredit" v-if="userInfo.userType == 'Client'"
          >信用变化记录</a
        >
      </a-form-item>
      <a-form-item
        label="头像"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <uploadAvatar></uploadAvatar>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
        <a-button type="primary" @click="saveModify">
          保存
        </a-button>
        <a-button
          type="default"
          style="margin-left: 30px"
          @click="cancelModify"
        >
          取消
        </a-button>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
        <a-button type="primary" @click="modifyInfo">
          修改信息
        </a-button>
        <a-divider type="vertical" />
        <a-button type="primary" @click="modifyPsw">
          修改密码
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script>
import uploadAvatar from './components/uploadAvator'
import { mapGetters, mapMutations, mapActions } from "vuex";
export default {
  name: "info",
  data() {
    return {
      modify: false,
      formLayout: "horizontal",
      pagination: {},
      data: [],
      form: this.$form.createForm(this, { name: "coordinated" }),
    };
  },
  components: {uploadAvatar},
  computed: {
    ...mapGetters(["userId", "userInfo"]),
  },
  async mounted() {
    await this.getUserInfo();
  },
  methods: {
    ...mapActions([
      "getUserInfo",
      "getUserOrders",
      "updateUserInfo",
      "cancelOrder",
    ]),
    saveModify() {
      this.form.validateFields((err, values) => {
        if (!err) {
          const data = {
            userName: this.form.getFieldValue("userName"),
            phoneNumber: this.form.getFieldValue("phoneNumber"),
          };
          this.updateUserInfo(data).then(() => {
            this.modify = false;
          });
        }
      });
    },
    modifyInfo() {
      setTimeout(() => {
        this.form.setFieldsValue({
          userName: this.userInfo.userName,
          phoneNumber: this.userInfo.phoneNumber,
        });
      }, 0);
      this.modify = true;
    },
    cancelModify() {
      this.modify = false;
    },
    checkCredit() {
      // 跳转到信用记录页面
      this.$router.push({
        name: "userCredit",
        params: { userId: this.userId },
      });
    },
    checkPhoneNum(rule, value, callback) {
      const re = /^((1[0-9]{1})+\d{9})$/;
      if (re.test(value)) {
        callback();
      } else {
        callback(new Error("请输入有效的电话号码"));
      }
      callback();
    },
    modifyPsw(){
      this.$router.push({
        name: "password",
      });
    }
  },
};
</script>
<style lang="less">
#credit {
  margin-left: 200px;
}
</style>
