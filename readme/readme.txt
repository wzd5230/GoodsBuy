

#数据库脚本
四张表，分别为user、items、orders、orderdetail

CREATE TABLE USER(
id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
username VARCHAR(32) NOT NULL COMMENT '用户名称',
birthday DATE COMMENT '生日',
sex CHAR(1)  COMMENT '性别',
address VARCHAR(256) COMMENT '地址'
);

CREATE TABLE orders(
id INT(11) NOT NULL AUTO_INCREMENT,
user_id INT(11) NOT NULL COMMENT '下单用户id',
number VARCHAR(32) NOT NULL COMMENT '订单号',
createtime DATE NOT NULL COMMENT '创建订单时间',
note VARCHAR(100) COMMENT '备注',
CONSTRAINT consistent_pk PRIMARY KEY(id)
);

CREATE TABLE orderdetail (
  id INT (11) NOT NULL AUTO_INCREMENT,
  orders_id INT (11) NOT NULL COMMENT '订单id',
  items_id INT (11) NOT NULL COMMENT '商品id',
  items_num INT (11) COMMENT '商品购买数量',
  CONSTRAINT constraint_pk PRIMARY KEY (id)
) ;

CREATE TABLE items (
  id INT (11) NOT NULL AUTO_INCREMENT,
  NAME VARCHAR (32) NOT NULL COMMENT '商品名称',
  price FLOAT (10, 1) NOT NULL COMMENT '商品定价',
  detail TEXT COMMENT '描述',
  pic VARCHAR (512) COMMENT '商品图片',
  createtime DATETIME COMMENT '生产日期',
  CONSTRAINT constraint_pk PRIMARY KEY (id)
) ;


