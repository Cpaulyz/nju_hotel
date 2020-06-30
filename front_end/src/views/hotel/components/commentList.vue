<template>
    <a-list
        item-layout="horizontal"
        :data-source="hotelComment"
    >
        <a-list-item slot="renderItem" slot-scope="item">
        <a-comment :author="item.userName" :avatar="avatar">
            <div slot="content">
                <a-rate class="grade" :default-value="item.grade" disabled >
                    <a-icon  slot="character" type="star" theme="filled" :style="{ fontSize: '14px' }" />
                </a-rate>
                <div>{{ item.content }}</div>
            </div>
            <a-tooltip slot="datetime" :title="moment(item.createTime).format('YYYY-MM-DD HH:mm:ss')">
              <span>{{ moment(item.createTime).fromNow()}}</span>
            </a-tooltip>
        </a-comment>
        </a-list-item>
    </a-list>
</template>
<script>
import moment from 'moment'
import { mapGetters, mapActions, mapMutations } from 'vuex'
export default {
  data() {
    return {
        avatar:'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        moment
    };
  },
  mounted(){
      console.log(this.currentHotelId);
      
      this.getHotelComment()
  },
  computed:{
      ...mapGetters([
          'currentHotelId',
          'hotelComment'
      ])
  },
  methods: {
    ...mapActions([
        'getHotelComment'
    ]),
  },
};
</script>

<style lang="css">
    .grade{
        margin-bottom: 10px;
    }
</style>