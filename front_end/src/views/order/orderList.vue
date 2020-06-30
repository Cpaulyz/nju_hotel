<template>
  <div class="info-wrapper">
    <a-table :columns="columns" :dataSource="orderList" bordered rowKey="id">
      <span slot="price" slot-scope="text">
        <span>￥{{ text }}</span>
      </span>
      <span slot="roomType" slot-scope="text">
        <span v-if="text == 'BigBed'">大床房</span>
        <span v-if="text == 'DoubleBed'">双床房</span>
        <span v-if="text == 'Family'">家庭房</span>
      </span>
      <span slot="orderState" slot-scope="text">
        <a-tag v-if="text == 'Ordered'" color="blue">已下单</a-tag>
        <a-tag v-if="text == 'Annulled'">已撤销</a-tag>
        <a-tag v-if="text == 'Executed'" color="green">已入住</a-tag>
        <a-tag v-if="text == 'Evaluated'" color="cyan">已评价</a-tag>
        <a-tag v-if="text == 'Exception'" color="red">异常</a-tag>
      </span>
      <!-- 以下为搜索功能 -->
      <div
        slot="filterDropdown"
        slot-scope="{
          setSelectedKeys,
          selectedKeys,
          confirm,
          clearFilters,
          column,
        }"
        style="padding: 8px"
      >
        <a-input
          v-ant-ref="(c) => (searchInput = c)"
          :placeholder="`Search ${column.dataIndex}`"
          :value="selectedKeys[0]"
          style="width: 188px; margin-bottom: 8px; display: block;"
          @change="
            (e) => setSelectedKeys(e.target.value ? [e.target.value] : [])
          "
          @pressEnter="
            () => handleSearch(selectedKeys, confirm, column.dataIndex)
          "
        />
        <a-button
          type="primary"
          icon="search"
          size="small"
          style="width: 90px; margin-right: 8px"
          @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
        >
          Search
        </a-button>
        <a-button
          size="small"
          style="width: 90px"
          @click="() => handleReset(clearFilters)"
        >
          Reset
        </a-button>
      </div>
      <a-icon
        slot="filterIcon"
        slot-scope="filtered"
        type="search"
        :style="{ color: filtered ? '#108ee9' : undefined }"
      />
      <template slot="customRender" slot-scope="text, record, index, column">
        <span v-if="searchText && searchedColumn === column.dataIndex">
          <template
            v-for="(fragment, i) in text
              .toString()
              .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
          >
            <mark
              v-if="fragment === searchText"
              :key="i"
              class="highlight"
              >{{ fragment }}</mark
            >
            <template v-else>{{ fragment }}</template>
          </template>
        </span>
        <template v-else>
          {{ text }}
        </template>
      </template>
      <!-- 搜索功能结束 -->
      <span slot="action" slot-scope="record">
        <a-button size="small" @click="jumpToDetail(record.id)"
          >查看详情</a-button
        >
        <a-divider type="vertical"></a-divider>

        <a-button
          type="danger"
          size="small"
          :disabled="record.orderState != 'Ordered'"
          @click="cancelOrder(record)"
          >撤销</a-button
        >

        <a-divider
          type="vertical"
          v-if="userInfo.userType == 'Client'"
        ></a-divider>
        <a-button
          type="primary"
          size="small"
          :disabled="record.orderState != 'Executed'"
          @click="commentOrder(record.id, record.hotelId)"
          v-if="userInfo.userType == 'Client'"
          >评价</a-button
        >
        <a-divider type="vertical"></a-divider>
        <a-popconfirm
          title="您确定客人已入住吗？确认后将完成订单"
          @confirm="confirmOrder(record)"
          okText="确定"
          cancelText="取消"
          :disabled="record.orderState != 'Ordered'"
          v-if="userInfo.userType == 'HotelManager'"
        >
          <a-button
            type="primary"
            size="small"
            :disabled="record.orderState != 'Ordered'"
            >入住</a-button
          >
        </a-popconfirm>
      </span>
    </a-table>
    <commentsModal ref="comment"></commentsModal>
    <cancelOrderModal ref="cancel"></cancelOrderModal>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import commentsModal from "./components/commentModal";
