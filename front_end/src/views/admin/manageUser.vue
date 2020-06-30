<template>
  <div>
    <div style="width: 100%; text-align: right; margin:20px 0">
      <a-button type="primary" @click="addManager"  v-if="roles=='Admin'"
        ><a-icon type="plus" />添加网站工作人员</a-button
      >
    </div>
    <a-table :columns="columns" :dataSource="userList" bordered rowKey="id">
      <span slot="userType" slot-scope="userType">
        <span v-if="userType == 'Client'"
          ><a-tag color="cyan">客户</a-tag></span
        >
        <span v-if="userType == 'Manager'"
          ><a-tag color="blue">网站</a-tag></span
        >
        <span v-if="userType == 'HotelManager'"
          ><a-tag color="purple">酒店</a-tag></span
        >
      </span>
      <!-- 搜索功能 -->
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
            <mark v-if="fragment.toLowerCase() === searchText.toLowerCase()" :key="i" class="highlight">{{
              fragment
            }}</mark>
            <template v-else>{{ fragment }}</template>
          </template>
        </span>
        <template v-else>
          {{ text }}
        </template>
      </template>
      <!-- 搜索功能结束，下面是操作 -->
      <span slot="action" slot-scope="text, record">
        <a-popconfirm
          title="删除操作将不可恢复，删除酒店账户将同时删除酒店，请谨慎操作！"
          @confirm="delete_User(record)"
          okText="确定"
          cancelText="取消"
          v-if="roles=='Admin'"
        >
          <a-button type="danger" size="small"
            >删除用户</a-button>
        </a-popconfirm>
        <a-divider type="vertical" v-if="roles=='Admin'"/>
        <a-popconfirm
          title="是否确定重置密码？初始密码为123456，请提醒用户及时修改密码"
          @confirm="resetPsw(record.id)"
          okText="确定"
          cancelText="取消"
        >
          <a-button type="primary" size="small">重置密码</a-button>
        </a-popconfirm>
        <a-popconfirm
          @confirm="topupCredit(record)"
          okText="确定"
          cancelText="取消"
        >
          <a-icon slot="icon" type="dollar" />
          <div slot="title">
            请输入要充值的信用额度
            <a-input-number
              id="inputNumber"
              v-model="topupValue"
              :min="1"
              :max="10000"
            />
          </div>
          <a-divider type="vertical" />
          <a-button size="small">充值信用</a-button>
        </a-popconfirm>
      </span>
    </a-table>
    <AddManagerModal v-if="addManagerModalVisible"></AddManagerModal>
  </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import AddManagerModal from "./components/addManagerModal";
import moment from "moment";
const columns = [
  {
    title: "ID",
    dataIndex: "id",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) => record.id.toString().includes(value),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "用户邮箱",
    dataIndex: "email",
        scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) => record.email.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "用户名",
    dataIndex: "userName",
  },
  {
    title: "用户类型",
    dataIndex: "userType",
    scopedSlots: { customRender: "userType" },
    filters:[
      { text: '客户', value: 'Client' },
      { text: '酒店', value: 'HotelManager' },
      { text: '网站', value: 'Manager' }
    ],
    onFilter: (value, record) => record.userType===value,
  },
  {
    title: "用户手机号",
    dataIndex: "phoneNumber",
  },
  {
    title: "信用值",
    dataIndex: "credit",
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
      columns,
      data: [],
      form: this.$form.createForm(this, { name: "manageUser" }),
      topupValue: 100,
      // 下面三个是搜索相关
      searchText: "",
      searchInput: null,
      searchedColumn: ""
    };
  },
  components: {
    AddManagerModal,
  },
  computed: {
    ...mapGetters([
      "addManagerModalVisible",
      // 'managerList'
      "userList",
      "roles"
    ]),
  },
  mounted() {
    //   this.getManagerList()
    this.getUserList();
  },
  methods: {
    ...mapActions([
      // 'getManagerList'
      "getUserList",
      "resetPassword",
      "updateCredit",
      "deleteUser"
    ]),
    ...mapMutations(["set_addManagerModalVisible"]),
    addManager() {
      this.set_addManagerModalVisible(true);
    },
    async resetPsw(id) {
      // console.log(id);
      await this.resetPassword(id);
    },
    async topupCredit(record) {
      const creditVO = {
        userId: record.id,
        action: "TOPUP",
        sign: "ADD",
        changeNum: this.topupValue,
        result: record.credit + this.topupValue,
        time: moment().format("YYYY-MM-DD HH:mm:ss"),
      };
      // console.log(creditVO);
      await this.updateCredit(creditVO);
      await this.getUserList();
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
    async delete_User(user){
      console.log(user.id);
      await this.deleteUser(user.id)
      await this.getUserList();
    }
  },
};
</script>
