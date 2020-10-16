package com.jb.facade;

import java.util.ArrayList;

import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;
import com.jb.exceptions.IDDoesntExistException;
import com.jb.exceptions.InvalidAction;
import com.jb.exceptions.ItemExistException;
import com.jb.exceptions.LoginFailed;

public class AdminFacade extends ClientFacade {

	@Override
	public boolean login(String email, String password) throws LoginFailed {
		if (email == "admin@admin.com" && password == "admin")
			return true;
		return false;
	}

	public void addcompany(Company company) throws ItemExistException {
		if (companiesDBDAO.isCompanyExists(company.getEmail()))
			throw new ItemExistException("Email" + company.getEmail() + "already exists");
		else if (companiesDBDAO.isCompanyNameExists(company.getName()))
			throw new ItemExistException("Name" + company.getName() + "already exists");
		else
			companiesDBDAO.addCompany(company);
	}

	public void updateCompany(Company company) throws IDDoesntExistException, InvalidAction {
		if (!companiesDBDAO.isCompanyExists(company.getId())) {
			throw new IDDoesntExistException();
		}
		Company company2 = companiesDBDAO.getOneCompany(company.getId());
		if (!company.getName().equals(company2.getName())) {
			throw new InvalidAction("Cannot update company name");
		}
		companiesDBDAO.updateCompany(company);
	}

	public void deleteCompany(int companyID) throws IDDoesntExistException {
		if (!companiesDBDAO.isCompanyExists(companyID)) {
			throw new IDDoesntExistException();
		}
		companiesDBDAO.deleteCompany(companyID);
	}

	public ArrayList<Company> getALlCompanies() {
		ArrayList<Company> companies = companiesDBDAO.getAllCompanies();
		for (Company company : companies) {
			company.setCoupons(couponsDBDAO.getAllCouponsByCompanyID(company.getId()));
		}
		return companies;
	}

	public Company getOneCompany(int companyID) throws IDDoesntExistException {
		if (!companiesDBDAO.isCompanyExists(companyID)) {
			throw new IDDoesntExistException();
		}
		Company company = companiesDBDAO.getOneCompany(companyID);
		company.setCoupons(couponsDBDAO.getAllCouponsByCompanyID(companyID));
		return company;
	}

	public void addCustomer(Customer customer) throws ItemExistException {
		if (customersDBDAO.isCustomerExists(customer.getEmail()))
			throw new ItemExistException("Email" + customer.getEmail() + "already exists");
		customersDBDAO.addCustomer(customer);
	}

	public void updateCustomer(Customer customer) throws IDDoesntExistException {
		if (!customersDBDAO.isCustomerExists(customer.getId())) {
			throw new IDDoesntExistException();
		}
		customersDBDAO.updateCustomer(customer);
	}

	public void deleteCustomer(int customerID) throws IDDoesntExistException {
		if (!customersDBDAO.isCustomerExists(customerID)) {
			throw new IDDoesntExistException();
		}
		customersDBDAO.deleteCustomer(customerID);
	}

	public ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> customers = customersDBDAO.getAllCustomers();
		for (Customer customer : customers) {
			customer.setCoupons(couponsDBDAO.getAllCouponsByCustomerID(customer.getId()));
		}
		return customers;

	}

	public Customer getOneCustomer(int customerID) throws IDDoesntExistException {
		Customer customer = customersDBDAO.getOneCustomer(customerID);
		customer.setCoupons(couponsDBDAO.getAllCouponsByCustomerID(customerID));
		return customer;
	}

	public ArrayList<Coupon> getAllCoupons() {
		return couponsDBDAO.getAllCoupons();
	}
}
