<template>
  <div>
    <div class="hotelDetailCard">
      <h1>
        {{ currentHotelInfo.title }}
      </h1>
      <div class="hotel-info">
        <a-card style="width: 250px;height:300px" :bordered="false">
          <img
            alt="example"
            :src="currentHotelInfo.picture"
            slot="cover"
            referrerPolicy="no-referrer"
            class="hotelCard"
          />
        </a-card>
        <div class="info">
          <div class="items" v-if="currentHotelInfo.name">
            <span class="label">酒店名称：</span>
            <span class="value">{{ currentHotelInfo.name }}</span>
          </div>
          <div class="items" v-if="currentHotelInfo.address">
            <span class="label">地址</span>
            <span class="value">{{ currentHotelInfo.address }}</span>
          </div>
          <div class="items" v-if="currentHotelInfo.bizRegion">
            <span class="label">地区</span>
            <span class="value">{{ currentHotelInfo.bizRegion }}</span>
          </div>
          <div class="items" v-if="currentHotelInfo.rate">
            <span class="label">评分:</span>
            <span class="value">{{ currentHotelInfo.rate.toFixed(1) }}</span>
          </div>
          <div class="items" v-if="currentHotelInfo.hotelStar">
            <span class="label">星级:</span>
            {{ star_string[currentHotelInfo.hotelStar] }}
            <a-rate
              class="rate"
              style="font-size: 15px"
              :value="star_map[currentHotelInfo.hotelStar]"
              :count="star_map[currentHotelInfo.hotelStar]"
              disabled
              allowHalf
            />
          </div>
          <div class="items" v-if="currentHotelInfo.description">
            <span class="label">酒店简介:</span>
            <span class="value">{{ currentHotelInfo.description }}</span>
          </div>
        </div>
      </div>
      <a-divider></a-divider>
      <a-tabs>
        <a-tab-pane tab="房间信息" key="1">
          <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
        </a-tab-pane>
        <!-- <a-tab-pane tab="酒店详情" key="2"> </a-tab-pane> -->
        <a-tab-pane tab="酒店评价" key="3">
          <CommentList></CommentList>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import RoomList from "./components/roomList";
import CommentList from "./components/commentList";
export default {
  data() {
    return {
      star_map: {
        Five: 5,
        Four: 4,
        Three: 3,
      },
      star_string: {
        Five: "五星级",
        Four: "四星级",
        Three: "三星级",
      },
    };
  },
  name: "hotelDetail",
  components: {
    RoomList,
    CommentList,
  },
  computed: {
    ...mapGetters(["currentHotelInfo"]),
  },
  beforeMount() {
    this.set_currentHotelId(Number(this.$route.params.hotelId));
    this.getHotelById();
    
    console.log(this.currentHotelInfo);

  },
  beforeRouteUpdate(to, from, next) {
    this.set_currentHotelId(Number(to.params.hotelId));
    this.getHotelById();
    next();
  },
  methods: {
    ...mapMutations(["set_currentHotelId"]),
    ...mapActions(["getHotelById"]),
  },
};
</script>
<style scoped lang="less">
.hotelDetailCard {
  padding: 0 50px;
}
.hotelCard{
  width: 240px;
  height: 300px
}
.hotel-info {
  display: flex;
  align-items: stretch;
  justify-content: flex-start;
  .info {
    padding: 10px 0;
    display: flex;
    flex-direction: column;
    margin-left: 20px;
    .items {
      display: flex;
      align-items: center;
      margin-bottom: 10px;
      .label {
        margin-right: 10px;
        font-size: 18px;
      }
      .value {
        margin-right: 15px;
      }
    }
  }
}
.rate {
  margin-left: 10px;
  padding-bottom: 3px;
}
</style>
