package cn.wzd.mapper;

import java.util.List;

import cn.wzd.po.Orders;
import cn.wzd.po.User;

public interface OrderUser {
	
	//查询订单关联查询订单用户信息，一对一的关系
	public List<Orders> findOrderAndUserResultMap() throws Exception;
	
	//查询用户关联查询订单信息，一对多的关系
	public List<User> findUserAndOrdersResultMap() throws Exception;
	
	//查询用户表，关联查询商items表，多对多关系
	public List<User> findUserAndItemsResultMap() throws Exception;
	
}
