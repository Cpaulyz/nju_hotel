<template>
  <div class="detail-wrapper">
    <a-descriptions title="订单详情" :size="size">
      <a-descriptions-item label="订单号">
        {{ currentOrderInfo.id }}
      </a-descriptions-item>
    </a-descriptions>
    <a-descriptions bordered title="" :size="size">
      <a-descriptions-item label="客户ID">
        {{ currentOrderInfo.userId }}
      </a-descriptions-item>
      <a-descriptions-item label="酒店名称">
        {{ currentOrderInfo.hotelName }}
      </a-descriptions-item>
      <a-descriptions-item label="订单状态">
        <!-- {{currentOrderInfo.orderState}} -->
        {{ ordertype[currentOrderInfo.orderState] }}
      </a-descriptions-item>
      <a-descriptions-item label="房间类型">
        {{ roomType_map[currentOrderInfo.roomType] }}
      </a-descriptions-item>
      <a-descriptions-item label="入住时间">
        {{ currentOrderInfo.checkInDate }}
      </a-descriptions-item>
      <a-descriptions-item label="退房时间">
        {{ currentOrderInfo.checkOutDate }}
      </a-descriptions-item>
      <a-descriptions-item label="入住信息">
        入住人数: {{ currentOrderInfo.peopleNum }}
        <br />
        是否携带儿童:
        <span v-if="currentOrderInfo.haveChild">是</span>
        <span v-else>否</span>
        <br />
        入住人姓名: {{ currentOrderInfo.clientName }}
        <br />
        入住人电话: {{ currentOrderInfo.phoneNumber }}
      </a-descriptions-item>
    </a-descriptions>
    <br />
    <br />
    <a-descriptions title="" :size="size">
      <a-descriptions-item label="创建时间">
        {{ currentOrderInfo.createDate }}
      </a-descriptions-item>
      <a-descriptions-item label="房间数量">
        {{ currentOrderInfo.roomNum }}
      </a-descriptions-item>
      <a-descriptions-item label="总价">
        ${{ currentOrderInfo.price }}
      </a-descriptions-item>
    </a-descriptions>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
export default {
  data() {
    return {
      size: "default",
      ordertype: {
        Ordered: "已下单",
        Executed: "已入住",
        Annulled: "已撤销",
        Exception: "异常",
        Evaluated: "已评价",
      },
      roomType_map: {
        BigBed: "大床房",
        DoubleBed: "双床房",
        Family: "家庭房",
      },
    };
  },
  computed: {
    ...mapGetters(["currentOrderInfo"]),
  },
  async mounted() {
    await this.getOrderDetail(Number(this.$route.params.orderId));
    console.log(this.currentOrderInfo);
  },
  beforeRouteUpdate(to, from, next) {
    //TODO: 这里可能待修改
    this.getOrderDetail(Number(to.params.orderId));
    next();
  },
  methods: {
    ...mapMutations(["set_currentHotelId"]),
    ...mapActions(["getOrderDetail", "getUserInfo"]),
    onChange(e) {
      console.log("size checked", e.target.value);
      this.size = e.target.value;
    },
  },
};
</script>

<style scoped lang="less">
.detail-wrapper {
  padding: 50px;
}
</style>
