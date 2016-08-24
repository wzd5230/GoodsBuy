package cn.wzd.junitTest.lazy;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.wzd.mapper.lazy.UserOrdersItems;
import cn.wzd.po.Orders;

/**
 * 
 * @ClassName: LazyLoadingTest 
 * @Description: 对UserOrdersItems的mapper接口进行测试
 * @author wzd 
 * @date 2016年8月24日 下午8:03:19 
 *
 */
public class LazyLoadingTest {
	
	private SqlSessionFactory sqlSessionFactory; 

	@Before
	public void setUp() throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 
	 * @Title: testFindOrderUserResultMapLazy 
	 * @Description: 该测试方法用来测试UserOrdersItems这个映射文件中的id为findOrderUserResultMapLazy的
	 * statement，该statement采用了延迟加载的方式。
	 * 首先查询的是Orders表中的数据，并将这些数据映射到Orders类中的属性中，Orders类中有一个User类型的对象，在调用getUser
	 * 方法之前，并没有查询id为findUserById的statement，一旦调用getUser方法，则会执行该sql语句。
	 * 通过此方法将一个比较大的关联查询分开成多次比较小的关联查询。
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Test
	public void testFindOrderUserResultMapLazy() throws Exception{
		SqlSession session = this.sqlSessionFactory.openSession();
		
		UserOrdersItems userOrdersItems = session.getMapper(UserOrdersItems.class);

		//执行该语句的时候，可以通过console窗口看到执行了sql语句（SELECT * FROM orders;）
		List<Orders> list = userOrdersItems.findOrderUserResultMapLazy();
		
		System.out.println("size:----->"+list.size());
		
		for (Orders orders : list) {
			System.out.println(orders.getId());
			System.out.println(orders.getNumber());
			
			/*
			 * 执行该语句的时候，可以通过console窗口看到执行了sql
			 * 语句（SELECT id As id, username AS username, birthday AS birthday, sex AS sex, address AS address FROM user WHERE user.id=? ）
			 */
			System.out.println("---->"+orders.getUser().getUsername());
		}
		
		//关闭session
		session.close();
	}
}
