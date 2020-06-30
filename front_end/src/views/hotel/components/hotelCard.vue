<template>
  <a-card hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
    <img
      alt="example"
      :src="hotel.picture"
      slot="cover"
      referrerPolicy="no-referrer"
    />
    <a-tooltip :title="hotel.title" placement="top">
      <a-card-meta>
          <div slot="title">{{hotel.name}}</div>
          <template slot="description">
            <a-tag color="red" v-if="hotel.hasOrder">
              住过
            </a-tag>
            <a-rate style="font-size: 15px" :value="star_map[hotel.hotelStar]" :count="star_map[hotel.hotelStar]" disabled allowHalf/>
            <span>{{hotel.rate.toFixed(1)}}分</span>
          </template>
      </a-card-meta>
    </a-tooltip>
  </a-card>
</template>
<script>
export default {
  name:'',
  props: {
    hotel: {}
  },
  data() {
    return{
      star_map:{
        'Five':5,
        'Four':4,
        'Three':3
      }
    }
  },
}
</script>
<style scoped lang="less">
    .hotelCard {
        margin: 10px 10px;
        min-width: 180px;
        max-height: 350px;
        img {
          height: 250px;
        }
    }
</style>

<style lang="less">
.hotelCard{
  .ant-card-body{
    padding: 12px
  }
}

.ant-card-hoverable:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.4)
}

</style>