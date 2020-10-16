package com.jb.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.jb.DBDAO.CompaniesDBDAO;
import com.jb.DBDAO.CouponsDBDAO;
import com.jb.DBDAO.CustomersDBDAO;
import com.jb.beans.Category;
import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;

public class Database {

	private static Connection connection;
	private static final String url = "jdbc:mysql://localhost:3306/couponsproject1?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
	private static final String username = "yossi20941";
	private static final String password = "DontEvenTry";

	// companies table
	public static void createTableCompanies() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "CREATE TABLE `couponsproject1`.`companies`" + "(`ID` INT NOT NULL AUTO_INCREMENT,"
					+ "`NAME` VARCHAR(45) NULL," + "`EMAIL` VARCHAR(45) NULL," + "`PASSWORD` VARCHAR(45) NULL,"
					+ "PRIMARY KEY (`ID`))";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void dropTableCompanies() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DROP TABLE `couponsproject1`.`companies`";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// customers table
	public static void createTableCustomers() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "CREATE TABLE `couponsproject1`.`customers`" + "(`ID` INT NOT NULL AUTO_INCREMENT,"
					+ "`FIRST_NAME` VARCHAR(45) NULL," + "`LAST_NAME` VARCHAR(45) NULL," + "`EMAIL` VARCHAR(45) NULL,"
					+ "`PASSWORD` VARCHAR(45) NULL," + " PRIMARY KEY (`ID`))";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void dropTableCustomers() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DROP TABLE `couponsproject1`.`customers`";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// categories table
	public static void createTableCategories() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "CREATE TABLE `couponsproject1`.`categories`" + "(`ID` INT NOT NULL AUTO_INCREMENT,"
					+ "`NAME` VARCHAR(45) NULL," + " PRIMARY KEY (`ID`))";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
			sql = "INSERT INTO `couponsproject1`.`categories` (`NAME`) VALUES ('Food');";
			statment = connection.prepareStatement(sql);
			statment.executeUpdate();
			sql = "INSERT INTO `couponsproject1`.`categories` (`NAME`) VALUES ('Electricity');";
			statment = connection.prepareStatement(sql);
			statment.executeUpdate();
			sql = "INSERT INTO `couponsproject1`.`categories` (`NAME`) VALUES ('Sport');";
			statment = connection.prepareStatement(sql);
			statment.executeUpdate();
			sql = "INSERT INTO `couponsproject1`.`categories` (`NAME`) VALUES ('Vacation');";
			statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void dropTableCategories() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DROP TABLE `couponsproject1`.`categories`";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// coupons table
	public static void createTableCoupons() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "CREATE TABLE `couponsproject1`.`coupons`"
					+ "(`ID` INT NOT NULL AUTO_INCREMENT,`COMPANY_ID` INT NULL,`CATEGORY_ID` INT NULL,"
					+ "`TITLE` VARCHAR(45) NULL,`DESCRIPTION` VARCHAR(45) NULL,`START_DATE` DATE NULL,"
					+ "`END_DATE` DATE NULL,`AMOUNT` INT NULL,`PRICE` DOUBLE NULL,`IMAGE` VARCHAR(45) NULL, PRIMARY KEY (`ID`),"
					+ "INDEX `COMPANY_ID_idx` (`COMPANY_ID` ASC) VISIBLE,INDEX `CATEGORY_ID_idx` (`CATEGORY_ID` ASC) VISIBLE,"
					+ "CONSTRAINT `COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `couponsproject1`.`companies` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,"
					+ "CONSTRAINT `CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `couponsproject1`.`categories` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION)";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void dropTableCoupons() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DROP TABLE `couponsproject1`.`coupons`";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// customers_vs_coupons table
	public static void createTableCustomers_Vs_Coupons() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "CREATE TABLE `couponsproject1`.`customers_vs_coupons`"
					+ "(`CUSTOMER_ID` INT NOT NULL,`COUPON_ID` INT NOT NULL,PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`),"
					+ "INDEX `COUPON_ID_idx` (`COUPON_ID` ASC) VISIBLE,"
					+ "CONSTRAINT `CUSTOMER_ID` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `couponsproject1`.`customers` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,"
					+ "CONSTRAINT `COUPON_ID` FOREIGN KEY (`COUPON_ID`) REFERENCES `couponsproject1`.`coupons` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION)";

			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void dropTableCustomers_Vs_Coupons() throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DROP TABLE `couponsproject1`.`customers_vs_coupons`";
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	// General functions
	public static void createAllTables() throws SQLException {
		createTableCompanies();
		createTableCustomers();
		createTableCategories();
		createTableCoupons();
		createTableCustomers_Vs_Coupons();
	}

