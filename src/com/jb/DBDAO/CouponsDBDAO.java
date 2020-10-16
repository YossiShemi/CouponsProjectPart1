package com.jb.DBDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.jb.DAO.CouponsDAO;
import com.jb.beans.Category;
import com.jb.beans.Coupon;
import com.jb.database.ConnectionPool;
import com.jb.utils.Utils;

//couponsproject1.coupons
public class CouponsDBDAO implements CouponsDAO {

	private static final String isCouponExists = "SELECT * FROM couponsproject1.coupons where id=?";
	private static final String addCoupon = "INSERT INTO `couponsproject1`.`coupons`"
			+ " (`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String updateCoupon = "	UPDATE `couponsproject1`.`coupons` SET "
			+ "`CATEGORY_ID` = ? ,`TITLE` = ? ,`DESCRIPTION` = ? ,`START_DATE` = ? ,`END_DATE` = ?, `AMOUNT` = ? ,`PRICE` = ? ,`IMAGE` = ?"
			+ " WHERE (`ID` = ?);";
	private static final String deleteCoupon = "DELETE FROM `couponsproject1`.`coupons` WHERE (`ID` = ?);";
	private static final String getAllCoupons = "SELECT * FROM couponsproject1.coupons";
	private static final String getOneCoupon = "SELECT * FROM couponsproject1.coupons  WHERE (`ID` = ?);";
	private static final String addCouponPurchase = "INSERT INTO `couponsproject1`.`customers_vs_coupons` (`CUSTOMER_ID`, `COUPON_ID`) VALUES (?, ?);";
	private static final String deleteCouponPurchaseByCouponCustomer = "delete from couponsproject1.customers_vs_coupons where CUSTOMER_ID= ? and COUPON_ID= ?;";
	private static final String getAllCouponsByCompanyID = "SELECT * FROM couponsproject1.coupons where COMPANY_ID = ?";
	private static final String getAllCouponsByCustomerID = "SELECT COUPON_ID FROM couponsproject1.customers_vs_coupons where CUSTOMER_ID = ?";
	private static final String deleteCouponPurchaseByCouponID = "delete from couponsproject1.customers_vs_coupons where COUPON_ID= ?;";

	// Check for exception
	public boolean isCouponExists(int couponID) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCouponExists;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
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

	public void addCoupon(Coupon coupon) {
		Connection connection = null;
		CategoryDBDAO CategoryDBDAO = new CategoryDBDAO();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = addCoupon;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCompanyID());
			statement.setInt(2, CategoryDBDAO.getCategoryID(coupon.getCategory()));
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, Utils.convertUtilDateToSQL(coupon.getStartDate()));
			statement.setDate(6, Utils.convertUtilDateToSQL(coupon.getEndDate()));
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());

			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	public void updateCoupon(Coupon coupon) {

		Connection connection = null;
		CategoryDBDAO CategoryDBDAO = new CategoryDBDAO();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = updateCoupon;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, CategoryDBDAO.getCategoryID(coupon.getCategory()));
			statement.setString(2, coupon.getTitle());
			statement.setString(3, coupon.getDescription());
			statement.setDate(4, (Date) coupon.getStartDate());
			statement.setDate(5, (Date) coupon.getEndDate());
			statement.setInt(6, coupon.getAmount());
			statement.setDouble(7, coupon.getPrice());
			statement.setString(8, coupon.getImage());
			statement.setInt(9, coupon.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	public void deleteCoupon(int couponID) {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = deleteCoupon;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public ArrayList<Coupon> getAllCoupons() {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		CategoryDBDAO categoryDBDAO = new CategoryDBDAO();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getAllCoupons;
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = categoryDBDAO.getCategoryName(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price,
						image));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupons;
	}

	public Coupon getOneCoupon(int couponID) {
		Coupon coupon = null;
		CategoryDBDAO categoryDBDAO = new CategoryDBDAO();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getOneCoupon;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = categoryDBDAO.getCategoryName(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupon = new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price,
						image);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupon;
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = addCouponPurchase;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.executeUpdate();
			Coupon coupon = this.getOneCoupon(couponID);
			coupon.setAmount(coupon.getAmount() - 1);// Decrease coupon amount
			this.updateCoupon(coupon);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = deleteCouponPurchaseByCouponCustomer;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	// Get all company coupons
	public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		CategoryDBDAO categoryDBDAO = new CategoryDBDAO();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getAllCouponsByCompanyID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				Category category = categoryDBDAO.getCategoryName(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price,
						image));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupons;
	}

	// Get all customer coupons
	public ArrayList<Coupon> getAllCouponsByCustomerID(int customerID) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		ArrayList<Integer> filterCoupons = new ArrayList<Integer>(); // to get all coupons ID from C_vs_C for the ID
																		// provided
		CategoryDBDAO categoryDBDAO = new CategoryDBDAO();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getAllCouponsByCustomerID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				filterCoupons.add(resultSet.getInt(1));
			}
			for (int i = 0; i < filterCoupons.size(); i++) {
				sql = "SELECT * FROM couponsproject1.coupons where id= ?;";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, filterCoupons.get(i));
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					int id = resultSet.getInt(1);
					int companyID = resultSet.getInt(2);
					Category category = categoryDBDAO.getCategoryName(resultSet.getInt(3));
					String title = resultSet.getString(4);
					String description = resultSet.getString(5);
					Date startDate = resultSet.getDate(6);
					Date endDate = resultSet.getDate(7);
					int amount = resultSet.getInt(8);
					double price = resultSet.getDouble(9);
					String image = resultSet.getString(10);
					coupons.add(new Coupon(id, companyID, category, title, description, startDate, endDate, amount,
							price, image));
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return coupons;
	}

	// Use in cleaning expired coupons class, companyFacade
	public void deleteCouponPurchase(int couponID) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = deleteCouponPurchaseByCouponID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

}
