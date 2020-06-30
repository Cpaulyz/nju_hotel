<template>
    <div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="roomList.filter(item => item.curNum>0)"
                rowKey="id"
            >
                <span slot="roomType" slot-scope="roomType">
                    <span>{{ type_map[roomType] }}</span>
                </span>

                <template
                v-for="col in ['price', 'total']"
                :slot="col"
                slot-scope="text, record"
                >
                <div :key="col">
                    <a-input-number size="small" :min="0" :max="10000" :precision="0"
                    v-if="record.editable"
                    @change="e => handleChange(e, col, record)"
                    :default-value="text"
                    />
                    <template v-else>
                    {{ text }}
                    </template>
                </div>
                </template>
                <template slot="action" slot-scope="text, record">
                <div class="editable-row-operations">
                    <span v-if="record.editable">
                        <a @click="() => save(record.id)">保存</a>
                        <a-divider type="vertical" />
                        <a @click="() => cancel(record.id)">取消</a>
                    </span>
                    <span v-else>
                        <a :disabled="editingKey !== ''" @click="() => edit(record.id)">修改</a>
                        <a-divider type="vertical" />
                        <a-popconfirm title="您确定要删除该房间吗？操作将不可撤回。" 
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="() => deleteConfirm(record.id)">
                            <a :disabled="editingKey !== ''">删除</a>
                        </a-popconfirm>
                    </span>
                </div>
                </template>
                
            </a-table>
        </div>
    </div>
</template>

<script>
import {mapActions,mapGetters,mapMutations} from 'vuex'
import moment from 'moment'
const columns = [
    {  
      title: '房型',
      dataIndex: 'roomType',
      key: 'roomType',
      scopedSlots: { customRender: 'roomType'}
    },
    {
      title: '房价',
      key: 'price',
      dataIndex: 'price',
      scopedSlots: { customRender: 'price'}
    },    
    {
      title: '总房间数',
      key: 'total',
      dataIndex: 'total',
      scopedSlots: { customRender: 'total'}
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name:'roomList',
    data() {
        return {
            columns,
            formItemLayout: {

            },
            type_map:{
                'BigBed':"大床房",
                'DoubleBed':"双床房",
                'Family':"家庭房"
            },
            editingKey: '', // 编辑用的
            current_room:{
                
            }
        }
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'userInfo',
            'roomList'
        ])
    },
    mounted(){
        var defaultDate = [moment(),moment().add(1,'days')]
        this.set_currentHotelRoomDate(defaultDate)
        this.getRoomList()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelRoomDate',
            'set_roomList'
        ]),
        ...mapActions([
            'getRoomList',
            'updateRoom',
            'deleteRoom'
        ]),
        handleChange(value, column,record) {
            this.current_room[column] = value
            this.current_room['id'] = record.id
            console.log(this.current_room);
            
        },
        edit(key) { // 启动编辑
            const newData = [...this.roomList];
            const target = newData.filter(item => key === item.id)[0];
            // 选择目标
            this.editingKey = key;
            if (target) {
                target.editable = true;
                this.current_room['total'] = target.total
                this.current_room['price'] = target.price 
                this.set_roomList(newData)
            }
        },
        async save(key) {
            await this.updateRoom(this.current_room)
            this.editingKey = '';
            await this.getRoomList()
            // 更新信息，然后重新获取roomList
        },
        cancel(key) {
            const newData = [...this.roomList];
            const target = newData.filter(item => key === item.id)[0];
            this.editingKey = '';
            if (target) {
                // Object.assign(target, this.cacheData.filter(item => key === item.key)[0]);
                delete target.editable;
                console.log(newData);
                
                this.set_roomList(newData)
            }
        },
        async deleteConfirm(roomId){
           await this.deleteRoom(roomId)
           await this.getRoomList()
        }
    }
}
</script>