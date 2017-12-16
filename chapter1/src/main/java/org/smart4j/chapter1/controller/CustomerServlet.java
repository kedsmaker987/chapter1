package org.smart4j.chapter1.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;

/**
 * 创建客户
 * @author Administrator
 *
 */
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet{
	
	private CustomerService customerService;
	
	@Override
	public void init(){
		customerService = new CustomerService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Customer> customerList = customerService.getCustomerList();
		req.setAttribute("customerList", customerList);
		req.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req,HttpServletResponse resp){
		//TODO
	}
}
