<template>
<div class="wrapper">
  <a-table :columns="columns" :data-source="creditChangeList" bordered rowKey="id">
    <template slot="action" slot-scope="action">
      {{ action_map[action] }}
    </template>    
    <template slot="sign" slot-scope="sign">
      <a-tag color="red" v-if="sign=='SUB'">减少</a-tag>
      <a-tag color="green" v-if="sign=='ADD'">增加</a-tag>
    </template>    
    <template slot="order" slot-scope="orderId">
      <a @click="jumpToDetail(orderId)"> 查看相关订单</a>
    </template>
  </a-table>
</div>
</template>

<script>
import {mapActions,mapGetters} from 'vuex'
import moment from 'moment'
const columns = [
  {
    title: '时间',
    dataIndex: 'time',
    sorter:(a,b)=> moment(a.time).diff(moment(b.time),"seconds")
  },
  {
    title: '变化原因',
    dataIndex: 'action',
    scopedSlots: { customRender: 'action' },
    filters: [{ text: '初始化', value: 'INIT' }, { text: '执行订单', value: 'EXECUTE' },
              { text: '撤销订单', value: 'ANNUL' },{ text: '异常订单', value: 'EXCEPTION' },{ text: '充值', value: 'TOPUP' }],
    onFilter: (value, record) => record.action.includes(value),
  },
  {
    title: '信用值变化',
    dataIndex: 'sign',
    scopedSlots: { customRender: 'sign' },
    filters: [{ text: '增加', value: 'ADD' }, { text: '减少', value: 'SUB' }],
    onFilter: (value, record) => record.sign.includes(value),
  },  
  {
    title: '变化额度',
    dataIndex: 'changeNum',
  },  
  {
    title: '变化后额度',
    dataIndex: 'result',
  },
  {
    title: '查看订单',
    dataIndex: 'orderId',
    scopedSlots: { customRender: 'order' },
  },  
];
export default {
  data() {
    return {
      columns,
      action_map:{
        'INIT':'初始化',
        'EXECUTE':'执行订单',
        'ANNUL':'撤销订单',
        'EXCEPTION':'异常订单',
        'TOPUP':'充值',
      },
      sign_map:{
        'ADD':'增加',
        'SUB':'减少'
      }
    };
  },
  computed:{
    ...mapGetters(['creditChangeList'])
  },
  methods:{
    ...mapActions(['getUserCreditChangeList']),
    jumpToDetail(orderId){
      this.$router.push({ name: 'orderDetail', params: { orderId: orderId } })
    }
  },
  mounted(){
    this.getUserCreditChangeList()
  }
};
</script>
<style scoped>
.wrapper{
  margin-top: 30px;
}
th.column-money,
td.column-money {
  text-align: right !important;
}
</style>