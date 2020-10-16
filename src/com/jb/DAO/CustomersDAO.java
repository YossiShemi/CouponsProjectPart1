package com.jb.DAO;

import java.util.ArrayList;

import com.jb.beans.Customer;

//couponsproject.customers
public interface CustomersDAO {

	boolean isCustomerExists(String email, String password);

	boolean isCustomerExists(int customerID); // Check for exception

	boolean isCustomerExists(String email); // Check for exception

	int getCustomerID(String email, String password);

	void addCustomer(Customer customer);

	void deleteCustomer(int customerID);

	void updateCustomer(Customer customer);

	ArrayList<Customer> getAllCustomers();

	Customer getOneCustomer(int customerID);
}
