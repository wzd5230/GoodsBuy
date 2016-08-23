/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.7.12-log : Database - mytatis
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mytatis` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mytatis`;

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '商品名称',
  `price` float(10,1) NOT NULL COMMENT '商品定价',
  `detail` text COMMENT '描述',
  `pic` varchar(512) DEFAULT NULL COMMENT '商品图片',
  `createtime` datetime DEFAULT NULL COMMENT '生产日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `items` */

insert  into `items`(`id`,`name`,`price`,`detail`,`pic`,`createtime`) values (1,'羽毛球拍',499.0,'YONEX X-2 羽毛球拍 带包装',NULL,NULL),(2,'羽毛球',499.0,'YONEX ball-2 羽毛球 耐打',NULL,NULL),(3,'运动鞋',299.0,'安踏 运动鞋',NULL,NULL),(4,'跑鞋',999.0,'ascis 运动鞋',NULL,NULL),(5,'护膝',9.9,'李宁 护膝 加厚',NULL,NULL),(6,'袜子',19.9,'耐克 防滑 吸汗 防臭',NULL,NULL),(7,'乒乓球',5.9,'红双喜 三星 比赛用球',NULL,NULL),(8,'乒乓球拍',1200.0,'日本 butterfly 双面弧圈球 王皓 无机胶水',NULL,NULL);

/*Table structure for table `orderdetail` */

DROP TABLE IF EXISTS `orderdetail`;

CREATE TABLE `orderdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orders_id` int(11) NOT NULL COMMENT '订单id',
  `items_id` int(11) NOT NULL COMMENT '商品id',
  `items_num` int(11) DEFAULT NULL COMMENT '商品购买数量',
  PRIMARY KEY (`id`),
  KEY `constraint_fk2` (`items_id`),
  KEY `constraint_fk3` (`orders_id`),
  CONSTRAINT `constraint_fk2` FOREIGN KEY (`items_id`) REFERENCES `items` (`id`),
  CONSTRAINT `constraint_fk3` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orderdetail` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '下单用户id',
  `number` varchar(32) NOT NULL COMMENT '订单号',
  `createtime` date NOT NULL COMMENT '创建订单时间',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `constraint_fk1` (`user_id`),
  CONSTRAINT `constraint_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`id`,`user_id`,`number`,`createtime`,`note`) values (1,3,'20160001','2016-08-23','我是林丹，羽毛球拍包装好点！！'),(2,3,'20160002','2016-08-23','我是林丹，羽毛球我要质量最好的，包邮！！'),(3,4,'20160003','2016-08-23','我是王皓，我要直拍，双面弧圈球。'),(4,4,'20160003','2016-08-11','我是王皓，红双喜三星乒乓球来50个！'),(5,6,'20160004','2016-08-15','I\'m BOLT,I love ascis.');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`birthday`,`sex`,`address`) values (3,'林丹','2016-08-23','b','国家羽毛球队'),(4,'王皓','2016-08-23','b','国家乒乓球队'),(6,'BOLT','1988-07-11','b','牙买加田径队');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
