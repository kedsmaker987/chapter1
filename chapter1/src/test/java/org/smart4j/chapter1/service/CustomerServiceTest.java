package org.smart4j.chapter1.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter1.model.Customer;


public class CustomerServiceTest {
	private final CustomerService customerService;
	
	public CustomerServiceTest(){
		customerService = new CustomerService();
	}
	
	@Before
	public void init(){
		// TODO 初始化数据库
	}
	
	@Test
	public void getCustomerListTest() {
		List<Customer> customerList = customerService.getCustomerList();
		System.out.println(customerList.size());
	}
	
	@Test
	public void getCustomerTest() {
		long id = 1;
		Customer customer = customerService.getCusomer(id);
		Assert.assertNotNull(customer);
	}
	
	@Test
	public void createCustomerTest(){
		Map<String,Object> fieldMap = new HashMap<String,Object>();
		fieldMap.put("name", "customer100");
		fieldMap.put("contact","john");
		fieldMap.put("telephone", "13544253621");
		boolean result = customerService.createCustomer(fieldMap);
		Assert.assertTrue(result);
	}
	
	@Test
	public void updateCustomerTest(){
		long id = 1;
		Map<String,Object> fieldMap = new HashMap<String,Object>();
		fieldMap.put("contact", "Eric");
		boolean result = customerService.updateCustomer(id, fieldMap);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void deleteCustomerTest(){
		long id = 1;
		boolean result = customerService.deleteCustomer(id);
		Assert.assertTrue(result);
	}

}
