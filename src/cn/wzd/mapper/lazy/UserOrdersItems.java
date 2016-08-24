package cn.wzd.mapper.lazy;

import java.util.List;

import cn.wzd.po.Orders;

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
}
