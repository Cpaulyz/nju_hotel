<template>
  <a-modal
    :visible="visible"
    title="修改酒店信息"
    cancelText="取消"
    okText="确定"
    @cancel="cancel"
    @ok="handleSubmit"
    style="height=1000px"
  >
    <a-form-model
      :model="hotelVO"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
    >
      <a-form-model-item label="酒店图片">
        <UploadImage :picture="hotelVO.picture" @picture="setPicture"></UploadImage>
      </a-form-model-item>
      <a-form-model-item label="酒店地址">
        <a-input v-model="hotelVO.address" />
      </a-form-model-item>      
      <a-form-model-item label="酒店地区">
        <a-cascader v-model="hotelVO.bizRegion" :options="options" placeholder="地区不限" :allowClear="false"/>
      </a-form-model-item>
      <a-form-model-item label="酒店简介">
        <a-input type="textarea" v-model="hotelVO.description" />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>
<script>
import UploadImage from './components/uploadImage'
import options from '@/utils/cities'
import { mapGetters, mapActions } from "vuex";
export default {
  name: "addRoomModal",
  data() {
    return {
      labelCol: {
        xs: { span: 12 },
        sm: { span: 6 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      // 预填信息都是父组件传来的，这里没什么逻辑也不用验证，所以这样简单处理。
      hotelVO: {
        id: "",
        address: "",
        description: "",
        bizRegion:[],
        picture:''
      },
      visible:false,
      options
    };
  },
  computed:{
      ...mapGetters(['userId','roles'])
  },
  components:{
    UploadImage
  },
  methods: {
    ...mapActions(["updateHotel","getManagerHotelList","getHotelList"]),
    cancel() {
      this.visible = false
    },
    show(){
        this.visible = true
    },
    async handleSubmit(e) {
        const hotel = {
          id:this.hotelVO.id,
          address:this.hotelVO.address,
          description:this.hotelVO.description,
          bizRegion:this.hotelVO.bizRegion.join(' '),
          picture:this.hotelVO.picture,
        }
        console.log(hotel);
        
        await this.updateHotel(hotel)
        if(this.roles=='HotelManager')
          await this.getManagerHotelList(this.userId);
        else{
          await this.getHotelList()
        }
        this.cancel()
    },
    setPicture(url){
      console.log(url);
      
      this.hotelVO.picture = url
    }
  },
};
</script>
