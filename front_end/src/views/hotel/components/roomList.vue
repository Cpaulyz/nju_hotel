<template>
    <div class="room-list">
        <div class="filter">
             <a-form :form="form" @submit="reSearchRoom" layout="inline">
                <a-form-item v-bind="formItemLayout" label="入住日期">
                    <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                            'date', 
                            {
                                rules: [{validator:this.checkRangeTime}]   
                            }
                        ]"
                        :placeholder="['入住日期','退房日期']"
                    />
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" html-type="submit">
                        重新搜索
                    </a-button>
                </a-form-item>
            </a-form>
        </div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="roomList.filter(item => item.curNum>0)"
                rowKey="id"
            >
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="roomType" slot-scope="roomType">
                    <span>{{ type_map[roomType] }}</span>
                </span>
                <span slot="action" slot-scope="text, record">
                    <a-button type="primary" @click="order(record)">立即预定</a-button>
                </span>
            </a-table>
        </div>
        <OrderModal v-if="orderModalVisible"></OrderModal>
        <SuccessModal></SuccessModal>
        <ErrorModal ref="error"></ErrorModal>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import moment from 'moment'
import OrderModal from './orderModal'
import SuccessModal from './successModal'
import ErrorModal from './errorModal'
const columns = [
    {  
      title: '房型',
      dataIndex: 'roomType',
      key: 'roomType',
      scopedSlots: { customRender: 'roomType'}
    },
    {
      title: '房价',
      key: 'price',
      dataIndex: 'price',
      scopedSlots: { customRender: 'price'}
    },    
    {
      title: '剩余房间',
      key: 'curNum',
      dataIndex: 'curNum'
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name:'roomList',
    data() {
        return {
            columns,
            formItemLayout: {

            },
            type_map:{
                'BigBed':"大床房",
                'DoubleBed':"双床房",
                'Family':"家庭房"
            }
        }
    },
    components: {
        OrderModal,
        SuccessModal,
        ErrorModal,
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'userInfo',
            'roomList'
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'roomList' });
    },
    mounted(){
        var defaultDate = [moment(),moment().add(1,'days')]
        this.form.setFieldsValue({date:defaultDate})
        this.set_currentHotelRoomDate(defaultDate)
        this.getRoomList()
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_currentOrderRoom',
            'set_currentHotelRoomDate'
        ]),
        ...mapActions([
            'getRoomList'
        ]),
        order(record) {
            if(this.userInfo.userType!='Client'){
                this.userTypeWarning()
            }else if(this.userInfo.credit <= 0){
                this.$refs['error'].show()
            }else{
                this.set_currentOrderRoom(record)
                this.set_orderModalVisible(true)
            }
        },
        checkRangeTime(rule, value, callback){
            if(value==undefined){
                callback(new Error('请选择入住时间'))
            }
            else if (value.length == 0){
                callback(new Error('请选择入住时间'))
            }
            else if(moment(value[1]).format('YYYY-MM-DD')===moment(value[0]).format('YYYY-MM-DD')){
                callback(new Error('退房时间必须在入住时间以后'));
            } else if(moment(value[0]).format('YYYY-MM-DD')<moment().format('YYYY-MM-DD')){
                callback(new Error('入住时间必须在当前日期以后'));
            } else if(moment(value[1]).diff(moment(value[0]),'days')>20){
                callback(new Error('超过20天的订单请分开预定'));
            } else if(moment(value[1]).diff(moment(),'days')>30){
                callback(new Error('只能预定30天以内的订单'))
            } else{
                callback()
            }
            callback()
        },
        reSearchRoom(e) {
            // 修改入住时间后重新搜索
            
            e.preventDefault();
            this.form.validateFieldsAndScroll(async(err, values) => {
                if (!err) {
                    const data = this.form.getFieldValue('date')
                    await this.set_currentHotelRoomDate(data)
                    await this.getRoomList()
                }
            })
        },
        userTypeWarning() {
            // 如果不是客户要预订酒店会报错，弹窗提示
            this.$warning({
                title: '您当前账户无法预定酒店',
                content: '请使用客户账户进行登录后重试',
            });
        },
    }
}
</script>