import moment from "moment";
import cancelOrderModal from "./components/cancelOrderModal";
import { message } from "ant-design-vue";
export default {
  data() {
    return {
      searchText: "",
      searchInput: null,
      searchedColumn: "",
    };
  },
  components: {
    commentsModal,
    cancelOrderModal,
  },
  computed: {
    ...mapGetters(["userId", "orderList", "userInfo"]),
    // 根据orderList计算酒店List，用于columns中酒店名的筛选
    hotelList() {
      var res = [];
      var added = []; // 用于保存已经加过的酒店，防止重复
      for (let index in this.orderList) {
        if (added.indexOf(this.orderList[index].hotelName) == -1) {
          res.push({
            text: this.orderList[index].hotelName,
            value: this.orderList[index].hotelName,
          });
          added.push(this.orderList[index].hotelName);
        }
      }
      return res;
    },
    // 因为用到了hotelList的computed属性，所以colmns也要放在computed中而不是const
    columns() {
      const c = [
        {
          title: "订单号",
          dataIndex: "id",
          key: "id",
          sorter: (a, b) => a.id - b.id,
        },
        {
          title: "客户ID",
          dataIndex: "userId",
          key: "userId",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) => record.userId.toString().includes(value),
          onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              });
            }
          },
        },
        {
          title: "酒店名",
          dataIndex: "hotelName",
          key: "hotelName",
          filters: this.hotelList,
          onFilter: (value, record) => record.hotelName.includes(value),
        },
        {
          title: "房型",
          dataIndex: "roomType",
          key: "roomType",
          scopedSlots: { customRender: "roomType" },
        },
        {
          title: "入住时间",
          dataIndex: "checkInDate",
          key: "checkInDate",
          scopedSlots: { customRender: "checkInDate" },
          sorter: (a, b) =>
            moment(a.checkInDate).diff(moment(b.checkInDate), "days"),
        },
        {
          title: "离店时间",
          dataIndex: "checkOutDate",
          key: "checkOutDate",
          scopedSlots: { customRender: "checkOutDate" },
          sorter: (a, b) =>
            moment(a.checkOutDate).diff(moment(b.checkOutDate), "days"),
        },
        {
          title: "入住人数",
          dataIndex: "peopleNum",
          key: "peopleNum",
        },
        {
          title: "房价",
          dataIndex: "price",
          key: "price",
        },
        {
          title: "状态",
          filters: [
            { text: "已预订", value: "Ordered" },
            { text: "已撤销", value: "Annulled" },
            { text: "已入住", value: "Executed" },
            { text: "已评价", value: "Evaluated" },
            { text: "异常", value: "Exception" },
          ],
          onFilter: (value, record) => record.orderState.includes(value),
          dataIndex: "orderState",
          key: "orderState",
          scopedSlots: { customRender: "orderState" },
        },
        {
          title: "操作",
          key: "action",
          scopedSlots: { customRender: "action" },
        },
      ];
      return c;
    },
  },
  async mounted() {
    // 该页面会被复用，所以要根据用户类型来获取订单List
    if (this.userInfo.userType == "Client") {
      await this.getUserOrders(this.userId);
    } else if (this.userInfo.userType == "HotelManager") {
      await this.getManagerOrders(this.userId);
    } else if (
      this.userInfo.userType == "Admin" ||
      this.userInfo.userType == "Manager"
    ) {
      await this.getAllOrders();
    } else {
      message.error("加载失败");
    }
  },
  methods: {
    ...mapActions([
      "getUserInfo",
      "getUserOrders",
      "cancelOrder",
      "getManagerOrders",
      "updateCredit",
      "executeOrder",
      "getUserCredit",
      "getAllOrders",
    ]),
    cancelOrder(order) {
      var data = {
        userId: order.userId,
        action: "ANNUL",
        sign: "SUB",
        orderId: order.id,
        changeNum: order.price / 2,
        result:
          this.userInfo.credit - order.price / 2 < 0
            ? 0
            : this.userInfo.credit - order.price / 2,
        time: moment().format("YYYY-MM-DD HH:mm:ss"),
      };
      console.log(data);

      this.$refs["cancel"].showModal();
      this.$refs["cancel"].cancelVO = data;
    },
    jumpToDetail(orderId) {
      this.$router.push({ name: "orderDetail", params: { orderId: orderId } });
    },
    handleSearch(selectedKeys, confirm, dataIndex) {
      confirm();
      this.searchText = selectedKeys[0];
      this.searchedColumn = dataIndex;
    },

    handleReset(clearFilters) {
      clearFilters();
      this.searchText = "";
    },
    commentOrder(orderId, hotelId) {
      console.log(orderId);
      console.log(hotelId);
      // 逻辑待补充，orderId还没传给modal
      this.$refs["comment"].showModal();
      this.$refs["comment"].set_orderId(orderId);
      this.$refs["comment"].set_hotelId(hotelId);
    },
    async confirmOrder(order) {
      console.log(order);
      await this.executeOrder(order.id);
      var nowcredit = await this.getUserCredit(order.userId);
      var data = {
        userId: order.userId,
        action: "EXECUTE",
        sign: "ADD",
        orderId: order.id,
        changeNum: order.price,
        result: nowcredit + order.price,
        time: moment().format("YYYY-MM-DD HH:mm:ss"),
      };
      await this.updateCredit(data);
      if (this.userInfo.userType == "Client") {
        await this.getUserOrders(this.userId);
      } else if (this.userInfo.userType == "HotelManager") {
        await this.getManagerOrders(this.userId);
      }
    },
  },
};
</script>

<style scoped lang="less">
.info-wrapper {
  // padding: 50px;
  .chart {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px;
  }
}
</style>
