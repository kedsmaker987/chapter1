package org.smart4j.chapter1.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.helper.DatabaseHelper;
import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.util.PropsUtil;

/**
 * 提供客户信息
 * @author Administrator
 *
 */
public class CustomerService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	

	/**
	 * 获取客户列表
	 * 
	 * @param keyword
	 * @return
	 */
	public List<Customer> getCustomerList() {
		String sql = "select * from customer";
		return DatabaseHelper.queryEntityList(Customer.class, sql);
	}

	/**
	 * 获取客户
	 * 
	 * @param id
	 * @return
	 */
	public Customer getCusomer(long id) {
		String sql = "select * from customer where id = ?";
		return DatabaseHelper.queryEntity(Customer.class, sql, id);
	}

	/**
	 * 创建客户
	 * 
	 * @param fieldMap
	 * @return
	 */
	public boolean createCustomer(Map<String, Object> fieldMap) {
		return DatabaseHelper.insertEntity(Customer.class, fieldMap);
	}
	
	
	public boolean updateCustomer(long id,Map<String,Object> fieldMap){
		return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
	}

	public boolean deleteCustomer(long id) {
		return DatabaseHelper.deleteEntity(Customer.class, id);
	}

}
