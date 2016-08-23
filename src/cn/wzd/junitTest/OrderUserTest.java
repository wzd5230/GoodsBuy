package cn.wzd.junitTest;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.wzd.mapper.OrderUser;
import cn.wzd.po.Orders;
import cn.wzd.po.User;

public class OrderUserTest {
	
	private SqlSessionFactory sqlSessionFactory; 

	@Before
	public void setUp() throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testFindOrderAndUserResultMap() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		OrderUser orderUser = session.getMapper(OrderUser.class);
		
		List<Orders> orders = orderUser.findOrderAndUserResultMap();
		
		System.out.println("find list size====>"+orders.size());
		
		session.close();
	}
	
	@Test
	public void testFindUserAndOrdersResultMap() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		OrderUser orderUser = session.getMapper(OrderUser.class);
		
		List<User> users = orderUser.findUserAndOrdersResultMap();
		
		System.out.println("find list size====>"+users.size());
		
		session.close();
	}

}
