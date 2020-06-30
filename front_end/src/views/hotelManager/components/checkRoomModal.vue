<template>
  <a-modal
    :visible="visible"
    title="查看房间信息"
    @cancel="cancel"
    width="800px"
    :footer="null"
  >
    <RoomList v-if="visible"></RoomList>
  </a-modal>
</template>
<script>
import RoomList from './roomList'
import { mapGetters, mapActions } from "vuex";
export default {
  name: "checkRoomModal",
  data() {
    return {
      visible:false,
    };
  },
  components:{
      RoomList
  },
  methods: {
    cancel() {
      this.visible = false
    },
    show(){
        this.visible = true
    },
  },
};
</script>