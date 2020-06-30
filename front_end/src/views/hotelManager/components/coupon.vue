<template>
   <div>
    <a-modal
        :visible="couponVisible"
        :title="hoetlName"
        width="1300px"
        :footer="null"
        @cancel="cancel"
    >
        <a-button  id="addCoupon" type="primary" @click="addCoupon()" v-if="allowAdd"><a-icon type="plus"/>添加优惠策略</a-button>
        <a-table :columns="columns" :dataSource="couponList" rowKey="id">
            <span slot="tags" slot-scope="type">
                <a-tag
                    :color="type == 'Target' ? 'volcano' :  type=='Multi'?'geekblue':'orange'"
                    :key="type"
                >
                    {{ type_map[type] }}
                </a-tag>
            </span>
            <span slot="scope" slot-scope="hotelId">
                <a-tag v-if="hotelId==0" color="purple">网站通用</a-tag>
                <a-tag v-else color="cyan">仅限酒店</a-tag>
            </span>
            <span slot="status_tags" slot-scope="status">
                <a-tag
                    :color="status === 1 ? 'green' :  ''"
                    :key="status"
                >
                    {{ status_map[status] }}
                </a-tag>
            </span>
            <span slot="discount" slot-scope="discount,record">
                <span v-if="record.couponType==='Target'">/</span>
                <span v-else>{{discount}}</span>
            </span>            
            <span slot="targetMoney" slot-scope="targetMoney,record">
                <span v-if="record.couponType==='Target'">{{targetMoney}}</span>
                <span v-else>/</span>
            </span>            
            <span slot="discountMoney" slot-scope="discountMoney,record">
                <span v-if="record.couponType==='Target'">{{discountMoney}}</span>
                <span v-else>/</span>
            </span>
            <span slot="action" slot-scope="id">
                <a @click="handleModifyCoupon(id)" :disabled="!allowAdd">修改</a>
                <a-divider type="vertical" />
                <a @click="handleDeleteCoupon(id)">删除</a>
            </span>
        </a-table>
        <button @click="test"></button>
        <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
    </a-modal>
    <AddCoupon :couponId="couponId" v-if="addCouponVisible"></AddCoupon>
   </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'
import { axios } from '@/utils/request'

const columns = [
    // 这里定义列表头
    {
        title: '优惠类型',
        dataIndex: 'couponType',
        key: 'type',
        scopedSlots: { customRender: 'tags' },
    },
    {
        title: '名称',
        dataIndex: 'couponName',
        key: 'couponName',
    },    
    {
        title: '适用范围',
        dataIndex: 'hotelId',
        key: 'hotelId',
        scopedSlots:{ customRender:'scope'}
    },
    {
        title: '折扣',
        dataIndex: 'discount',
        key: 'discount',
        scopedSlots: { customRender: 'discount' }
    },
    {
        title: '优惠简介',
        key: 'description',
        dataIndex: 'description',
    },
    {
        title: '达标金额',
        dataIndex: 'targetMoney',
        key: 'targetMoney',
        scopedSlots: { customRender: 'targetMoney' }
    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney',
        key: 'discountMoney',
        scopedSlots: { customRender: 'discountMoney' }
    },
    {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        scopedSlots: { customRender: 'status_tags' },
    },
    {
        title: '操作',
        dataIndex:'id',
        key: 'id',
        scopedSlots: { customRender: 'action' },
    }
  ];
export default {
    name: 'coupon',
    data() {
        return {
            columns,
            type_map:{
                'Target':'满减优惠',
                'Festival':'节日优惠',
                'Multi':'多间优惠'
            },
            status_map:{
                1:'有效',
                0:'已失效'
            },
            couponId:0
        }
    },
    async mounted(){  
      console.log(this.couponList);
      
    },
    components: {
        AddCoupon,
    },
    computed: {
        ...mapGetters([
            'couponVisible',
            'couponList',
            'activeHotelName',
            'addCouponVisible',
            'activeHotelId',
            'roles'
        ]),
        hoetlName:function(){
            return this.activeHotelName+"优惠策略"
        },
        allowAdd(){
            if(this.activeHotelId==0){
                return this.roles!='HotelManager'
            }
            return true
        }
    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponVisible',
        ]),
        ...mapActions([
            'getHotelCoupon',
        ]),
        cancel() {
            this.set_couponVisible(false)
        },
        addCoupon() {
            this.couponId = 0
            this.set_addCouponVisible(true),
            this.set_couponVisible(false)
        },
        handleModifyCoupon(couponId){
            this.couponId = couponId
            this.set_addCouponVisible(true),
            this.set_couponVisible(false)
        },
        handleDeleteCoupon(couponId){
            this.$message.warning('系统暂不支持删除优惠券，可通过修改使优惠失效。');
        },
        test(){
            this.getHotelCoupon()
            console.log(this.couponList)
        }
    }  
}  
</script>
<style scoped>
#addCoupon{
    position: relative;
    margin: 10px;
    margin-top: 0px;
    margin-left: 1100px;
}
</style>