<template>
  <div>
    <a-modal
      :visible="show"
      title="评价订单"
      @ok="handleOk"
      @cancel="cancel"
      cancelText="以后再说"
      okText="提交"
    >
      <a-form-model
        :model="form"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-form-model-item label="满意度">
          <a-rate class="star" v-model="form.star" allow-half />
        </a-form-model-item>
        <a-form-model-item label="评价">
          <a-input v-model="form.comment" type="textarea" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import {mapActions,mapGetters} from 'vuex'
import moment from 'moment'
export default {
  data() {
    return {
      show: false,
      labelCol: { span: 5 },
      wrapperCol: { span: 15 },
      form: {
        star:5,
        comment: "",
      },
      orderId:0,
      hotelId:0,
    };
  },
  computed:{
    ...mapGetters([
      'userInfo',
      'userId'
    ])
  },
  methods: {
    ...mapActions([
      'addComment',
      'getUserOrders',
      'getManagerOrders'
    ]),
    showModal() {
      this.show = true;
    },
    async handleOk() {
        var data = {
          hotelId:this.hotelId,
          orderId:this.orderId,
          grade:this.form.star,
          content:this.form.comment,
          userName:this.userInfo.userName,
          createTime:moment().format('YYYY-MM-DD HH:mm:ss')
        }
        if(this.form.comment==""){
          console.log("默认评价");
          data.content = "系统默认评价"
        }
        await this.addComment(data)
        // 提交评价完，要刷新页面，重新获取数据以更新状态
        if(this.userInfo.userType=='Client'){
          await this.getUserOrders(this.userId)
        }else if(this.userInfo.userType=='HotelManager'){
          await this.getManagerOrders(this.userId)
        }
        this.cancel()
    },
    cancel() {
      this.show = false;
      this.form={
        star:5,
        comment: "",
      }
    },
    set_orderId(id){
      this.orderId = id
    },
    set_hotelId(id){
      this.hotelId = id
    },
  },
};
</script>

<style lang="css">
.star{
    padding-left: 10px;
}
</style>