<template>
  <div class="hotelList">
    <div class="select">
      <a-radio-group v-model="sort" @change="changeSort">
        <a-radio-button value="default">
          综合排序
        </a-radio-button>
        <a-radio-button value="rate">
          评分优先
        </a-radio-button>
        <a-radio-button value="live">
          住过优先
        </a-radio-button>
      </a-radio-group>
      <a-select v-model="star" style="width: 120px" @change="changeStar">
        <a-select-option value="all">
          星级不限
        </a-select-option>
        <a-select-option value="Three">
          三星级
        </a-select-option>
        <a-select-option value="Four">
          四星级
        </a-select-option>        
        <a-select-option value="Five">
          五星级
        </a-select-option>
      </a-select>
      <a-cascader v-model="region" :options="options" placeholder="地区不限" @change="changeRegion" />
    </div>
    <a-spin :spinning="hotelListLoading">
      <div class="card-wrapper">
        <HotelCard
          :hotel="item"
          v-for="item in active_hotelList"
          :key="item.index"
          @click.native="jumpToDetails(item.id)"
        ></HotelCard>
        <div
          v-for="item in emptyBox"
          :key="item.name"
          class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3"
        ></div>
        <a-pagination
          showQuickJumper
          :total="hotelList.totalElements"
          :defaultCurrent="1"
          @change="pageChange"
        ></a-pagination>
      </div>
    </a-spin>
  </div>
</template>
<script>
import options from '@/utils/cities'
import HotelCard from "./components/hotelCard";
import { mapGetters, mapActions, mapMutations } from "vuex";

export default {
  name: "home",
  components: {
    HotelCard,
  },
  data() {
    return {
      emptyBox: [{ name: "box1" }, { name: "box2" }, { name: "box3" }],
      sort:'default', // 用于排序
      star:'all', // 星级筛选
      region:[],  // 地区筛选
      // 为了实现筛选功能，需要用这个，而不是写死的
      active_hotelList:[],
      // 城市接口
      options
    };
  },
  async mounted() {
    await this.getHotelList();
    await this.getUserOrders(this.userId)
    for (const i in this.hotelList) {
      for (const j in this.orderList) {
        if(this.hotelList[i].id===this.orderList[j].hotelId)
          this.hotelList[i]={...this.hotelList[i],hasOrder:true}
        else
          this.hotelList[i]={...this.hotelList[i],hasOrder:false}
        break
      }
    }
    this.active_hotelList = JSON.parse(JSON.stringify(this.hotelList))
  },
  computed: {
    ...mapGetters(["hotelList", "hotelListLoading","userId","orderList"]),
  },
  methods: {
    ...mapMutations(["set_hotelListParams", "set_hotelListLoading"]),
    ...mapActions(["getUserOrders", "getHotelList"]),

    pageChange(page, pageSize) {
      const data = {
        pageNo: page - 1,
      };
      this.set_hotelListParams(data);
      this.set_hotelListLoading(true);
      this.getHotelList();
    },
    jumpToDetails(id) {
      this.$router.push({ name: "hotelDetail", params: { hotelId: id } });
    },
    changeSort(e){
      if(e.target.value==='default'){
        // default
        var newList = []
        this.hotelList.map(hotel=>{
          this.active_hotelList.forEach(inhotel=>{
            if (hotel.id===inhotel.id) {
              newList = [...newList,hotel]
            }
          })
        })
        this.active_hotelList = newList
      }else if(e.target.value === 'rate'){
        // rate
        this.active_hotelList.sort((a,b)=>{return b.rate - a.rate})
      }else if(e.target.value === 'live'){
        this.active_hotelList.sort((a,b)=>{
          var bi =  b.hasOrder?1:0
          var ai =  a.hasOrder?1:0
          // ai bi代表a、b的次序，其实就是把boolean转为int
          return bi-ai
        })
      }
    },
    changeStar(){
      this.reSelectHotelList()
    },
    changeRegion(){
      this.reSelectHotelList()
    },
    reSelectHotelList(){
      const reg = this.region.join(' ') // 地区字符串
      var newList = []
      if(this.star==='all'){
        if(reg === ''){
          newList = this.hotelList
        }else{
          this.hotelList.map(hotel=>{
            if(hotel.bizRegion === reg){
              newList = [...newList,hotel]
            }})
        }
      }else{
        if(reg === ''){
          this.hotelList.map(hotel=>{
            if(hotel.hotelStar === this.star){
              newList = [...newList,hotel]
            }})
        }else{
          this.hotelList.map(hotel=>{
            if(hotel.hotelStar === this.star&&hotel.bizRegion === reg){
              newList = [...newList,hotel]
            }})
        }
      }
      this.active_hotelList = newList
      this.sort = "default" // 把首筛选改为综合排序，简单点。。
    }
  },
};
</script>
<style scoped lang="less">
.hotelList {
  padding: 0 0;
  .emptyBox {
    height: 0;
    margin: 10px 10px;
  }
  .card-wrapper {
    text-align: center;
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    flex-grow: 3;
    // min-height: 800px  这个不知道有什么用，先注释掉把，不然会影响header等其他的布局。。
  }
  .card-wrapper .card-item {
    margin: 30px;
    position: relative;
    height: 188px;
  }
}
.select{
  margin-left: 15px;
  margin-bottom: 30px;
}
</style>
