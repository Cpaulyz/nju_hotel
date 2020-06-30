<template>
  <div class="manageHotel-wrapper">
    <div style="width: 100%; text-align: right; margin:20px 0">
      <a-button type="primary" @click="addHotel" v-if="roles=='Admin'"
        ><a-icon type="plus" />添加酒店</a-button>
      <a-divider type="vertical" v-if="roles=='Admin'"></a-divider>
      <a-button type="primary" @click="showWebCoupon"
        >查看网站促销策略</a-button>
    </div>
        <a-table :columns="columns1" :dataSource="hotelList" bordered rowKey="id">
          <span slot="action" slot-scope="record" >
            <a-button type="primary" size="small" @click="showCoupon(record)" v-if="roles=='HotelManager'"
              >优惠策略</a-button>
            <a-divider type="vertical" v-if="roles=='HotelManager'"></a-divider>
            <a-button type="primary" size="small" @click="checkRoom(record)" v-if="roles=='HotelManager'"
              >查看房间</a-button>
            <a-divider type="vertical" v-if="roles=='HotelManager'"></a-divider>
            <a-button type="primary" size="small" @click="addRoom(record)" v-if="roles=='HotelManager'"
              >录入房间</a-button>        
            <a-divider type="vertical" v-if="roles=='HotelManager'"></a-divider>
            <a-button type="primary" size="small" @click="updateHotel(record)"
              >修改酒店信息</a-button>
          </span>
        </a-table>
    <CheckRoomModal ref="checkRoomModal"></CheckRoomModal>
    <UpdateHotelModal ref="updateModal"></UpdateHotelModal>
    <AddRoomModal></AddRoomModal>
    <Coupon></Coupon>
    <AddHotelModal v-if="addHotelModalVisible"></AddHotelModal>
  </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import CheckRoomModal from './components/checkRoomModal'
import UpdateHotelModal from './components/updateHotelInfoModal'
import AddRoomModal from "./components/addRoomModal";
import AddHotelModal from './components/addHotelModal'
import orderList from "../order/orderList";
import Coupon from "./components/coupon";
const moment = require("moment");
const columns1 = [
  {
    title: "酒店名",
    dataIndex: "name",
  },
  {
    title: "地区",
    dataIndex: "bizRegion",
  },
  {
    title: "地址",
    dataIndex: "address",
  },
  {
    title: "酒店星级",
    dataIndex: "hotelStar",
  },
  {
    title: "评分",
    dataIndex: "rate",
  },
  {
    title: "简介",
    dataIndex: "description",
  },
  {
    title: "操作",
    key: "action",
    scopedSlots: { customRender: "action" },
  },
];
export default {
  name: "manageHotel",
  data() {
    return {
      formLayout: "horizontal",
      pagination: {},
      columns1,
      form: this.$form.createForm(this, { name: "manageHotel" }),
    };
  },
  components: {
    AddRoomModal,
    Coupon,
    UpdateHotelModal,
    AddHotelModal,
    CheckRoomModal
  },
  computed: {
    ...mapGetters([
      "userId",
      "hotelList",
      "addRoomModalVisible",
      "activeHotelId",
      "couponVisible",
      "roles",
      "addHotelModalVisible"
    ]),
  },
  async mounted() {
    if(this.roles=='HotelManager')
      await this.getManagerHotelList(this.userId);
    else{
      await this.getHotelList()
    }
  },
  methods: {
    ...mapMutations([
      "set_addRoomModalVisible",
      "set_couponVisible",
      "set_activeHotelId",
      "set_activeHotelName",
      "set_addHotelModalVisible",
      'set_currentHotelId'
    ]),
    ...mapActions(["getHotelCoupon",'getManagerHotelList','getHotelCoupon','getWebCoupon','getHotelList']),
    addRoom(record) {
      this.set_activeHotelName(record.name);
      this.set_activeHotelId(record.id);
      this.set_addRoomModalVisible(true);
    },
    checkRoom(record){
      // 查看房间
      this.set_currentHotelId(record.id)
      this.$refs['checkRoomModal'].show()
    },
    showCoupon(record) {
      this.set_activeHotelName(record.name);
      this.set_activeHotelId(record.id);
      this.set_couponVisible(true);
      this.getHotelCoupon(record.id)
    },
    showWebCoupon(){
      // 查看网站优惠策略
      // 和showCoupon类似，但是是酒店，用特殊的hotelId=0来表示
      this.set_activeHotelName("网站");
      this.set_activeHotelId(0);
      this.set_couponVisible(true);
      this.getWebCoupon()
    },
    updateHotel(record){
      // 传预留信息给子组件id
      this.$refs['updateModal'].show()
      this.$refs['updateModal'].hotelVO.address = record.address
      this.$refs['updateModal'].hotelVO.description = record.description
      this.$refs['updateModal'].hotelVO.id = record.id
      this.$refs['updateModal'].hotelVO.bizRegion = record.bizRegion.split(' ')
      this.$refs['updateModal'].hotelVO.picture = record.picture
    },
    addHotel() {
      this.set_addHotelModalVisible(true);
    },
  },
};
</script>
<style scoped lang="less">
.manageHotel-wrapper {
  .chart {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px;
  }
}
</style>
<style lang="less">
.manageHotel-wrapper {
  .ant-tabs-bar {
    padding-left: 30px;
  }
}
</style>
<style lang="less"></style>
