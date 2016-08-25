package cn.wzd.mapper.lazy;

import java.util.List;

import cn.wzd.po.Orders;
import cn.wzd.po.User;

/**
 * 
 * @ClassName: UserOrdersItems 
 * @Description:用于进行延迟加载的查询的mapper接口。  
 * @author wzd 
 * @date 2016年8月24日 下午7:37:30 
 *
 */
public interface UserOrdersItems {
	/**
	 * 
	 * @Title: findOrderUserResultMapLazy 
	 * @Description: 查询订单，关联查询用户信息，使用延迟加载的方式。 
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return Orders    返回类型 
	 * @throws
	 */
	public List<Orders> findOrderUserResultMapLazy() throws Exception;
	
	/**
	 * 
	 * @Title: findUserById 
	 * @Description: 在user表中通过id查询用户信息
	 * @param @param id
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return User    返回类型 
	 * @throws
	 */
	public User findUserById(int id) throws Exception;
	
	/**
	 * 
	 * @Title: updateUser 
	 * @Description: 更新用户信息表，id作为索引唯一标识。 
	 * @param @param user
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void updateUser(User user) throws Exception;
}
