<template>
  <div>
    <a-modal
      :visible="visible"
      title="撤销订单"
      :footer="null"
      :closable="false"
    >
      <a-result status="warning" title="请确认撤销订单">
        <div class="desc" v-if="userInfo.userType=='Client'">
            <p style="font-size: 16px;">
              <strong>本次撤销将会扣除您{{cancelVO.changeNum}}信用值</strong >
            </p>
            <p>
                您当前信用值为{{userInfo.credit}}
            </p>            
            <p>
                撤销后您的信用值为{{cancelVO.result}}
            </p>
            <p>
              联系酒店协商撤销，可免除信用值扣除惩罚.
            </p>
        </div>
        <div class="desc" v-else>
            <p style="font-size: 16px;">
              <strong>本次撤销将不会信用值</strong >
            </p>
            <p>
              确认撤销后将无法更改,请与客户协商确认后撤销.
            </p>
        </div>
        <template #extra>
            <a-button key="buy" @click="hide">
            放弃撤销
            </a-button>
            <a-button key="console" type="danger" @click="confirmCancel">
             确认撤销
            </a-button>
        </template>
      </a-result>
    </a-modal>
  </div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
export default {
  data() {
    return {
      visible: false,
      // cancelVO的值通过orderList父传子赋值来的
      cancelVO: '',
    };
  },
  computed:{
    ...mapGetters(['userInfo','userId'])
  },
  methods: {
    ...mapActions([
        'cancelOrder',
        'getUserOrders',
        'updateCredit',
        'getManagerOrders',
        'getAllOrders'
    ]),
    showModal() {
      this.visible = true
    },
    hide() {
      this.visible = false
    },
    async confirmCancel(){
        await this.cancelOrder(this.cancelVO.orderId)
        if(this.userInfo.userType=='Client'){
          await this.updateCredit(this.cancelVO)
          await this.getUserOrders(this.cancelVO.userId)
        }else if(this.userInfo.userType=='HotelManager'){
        // }else{
          await this.getManagerOrders(this.userId)
        }else if (
          this.userInfo.userType == "Admin" ||
          this.userInfo.userType == "Manager"
        ) {
          await this.getAllOrders();
        }
        
        this.hide()
    },
  },
};
</script>
<style scoped>
.desc p {
  margin-bottom: 1em;
}
</style>