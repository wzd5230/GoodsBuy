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
 * @ClassName: Cache2Test 
 * @Description: 测试mybatis的二级缓存。 
 * @author A18ccms a18ccms_gmail_com 
 * @date 2016年8月25日 上午9:58:36 
 *
 */
public class Cache2Test {
	/**
	 * 所谓的二级缓存，指的是相同的namespace的mapper中，所有的statement公用的缓存；相同的namespace使用同一块
	 * 二级缓存，不同的namespace使用的是不同的二级缓存；一般情况下二级缓存使用的不是内存；我们可以自己实现二级缓存或者是
	 * 使用其他第三方优秀的二级缓存开发包，只需要实现org.apache.ibatis.cache.Cache接口即可。
	 * 二级缓存的工作原理和一级缓存类似，当某namespace下执行了sql查询语句时，会现在二级缓存中进行查找；如果在二级缓存中
	 * 找到该key，则直接从二级缓存中返回该条目，如果二级缓存中没有找到，则执行相应的sql语句，执行完毕后会将该条目放到二级
	 * 缓存中；当相同namespace下执行了相同的sql查询语句时，会从二级缓存中查找，此时就可以从二级缓存中查找到该key对应的
	 * 条目，直接从二级缓存中返回，而不用执行sql语句。同一级缓存一样，一旦执行了改namespace下的insert、update、delete
	 * 等sql语句，并调用了commit()方法，则二级缓存将会被清空。
	 * 使用二级缓存要求：
	 * 1.某个表需要使用二级缓存，则该表对应的pojo类应该实现Serializable接口，因为二级缓存的截止多种多样，可能是硬盘、网络
	 * 等，因此缓存的对象需要序列化和反序列化功能，因此需要实现Serializable接口。
	 * 2.需要在mybatis的配置文件中开启二级缓存的功能。
	 * 		<setting name="cacheEnabled" value="true"/>
	 * 3.在需要使用二级缓存的mapper.xml中开启二级缓存功能。
	 * 		<cache/> <!-- 该标签下有许多配置属性，可以根据需求进行设置-->
	 * 4.如果需要对某个statement关闭二级缓存功能，可以在其statement中设置如下属性
	 * 		useCache="false"
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
	 * @Description: 相同namespace下的sql语句，使用的是2个SqlSession实例对象。测试二级缓存。 
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Test
	public void testCache1_1() throws Exception{
		
		SqlSession session1 = this.sqlSessionFactory.openSession();
		SqlSession session2 = this.sqlSessionFactory.openSession();
		
		UserOrdersItems userOrdersItems1 = session1.getMapper(UserOrdersItems.class);
		UserOrdersItems userOrdersItems2 = session2.getMapper(UserOrdersItems.class);
		
		/*
		 * 执行该语句的时候，mybatis会在二级缓存中（hashMap）查找该key是否有值。因为没有缓存过，所以命中率为0.0
		 * Cache Hit Ratio [cn.wzd.mapper.lazy.UserOrdersItems]: 0.0
		 * 并且会执行相应的sql语句，从数据库中进行查询。
		 */
		User user = userOrdersItems1.findUserById(3);
		
		System.out.println(user);
		
		//这里要先关闭，只有关闭SqlSession的时候，才会将该SqlSession查询的条目存到二级缓存。
		session1.close();
		
		
		/*
		 * 执行该语句的时候，mybatis会在二级缓存中（hashMap）查找该key是否有值。可以找到，命中率非0.0
		 * Cache Hit Ratio [cn.wzd.mapper.lazy.UserOrdersItems]: 0.5
		 * 直接从二级缓存中读取并返回对应的条目，不再执行sql语句。
		 */
		user = userOrdersItems2.findUserById(3);
		
		System.out.println(user);
		
		//关闭session
		
		session2.close();
	}
	
	/**
	 * 
	 * @Title: testCache1_2 
	 * @Description: 非正常测试，两次查询之间使用了update更新表，二级缓存清空，两次查询都要执行sql语句。
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Test
	public void testCache1_2() throws Exception{
		
		
		
		SqlSession session1 = this.sqlSessionFactory.openSession();
		SqlSession session2 = this.sqlSessionFactory.openSession();
		SqlSession session3 = this.sqlSessionFactory.openSession();
		
		UserOrdersItems userOrdersItems1 = session1.getMapper(UserOrdersItems.class);
		UserOrdersItems userOrdersItems2 = session2.getMapper(UserOrdersItems.class);
		UserOrdersItems userOrdersItems3 = session3.getMapper(UserOrdersItems.class);
		
		/*
		 * 执行该语句的时候，mybatis会在二级缓存中（hashMap）查找该key是否有值。因为没有缓存过，所以命中率为0.0
		 * Cache Hit Ratio [cn.wzd.mapper.lazy.UserOrdersItems]: 0.0
		 * 并且会执行相应的sql语句，从数据库中进行查询。
		 */
		User user = userOrdersItems1.findUserById(3);
		
		System.out.println(user);
		
		//关闭session
		session1.close();
		
		/*
		 * 更新数据库中该表的内容，调用commit()方法后将会清空二级缓存。
		 */
		user.setAddress(user.getAddress()+"_new");
		userOrdersItems3.updateUser(user);
		session3.commit();
		session3.close();
		
		
		/*
		 * 执行该语句的时候，mybatis会在二级缓存中（hashMap）查找该key是否有值。因为没有缓存过，所以命中率为0.0
		 * Cache Hit Ratio [cn.wzd.mapper.lazy.UserOrdersItems]: 0.0
		 * 并且会执行相应的sql语句，从数据库中进行查询。
		 */
		user = userOrdersItems2.findUserById(3);
		
		System.out.println(user);
		
		//关闭session
		session2.close();
	}
}
