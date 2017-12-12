package org.smart4j.chapter1.service;

import java.util.List;
import java.util.Map;

import org.smart4j.chapter1.model.Customer;

/**
 * 提供客户信息
 * @author Administrator
 *
 */
public class CustomerService {

	/**
	 * 获取客户列表
	 * 
	 * @param keyword
	 * @return
	 */
	public List<Customer> getCustomerList() {
		// TODU
		return null;
	}

	/**
	 * 获取客户
	 * 
	 * @param id
	 * @return
	 */
	public Customer getCusomer(long id) {
		// TODO

		return null;
	}

	/**
	 * 创建客户
	 * 
	 * @param fieldMap
	 * @return
	 */
	public boolean createCustomer(Map<String, Object> fieldMap) {
		// TODO

		return false;
	}
	
	
	public boolean updateCustomer(long id,Map<String,Object> fieldMap){
		// TODO
		
		return false;
	}

	public boolean deleteCustomer(long id) {

		// TODO
		return false;
	}

}
