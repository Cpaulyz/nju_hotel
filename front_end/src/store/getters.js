const getters = {
  // permission
  roles: state => state.user.roles,
  //user
  avatar:state => state.user.avatar,
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  // hotel
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  roomList:state => state.hotel.roomList,
  currentHotelRoomDate:state => state.hotel.currentHotelRoomDate,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  orderSuccessModalVisible : state => state.hotel.orderSuccessModalVisible,
  hotelComment:state => state.hotel.hotelComment,
  creditChangeList:state => state.user.creditChangeList,
  //admin
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addHotelModalVisible:state => state.admin.addHotelModalVisible,
  userList:state=>state.admin.userList,
  //hotelManager
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  activeHotelName: state => state.hotelManager.activeHotelName,
  //order
  currentOrderInfo:state => state.order.currentOrderInfo,
  orderList: state => state.order.orderList,
  //coupon
  couponList: state => state.coupon.couponList,
  couponVisible: state => state.coupon.couponVisible,
  addCouponVisible: state => state.coupon.addCouponVisible,
  }
  
  export default getters