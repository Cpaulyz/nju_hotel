<template>
    <div class="header">
        <div class="label">
            <img src="@/assets/logo.svg" class="logo" alt="logo" @click="jumpToHome">
            <span class="title">NJUSE 酒店管理系统</span>
            
        </div>
        <a-menu v-model="current" mode="horizontal" theme="dark" :style="{ lineHeight: '64px'}">
            <a-menu-item key="home" @click="selectMenu">
                <router-link to="/hotel/hotelList">
                    <a-icon type="home" />首页
                </router-link>
            </a-menu-item>
            <a-menu-item key="order" @click="jumpToOrder" v-if="userInfo.userType=='Client'||userInfo.userType=='HotelManager'">
                <router-link :to="{ name: 'orderList',params: { userId: userId}}">
                     <a-icon type="carry-out" />订单管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="user" @click="jumpToUserInfo" v-if="userInfo.userType=='Client'">
                <a-icon type="user" />个人中心
            </a-menu-item>
            <a-menu-item key="manageHotel" @click="selectMenu" v-if="userInfo.userType=='HotelManager'">
                <router-link :to="{ name: 'manageHotel'}">
                     <a-icon type="switcher" />酒店管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="adminManage" @click="selectMenu" v-if="userInfo.userType=='Admin'||userInfo.userType=='Manager'">
                <router-link :to="{ name: 'adminManage'}">
                     <a-icon type="control" />管理
                </router-link>
            </a-menu-item>
        </a-menu>
        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user">
                    <a-avatar :src="avatar"></a-avatar>
                    <span style="font-size: 14px" id='username'>{{ userInfo.userName }}</span>
                    <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                </div>
                <a-menu slot="overlay">
                <a-menu-item @click="jumpToHome()">
                    <a-icon type="home"></a-icon>
                    首页
                </a-menu-item>
                <a-menu-item @click="jumpToUserInfo()">
                    <a-icon type="profile"></a-icon>
                    我的信息
                </a-menu-item>
                <a-menu-item @click="jumpToPassword()">
                    <a-icon type="lock"></a-icon>
                    修改密码
                </a-menu-item>
                <a-menu-item @click="quit()">
                    <a-icon type="poweroff"></a-icon>
                    退出登录
                </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
export default {
    name: '',
    data() {
        return {
        }
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'avatar'
        ]),
        current:{
            get(){
                if (this.$route.name == 'hotelList' || this.$route.name == 'hotelDetail') {
                    return ['home']
                }else if(this.$route.name == 'userInfo'||this.$route.name == 'userCredit'||this.$route.name=='password') {
                    return ['user']
                }else if(this.$route.name == 'manageHotel') {
                    return ['manageHotel']
                }else if(this.$route.name == 'orderList' || this.$route.name == 'orderDetail') {
                    return ['order']
                }else {
                    return ['adminManage']
                }
            },
            set(){
                
            }
        }
    },
    async mounted(){
    },
    methods: {
        ...mapMutations([

        ]),
        ...mapActions([
            'logout',
        ]),
        selectMenu(v){
        },
        async quit() {
            await this.$store.dispatch('logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        },
        jumpToUserInfo() {
            this.$router.push({ name: 'userInfo', params: { userId: this.userId } })
        },
        jumpToHome() {
            this.$router.push('/hotel/hotelList')
        },
        jumpToOrder(){
            // this.$router.push({ name: 'orderList', params: { userId: this.userId } })
        },
        jumpToPassword(){
            this.$router.push({ name: 'password'})
        }
    }
}
</script>
<style scoped lang="less">
    .header {
        display: flex;
        line-height: 64px;
        height: 64px;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;
        .label{
            height: 64px;
            line-height: 64px;
            vertical-align: middle;
            min-width: 400px;

            .logo {
            height: 64px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
            cursor: pointer;
          }

          .title {
            font-size: 27px;
            color: #f0f2f5;
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
          }

          .search {
              width: 300px; 
              margin-left: 30px
          }
        }
        .logout {
            margin-right: 40px;
            .user {
                cursor: pointer;
                display:flex;
                align-items: center;
                span {
                    margin-left: 5px
                }
            }
            #username{
                color: #f0f2f5;
            }
        }

    }
</style>
<style lang="less">
    // .header {
    //     .ant-menu {
    //         background: none
    //     }
    // }
</style>