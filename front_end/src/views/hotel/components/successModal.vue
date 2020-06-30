<template>
    <div>
        <a-modal
            :visible="orderSuccessModalVisible"
            title="预订成功"
            :footer="null"
            :closable="false"
            >
            <a-result
                status="success"
                title="酒店预订成功!"
                sub-title="点击下方按钮可查看订单或继续浏览酒店页面."
            >
                <template #extra>
                <a-button key="console" @click="jumpToOrder" type="primary">
                    查看订单
                </a-button>
                <a-button key="buy" @click ="hide">
                    继续浏览
                </a-button>
                </template>
            </a-result>
        </a-modal>
    </div>
</template>

<script>
import{mapGetters,mapMutations} from 'vuex'
export default {
    data() {
        return {
            visible:false
        }
    },
    computed:{
        ...mapGetters([
            'userId',
            'orderSuccessModalVisible'
        ])
    },
    methods:{
        ...mapMutations([
            'set_orderSuccessModalVisible',
        ]),
        hide(){
            this.set_orderSuccessModalVisible(false)
        },
        jumpToOrder(){
            this.$router.push({ name: 'orderList', params: { userId: this.userId } })
            this.set_orderSuccessModalVisible(false)
        }

    }
}
</script>