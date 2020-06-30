import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login.vue'
import store from '../store'

Vue.use(VueRouter)
const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    redirect: '/NJUSE'
  },
  {
    path: '/NJUSE',
    name: 'layout',
    redirect: '/hotel/hotelList',
    component: ()=> import('@/views/layout'),
    children: [
      {
        path: '/hotel/hotelList',
        name: 'hotelList',
        component: () => import('@/views/hotel/hotelList')
      },
      {
        path: '/hotel/hotelDetail/:hotelId',
        name: 'hotelDetail',
        component: () => import('@/views/hotel/hotelDetail')
      },
      {
        path: '/user/info/:userId',
        name: 'userInfo',
        component: () => import('@/views/user/info')
      },        
      {
        path: '/user/password',
        name: 'password',
        component: () => import('@/views/user/modifyPassword')
      },      
      {
        path: '/user/credit/:userId',
        name: 'userCredit',
        component: () => import('@/views/user/creditRecord')
      },
      {
        path: '/order/orderList/:userId',
        name: 'orderList',
        component: () => import('@/views/order/orderList')
      },
      {
        path: '/order/orderDetail/:orderId',
        name: 'orderDetail',
        component: () => import('@/views/order/orderDetail')
      },
      {
        path: '/hotelManager/manageHotel',
        name: 'manageHotel',
        component: () => import('@/views/hotelManager/manageHotel')
      },
      {
        path: '/admin/manage',
        name: 'adminManage',
        component: () => import('@/views/admin/adminPanel'),
        beforeEnter: (to, from, next) => {
          if(store.getters.roles=='Admin'||store.getters.roles=='Manager'){
            next()
            console.log("管理员身份");
          }else{
            next('/')
          }
        }
      }
    ]
  },
  // {
  //   path: '/admin',
  //   name: 'admin',
  //   children:[
  //     {
  //       path:'/manage',
  //       name:'adminManage',    
  //       component: () => import('@/views/admin/adminPanel'),
  //     }
  //   ],
  //   beforeEnter: (to, from, next) => {
  //     console.log("管理员身份");
  //     next()
  //   }
  // },
]
const createRouter = () => new VueRouter({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes
})
const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router