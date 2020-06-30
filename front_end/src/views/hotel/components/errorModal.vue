<template>
    <div>
        <a-modal
            :visible="visible"
            title="预订失败"
            :footer="null"
            :closable="false"
            >
            <a-result
                status="error"
                title="您的预订请求被拒绝"
                sub-title="Please check the following information before resubmitting."
            >
                <template #extra>
                <a-button key="buy" @click="hide">
                    继续看看
                </a-button>
                </template>

                <div class="desc">
                <p style="font-size: 16px;">
                    <strong>您被拒绝预订的原因可能为:</strong>
                </p>
                <p>
                    <a-icon :style="{ color: 'red' }" type="close-circle" /> 您的信用值过低
                    <a @click="jumpToInfo">查看个人中心;</a>
                </p>
                <p>
                    <a-icon :style="{ color: 'red' }" type="close-circle" /> 该酒店暂时无法预订
                </p>
                </div>
            </a-result>
        </a-modal>
    </div>
</template>

<script>
import{mapGetters} from 'vuex'
export default {
    data() {
        return {
            visible:false
        }
    },
    computed:{
        ...mapGetters([
            'userId',
        ])
    },
    methods:{
        hide(){
            this.visible = false
        },
        show(){
            this.visible = true
        },
        jumpToInfo(){
            this.$router.push({ name: 'userInfo', params: { userId: this.userId } })
        }

    }
}
</script>