	public static void dropAllTables() throws SQLException {
		dropTableCustomers_Vs_Coupons();
		dropTableCoupons();
		dropTableCompanies();
		dropTableCustomers();
		dropTableCategories();
	}

	public static void fillTables() {
		CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
		Company company = new Company("Coca-Cola", "Cola@gmail.com", "1111", null);
		Company company1 = new Company("Pepsi", "Pepso@gmail.com", "2222", null);
		Company company2 = new Company("Spring", "Spring@gmail.com", "3333", null);
		Company company3 = new Company("Prigat", "Prigat@gmail.com", "4444", null);
		companiesDBDAO.addCompany(company);
		companiesDBDAO.addCompany(company1);
		companiesDBDAO.addCompany(company2);
		companiesDBDAO.addCompany(company3);
		CustomersDBDAO customersDBDAO = new CustomersDBDAO();
		Customer customer = new Customer("Yossi", "Shemi", "yossi@gmail.com", "111", null);
		Customer customer1 = new Customer("Kobi", "Shshsa", "Kobi@gmail.com", "222", null);
		Customer customer2 = new Customer("Noam", "Marciano", "Noam@gmail.com", "333", null);
		Customer customer3 = new Customer("Eden", "Gal", "Eden@gmail.com", "444", null);
		customersDBDAO.addCustomer(customer);
		customersDBDAO.addCustomer(customer1);
		customersDBDAO.addCustomer(customer2);
		customersDBDAO.addCustomer(customer3);
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
		Coupon coupon = new Coupon(1, Category.Food, "couponOne", "50% discount", new Date(2020, 01, 01),
				new Date(2020, 02, 01), 10, 50, "imageURL");
		Coupon coupon1 = new Coupon(1, Category.Electricity, "couponTwo", "50% discount", new Date(2020, 02, 01),
				new Date(2020, 03, 01), 10, 50, "imageURL");
		Coupon coupon2 = new Coupon(2, Category.Vacation, "couponThree", "50% discount", new Date(2020, 03, 01),
				new Date(2020, 04, 01), 10, 50, "imageURL");
		Coupon coupon3 = new Coupon(2, Category.Sport, "couponFour", "50% discount", new Date(2020, 04, 01),
				new Date(2020, 05, 01), 10, 50, "imageURL");
		Coupon coupon4 = new Coupon(3, Category.Food, "couponFive", "1+1", new Date(2020, 05, 01),
				new Date(2020, 06, 01), 10, 50, "imageURL");
		Coupon coupon5 = new Coupon(2, Category.Electricity, "couponSix", "1+1", new Date(2020, 8, 01),
				new Date(2020, 9, 01), 10, 50, "imageURL");
		Coupon coupon6 = new Coupon(2, Category.Vacation, "couponSeven", "1+1", new Date(2020, 10, 01),
				new Date(2020, 11, 01), 10, 50, "imageURL");
		Coupon coupon7 = new Coupon(3, Category.Vacation, "couponEight", "1+1", new Date(2020, 10, 01),
				new Date(2020, 11, 01), 10, 50, "imageURL");
		couponsDBDAO.addCoupon(coupon);
		couponsDBDAO.addCoupon(coupon1);
		couponsDBDAO.addCoupon(coupon2);
		couponsDBDAO.addCoupon(coupon3);
		couponsDBDAO.addCoupon(coupon4);
		couponsDBDAO.addCoupon(coupon5);
		couponsDBDAO.addCoupon(coupon6);
		couponsDBDAO.addCoupon(coupon7);

	}

	// Getters&Setters
	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

}