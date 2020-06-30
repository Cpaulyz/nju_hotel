<template>
  <div class="manageUser-wrapper">
    <a-layout>
      <a-layout-sider width="210" style="background: #fff">
        <a-menu
          style="width: 200px"
          :default-selected-keys="['1']"
          mode="inline"
        >
          <a-menu-item key="1" @click="changeToUser">
            <a-icon type="user" />
            用户管理
          </a-menu-item>
          <a-menu-item key="2" @click="changeToHotel">
            <a-icon type="shop" />
            酒店管理
          </a-menu-item>
          <a-menu-item key="3" @click="changeToOrder">
            <a-icon type="carry-out" />
            订单管理
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout-content id="content" >
        <transition name="panel-transform" mode="out-in">
          <ManageUser v-if="manage == 'user'"></ManageUser>
          <ManageHotel v-if="manage == 'hotel'"></ManageHotel>
          <ManageOrder v-if="manage=='order'"></ManageOrder>
        </transition>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import ManageHotel from "../hotelManager/manageHotel";
import ManageUser from "./manageUser";
import ManageOrder from "../order/orderList" // 复用一下orderList就行了
export default {
  data() {
    return {
      manage: "user",
    };
  },
  components: {
    ManageHotel,
    ManageUser,
    ManageOrder
  },
  methods: {
    changeToUser() {
      this.manage = "user";
    },
    changeToHotel() {
      this.manage = "hotel";
    },
    changeToOrder() {
      this.manage = "order";
    },
  },
};
</script>

<style lang="less" scoped>
.manageUser-wrapper {
  .chart {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px;
  }
}
#content {
  margin-top: 0px;
  padding: 0px;
  background: #fff
}

panel-transform .panel-transform-leave-active,
.panel-transform-enter-active {
  transition: all 0.5s;
}

.panel-transform-enter {
  opacity: 0;
}

.panel-transform-leave-to {
  opacity: 0;
}
</style>
