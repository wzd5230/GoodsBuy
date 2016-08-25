package cn.wzd.junitTest.cache;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.wzd.mapper.lazy.UserOrdersItems;
import cn.wzd.po.User;

/**
 * 
 * @ClassName: Cache1Test 
 * @Description: 测试mybatis的一级缓存。
 * @author A18ccms a18ccms_gmail_com 
 * @date 2016年8月25日 上午10:15:53 
 *
 */
public class Cache1Test {
	/**
	 * 所谓的一级缓存，其作用域为sqlSession内，意味着sqlSession关闭后，一级缓存将会清空，只有在
	 * 同一个sqlSession实现类的对象下，进行相同的操作，才会使用一级缓存，一级缓存是放置在内存中的，
	 * 后面说到的二级缓存，可以存放在任何介质，取决于Cache接口的实现类。
	 * 默认情况下，mybatis自动开启了一级缓存。
	 * 在sqlSession对象中，使用sql的查询语句时，会首先查看一级缓存中是否有缓存的数据，如果有，直接
	 * 从缓存中读取返回；如果一级缓存中没有请求的数据，将会执行对应的sql语句，从数据库中读取返回，并将
	 * 读取的数据保存在一级缓存中，以便相同sqlSession对象的后续查询使用；一旦使用insert、delet
	 * 、update等sql语句，并调用commit()方法，一级缓存将会清空，避免造成脏读。
	 * 
	 * 测试方法：
	 * 1.正常检测
	 *   1.1同一个sqlSession对象，执行两次相同的查询，观察两次是否都执行了sql语句。（log4j的debug输出）
	 *   
	 * 2.非正常检测
	 * 	 2.1同一个sqlSession对象，执行两次相同的查询，并在两次查询中间加入一次update操作。
	 *      观察两次是否都执行了sql语句。
	 */
	
	private SqlSessionFactory sqlSessionFactory; 

	@Before
	public void setUp() throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 
	 * @Title: testCache1_1 
	 * @Description: 正常测试，两次连续的查询，第二次查询直接从一级缓存读取。 
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Test
	public void testCache1_1() throws Exception{
		
		SqlSession session = this.sqlSessionFactory.openSession();
		
		UserOrdersItems userOrdersItems = session.getMapper(UserOrdersItems.class);
		
		/*
		 * 执行该语句的时候，mybatis会在一级缓存中（hashMap）查找该key是否有值。如果没有则会执行相应的
		 * sql语句，从数据库读取该用户条目。
		 */
		User user = userOrdersItems.findUserById(3);
		
		System.out.println(user);
		
		/*
		 * 执行该语句的时候，mybatis会在一级缓存中（hashMap）查找该key是否有值。并且可以找到，直接从
		 * 一级缓存中返回。
		 */
		user = userOrdersItems.findUserById(3);
		
		System.out.println(user);
		
		//关闭session
		session.close();
	}
	
	/**
	 * 
	 * @Title: testCache1_2 
	 * @Description: 非正常测试，2次查询，使用不同的SqlSession实例。
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Test
	public void testCache1_2() throws Exception{
		
		SqlSession session1 = this.sqlSessionFactory.openSession();
		SqlSession session2 = this.sqlSessionFactory.openSession();
		
		UserOrdersItems userOrdersItems1 = session1.getMapper(UserOrdersItems.class);
		UserOrdersItems userOrdersItems2 = session2.getMapper(UserOrdersItems.class);
		
		/*
		 * 执行该语句的时候，mybatis会在一级缓存中（hashMap）查找该key是否有值。如果没有则会执行相应的
		 * sql语句，从数据库读取该用户条目。
		 */
		User user = userOrdersItems1.findUserById(3);
		
		System.out.println(user);
		
		/*
		 * 执行该语句的时候，mybatis会在一级缓存中（hashMap）查找该key是否有值。找不到，将会执行sql语句从数据库查询。
		 */
		user = userOrdersItems2.findUserById(3);
		
		System.out.println(user);
		
		//关闭session
		session1.close();
		session2.close();
	}
	
	/**
	 * 
	 * @Title: testCache1_3 
	 * @Description: 非正常测试，2次查询，2次查询中间使用update用户user表。
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Test
	public void testCache1_3() throws Exception{
		
		SqlSession session = this.sqlSessionFactory.openSession();
		
		UserOrdersItems userOrdersItems = session.getMapper(UserOrdersItems.class);
		
		/*
		 * 执行该语句的时候，mybatis会在一级缓存中（hashMap）查找该key是否有值。如果没有则会执行相应的
		 * sql语句，从数据库读取该用户条目。
		 */
		User user = userOrdersItems.findUserById(3);
		
		System.out.println(user);
		
		/*
		 * 更新数据库操作
		 */
		user.setAddress("国家羽毛球队");
		userOrdersItems.updateUser(user);
		//进行提交才会真的实现一级缓存的清空
		session.commit();
		
		//先从一级缓存查找，找不到，执行相应的sql语句，从数据库中读取数据
		user = userOrdersItems.findUserById(3);
		
		System.out.println(user);
		
		//关闭session
		session.close();
	}

}
