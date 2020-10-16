package com.jb.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.jb.DAO.CustomersDAO;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;
import com.jb.database.ConnectionPool;

//couponsproject1.customers
public class CustomersDBDAO implements CustomersDAO {

	private static final String isCustomerExistsEmailPassword = "SELECT * FROM couponsproject1.customers where email= ? AND password= ?";
	private static final String isCustomerExistsID = "SELECT * FROM couponsproject1.customers where id=?";
	private static final String isCustomerExistsEmail = "SELECT * FROM couponsproject1.customers where email=?";
	private static final String getCustomerIdbyEmailPassword = "SELECT ID FROM couponsproject1.customers where email= ? AND password= ?";
	private static final String addCustomer = "INSERT INTO `couponsproject1`.`customers` (`ID`,`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?,?,?,?,?)";
	private static final String deleteCustomer = "DELETE FROM `couponsproject1`.`customers` WHERE (`ID` = ?)";
	private static final String updateCustomer = "UPDATE `couponsproject1`.`customers` SET `FIRST_NAME` = ?, `LAST_NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?)";
	private static final String getAllCustomers = "SELECT * FROM couponsproject1.customers";
	private static final String getCustomerByID = "SELECT * FROM couponsproject1.customers WHERE (`ID` = ?)";

	@Override
	public boolean isCustomerExists(String email, String password) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCustomerExistsEmailPassword;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flag = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return flag;
	}

	// Check for exception
	public boolean isCustomerExists(int customerID) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCustomerExistsID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flag = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return flag;
	}

	// Check for exception
	public boolean isCustomerExists(String email) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCustomerExistsEmail;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flag = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return flag;
	}

	public int getCustomerID(String email, String password) {
		Connection connection = null;
		int id = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getCustomerIdbyEmailPassword;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return id;
	}

	@Override
	public void addCustomer(Customer customer) {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = addCustomer;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customer.getId());
			statement.setString(2, customer.getFirstName());
			statement.setString(3, customer.getLastName());
			statement.setString(4, customer.getEmail());
			statement.setString(5, customer.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void deleteCustomer(int customerID) {

		Connection connection = null;
		try {
			// Delete all customer purchases
			CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
			ArrayList<Coupon> coupons = couponsDBDAO.getAllCouponsByCustomerID(customerID);
			for (Coupon coupon : coupons) {
				couponsDBDAO.deleteCouponPurchase(coupon.getId());
			}
			// Delete from customers table
			connection = ConnectionPool.getInstance().getConnection();
			String sql = deleteCustomer;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = updateCustomer;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customer.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getAllCustomers;
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);
				customers.add(new Customer(id, firstName, lastName, email, password, null));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return customers;
	}

	public Customer getOneCustomer(int customerID) {

		Customer customer = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getCustomerByID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);
				customer = new Customer(customerID, firstName, lastName, email, password, null);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return customer;
	}

}
