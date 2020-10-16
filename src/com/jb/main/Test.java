package com.jb.main;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jb.DBDAO.CompaniesDBDAO;
import com.jb.DBDAO.CouponsDBDAO;
import com.jb.DBDAO.CouponsExpirationDaily;
import com.jb.DBDAO.CustomersDBDAO;
import com.jb.beans.Category;
import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;
import com.jb.database.ConnectionPool;
import com.jb.database.Database;
import com.jb.exceptions.IDDoesntExistException;
import com.jb.exceptions.InvalidAction;
import com.jb.exceptions.ItemExistException;
import com.jb.exceptions.LoginFailed;
import com.jb.facade.AdminFacade;
import com.jb.facade.ClientType;
import com.jb.facade.CompanyFacade;
import com.jb.facade.CustomerFacade;
import com.jb.facade.LoginManager;
import com.jb.utils.Utils;

public class Test {

	public static void testAll() {

		CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
		CustomersDBDAO customersDBDAO = new CustomersDBDAO();
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

		Utils.printTestLine("Tables Before Tests");
		Utils.printCompaniesTable(companiesDBDAO.getAllCompanies());
		Utils.printCustomersTable(customersDBDAO.getAllCustomers());
		Utils.printCouponsTable(couponsDBDAO.getAllCoupons());

		System.out.println();
		System.out.println();
		System.out.println();
		Utils.printTestLine("Admin Facade");
		AdminFacade admin = null;
		try {
			admin = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",
					ClientType.Administrator);
		} catch (LoginFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Company company = new Company("AdminTest", "AdminTest", "AdminTest", null);
		Customer customer = new Customer("AdminTest", "AdminTest", "AdminTest", "AdminTest", null);

		try {
			admin.addcompany(company);
		} catch (ItemExistException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Added company AdminTest.");

		try {
			admin.addCustomer(customer);
		} catch (ItemExistException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		couponsDBDAO.addCouponPurchase(customersDBDAO.getCustomerID(customer.getEmail(), customer.getPassword()), 1);
		System.out.println("Added customer AdminTest.");
		System.out.println("Get one customer:");
		try {
			System.out.println(admin.getOneCustomer(2));
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Get one customer:");
		try {
			System.out.println(admin.getOneCompany(2));
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			admin.deleteCompany(1);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Deleted company ID 1 and all coupons related.");
		try {
			admin.deleteCustomer(1);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Deleted customer ID 1 and all coupond related.");
		System.out.println("Get one company:");
		try {
			System.out.println(admin.getOneCompany(2));
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Get one customer:");
		try {
			System.out.println(admin.getOneCustomer(2));
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		company.setEmail("Updated");
		try {
			try {
				admin.updateCompany(company);
			} catch (InvalidAction e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Updated company AdminTest email to Updated.");
		customer.setFirstName("Updated");
		try {
			admin.updateCustomer(customer);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Updated customer AdminTest name to Updated.");

		System.out.println();
		System.out.println();
		System.out.println();
		Utils.printTestLine("Company Facade");
		CompanyFacade companyFacade = null;
		try {
			companyFacade = (CompanyFacade) LoginManager.getInstance().login("Updated", "AdminTest",
					ClientType.Company);
		} catch (LoginFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Coupon coupon = new Coupon(companiesDBDAO.getCompanyID(company.getEmail(), company.getPassword()),
				Category.Food, "CompanyTest", "CompanyTest", new Date(2020, 07, 01), new Date(2020, 07, 01), 100, 100,
				"CompanyTest");
		Coupon coupon1 = new Coupon(companiesDBDAO.getCompanyID(company.getEmail(), company.getPassword()),
				Category.Sport, "CompanyTest1", "CompanyTest1", new Date(2020, 07, 01), new Date(2020, 07, 01), 100,
				150, "CompanyTest1");
		Coupon coupon2 = new Coupon(companiesDBDAO.getCompanyID(company.getEmail(), company.getPassword()),
				Category.Vacation, "CompanyTest2", "CompanyTest2", new Date(2020, 07, 01), new Date(2020, 07, 01), 100,
				170, "CompanyTest2");
		companyFacade.addCoupon(coupon);
		companyFacade.addCoupon(coupon1);
		companyFacade.addCoupon(coupon2);
		System.out.println("Get company logged in details:");
		System.out.println(companyFacade.getCompanyDetails());
		System.out.println("Added coupons CompanyTest to AdminTest company.");
		System.out.println("All coupons of company logged in:");
		Utils.printCouponsTable(companyFacade.getAllCoupons());
		System.out.println("Get all company's coupons from specific category: ");
		Utils.printCouponsTable(companyFacade.getAllCouponsByCategory(Category.Food));
		System.out.println("Get all company's coupons ubder price 170 $: ");
		Utils.printCouponsTable(companyFacade.getAllCouponsUnderPrice(170));
		try {
			companyFacade.deleteCoupon(coupon1);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Deleted coupon CompanyTest1 from coupons table.");
		coupon.setPrice(9999999);
		try {
			companyFacade.updateCoupon(coupon);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Update coupon CompanyTest price to 9999999.");

		System.out.println();
		System.out.println();
		System.out.println();
		Utils.printTestLine("Customer Facade");
		CustomerFacade customerFacade = null;
		try {
			customerFacade = (CustomerFacade) LoginManager.getInstance().login("AdminTest", "AdminTest",
					ClientType.Customer);
		} catch (LoginFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Customer logged in details:");
		System.out.println(customerFacade.getCustomerDetails());
		System.out.println("Purchasing coupons: ");

		try {
			customerFacade.purchaseCoupon(coupon);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAction e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			customerFacade.purchaseCoupon(coupon);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAction e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			customerFacade.purchaseCoupon(coupon2);
		} catch (IDDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAction e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All customer coupons:");
		Utils.printCouponsTable(customerFacade.getAllCoupons());
		System.out.println("Get all customer's coupons from vacation category:");
		Utils.printCouponsTable(customerFacade.getAllCouponsByCategory(Category.Vacation));
		System.out.println("Get all coupons under price 150$ :");
		Utils.printCouponsTable(customerFacade.getAllCouponsUnderPrice(150));
		System.out.println("Customer logged in details after buying coupons:");
		System.out.println(customerFacade.getCustomerDetails());

		System.out.println();
		System.out.println();
		System.out.println();
		Utils.printTestLine("Clean Expiered Coupons");
		Utils.printCouponsTable(couponsDBDAO.getAllCoupons());
		CouponsExpirationDaily cleaner = new CouponsExpirationDaily();
		System.out.println();
		System.out.println("Today's date: " + new Date());
		System.out.println();
		cleaner.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cleaner.pause();
		Utils.printCouponsTable(couponsDBDAO.getAllCoupons());

		System.out.println();
		System.out.println();
		System.out.println();
		Utils.printTestLine("Tables After Tests");
		Utils.printCompaniesTable(admin.getALlCompanies());
		Utils.printCustomersTable(admin.getAllCustomers());
		Utils.printCouponsTable(admin.getAllCoupons());

		System.out.println("\n");
		System.out.println("Done !");

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {

		// Connecting to driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Database handlers
		ConnectionPool.getInstance();
		Database.createAllTables();
		Database.fillTables();

		testAll();

		// Drop DB and connections
		Database.dropAllTables();
		ConnectionPool.getInstance().closeAllConnection();

	}

}