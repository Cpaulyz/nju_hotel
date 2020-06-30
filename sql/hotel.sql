
-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: Hotel
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Coupon`
--

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `hotelId` int(11) DEFAULT '-1',
  `couponType` int(11) NOT NULL,
  `couponName` varchar(255) NOT NULL,
  `target_money` double DEFAULT NULL,
  `multi_threshold` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `discount_money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--
BEGIN;
/*!40000 ALTER TABLE `Coupon` DISABLE KEYS */;
INSERT INTO `Coupon` VALUES (1,'满500-100优惠',2,2,'满减优惠券',500,NULL,0,1,NULL,NULL,100);
INSERT INTO `Coupon` VALUES (2,'国庆优惠',1,1,'节日优惠券',NULL,NULL,0.8,1,'2020-10-01','2020-10-07',NULL);
INSERT INTO `Coupon` VALUES (3,'3间优惠',1,0,'多间优惠券',NULL,3,0.9,1,NULL,NULL,NULL);
INSERT INTO `Coupon` VALUES (4,'网站优惠',0,2,'网站满减优惠券200-5',200,NULL,0,1,NULL,NULL,5);
/*!40000 ALTER TABLE `Coupon` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotelName` varchar(255) NOT NULL,
  `hotelDescription` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `bizRegion` varchar(255) DEFAULT NULL,
  `hotelStar` varchar(255) DEFAULT NULL,
  `phoneNum` varchar(255) DEFAULT NULL,
  `rate` double(255,1) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `picture` varchar(255) DEFAULT 'https://software-engineering.oss-cn-beijing.aliyuncs.com/hotel_default.jpeg',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

BEGIN;
/*!40000 ALTER TABLE `Hotel` DISABLE KEYS */;
INSERT INTO `Hotel` VALUES (1,'汉庭酒店','欢迎您入住','南京市栖霞区南京大学仙林校区篮球场','江苏省 南京市 栖霞区','Four','1829373819',4.5,6,'http://nju-hotel.oss-cn-beijing.aliyuncs.com/20200627/timg-vc-upload-1593240291071-5.jpeg');
INSERT INTO `Hotel` VALUES (2,'儒家酒店','欢迎您入住','南京市鼓楼区珠江路268号','江苏省 南京市 鼓楼区','Four','1829373819',5.0,6,'http://nju-hotel.oss-cn-beijing.aliyuncs.com/20200627/rujia-vc-upload-1593240347969-2.jpeg'),(3,'桂圆酒店','欢迎您入住','南京市栖霞区珠江路268号','江苏省 南京市 栖霞区','Four','1829553719',5.0,6,default);
INSERT INTO `Hotel` VALUES (4,'川骚酒店','那真的牛批','南京市栖霞区南京大学仙林校区下水道','江苏省 南京市 栖霞区','Three','1829373819',4.5,9,default);
COMMIT;

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `hotelId` int(11) DEFAULT NULL,
  `hotelName` varchar(255) DEFAULT NULL,
  `checkInDate` varchar(255) DEFAULT NULL,
  `checkOutDate` varchar(255) DEFAULT NULL,
  `roomType` varchar(255) DEFAULT NULL,
  `roomNum` int(255) DEFAULT NULL,
  `peopleNum` int(255) DEFAULT NULL,
  `haveChild` tinytext,
  `createDate` varchar(255) DEFAULT NULL,
  `price` decimal(65,0) DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `orderState` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

/*!40000 ALTER TABLE `OrderList` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrderList` ENABLE KEYS */;

--
-- Table structure for table `Comment`
--

DROP TABLE IF EXISTS `Comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comment`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comment`
--

BEGIN;
/*!40000 ALTER TABLE `Comment` DISABLE KEYS */;
INSERT INTO `Comment` VALUES(1,'2020-05-17 16:01:45',1,2,'刘钦',4,'就这？');
/*!40000 ALTER TABLE `Comment` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `roomType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

BEGIN;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES (2,199,20,1,'BigBed'),(3,299,30,1,'DoubleBed'),(4,399,10,1,'Family'),(6,399,10,2,'Family');
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL unique ,
  `password` varchar(32) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `credit` double(255,0) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

BEGIN;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (4,'111@qq.com','e10adc3949ba59abbe56e057f20f883e','测试一号','12345678919',100,'Client','https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png');
INSERT INTO `User` VALUES (5,'123@qq.com','e10adc3949ba59abbe56e057f20f883e','测试二号','12345678911',100,'Client','https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png');
INSERT INTO `User` VALUES (6,'333@qq.com','e10adc3949ba59abbe56e057f20f883e','酒店人员1','13159175930',100,'HotelManager','https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png');
INSERT INTO `User` VALUES (7,'222@qq.com','e10adc3949ba59abbe56e057f20f883e','管理员','12345678919',100,'Admin','https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png');
INSERT INTO `User` VALUES (8,'444@qq.com','e10adc3949ba59abbe56e057f20f883e','网站工作人员1号','12345678911',100,'Manager','https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png');
INSERT INTO `User` VALUES (9,'chuan@qq.com','e10adc3949ba59abbe56e057f20f883e','川骚酒店工作人员','12345678919',100,'HotelManager','https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Credit`
--

DROP TABLE IF EXISTS `Credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Credit`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `sign` varchar(255) DEFAULT NULL,
  `change_num` double(255,0) DEFAULT NULL,
  `result` double(255,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Content`
--

BEGIN;
/*!40000 ALTER TABLE `Credit` DISABLE KEYS */;
INSERT INTO `Credit` VALUES(1,4,'INIT','2020-04-15',NULL,NULL,NULL,100);
INSERT INTO `Credit` VALUES(2,4,'EXECUTE','2020-05-15',1,'ADD',20,120);
INSERT INTO `Credit` VALUES(3,5,'INIT','2020-06-04',NULL,NULL,NULL,100);
/*!40000 ALTER TABLE `Credit` ENABLE KEYS */;
COMMIT;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-12 10:08:42
SET FOREIGN_KEY_CHECKS = 1;
