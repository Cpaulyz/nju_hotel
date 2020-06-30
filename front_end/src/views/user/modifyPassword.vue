<template>
  <div>
    <a-form :form="form">
      <a-form-item
        label="原密码"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <a-input-password
          placeholder="请填写原密码"
          v-decorator="[
            'oldPassword',
            { rules: [{ required: true, message: '请输入用户名' }] },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="新密码"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <a-input-password
          placeholder="请填写新密码"
          v-decorator="[
            'password',
            { rules: [{ validator: this.checkNewPassword }] },
          ]"
        />
      </a-form-item>
      <a-form-item
        label="确认密码"
        :label-col="{ span: 3 }"
        :wrapper-col="{ span: 8, offset: 1 }"
      >
        <a-input-password
          placeholder="请确认新密码"
          v-decorator="[
            'checkPassword',
            { rules: [{ validator: this.checkOldPassword }] },
          ]"
        />
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
        <a-button type="primary" @click="saveModify">
          保存密码
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import loginVue from "../login.vue";
export default {
  name: "info",
  data() {
    return {
      modify: false,
      formLayout: "horizontal",
      pagination: {},
      data: [],
      form: this.$form.createForm(this, { name: "password" }),
    };
  },
  computed: {
    ...mapGetters(["userId"]),
  },
  methods: {
    ...mapActions(["updatePassword"]),
    saveModify() {
      this.form.validateFields((err, values) => {
        if (!err) {
          const data = {
            id: this.userId,
            oldPassword: this.form.getFieldValue("oldPassword"),
            newPassword: this.form.getFieldValue("password"),
          };
          this.updatePassword(data);
        }
      });
    },
    cancelModify() {},
    // 检查新密码符合标准
    checkNewPassword(rule, value, callback) {
      if (value == null) {
        callback(new Error("请输入新密码"));
      } else if (value.length < 6) {
        callback(new Error("密码长度不得小于6位"));
      }
      callback();
    },
    // 检查两次输入的密码一致
    checkOldPassword(rule, value, callback) {
      var newPsw = this.form.getFieldValue("password");
      if (value != newPsw) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    },
  },
};
</script>
