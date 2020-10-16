package com.jb.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.jb.DAO.CompaniesDAO;
import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.database.ConnectionPool;

//couponsproject1.companies
public class CompaniesDBDAO implements CompaniesDAO {

	private static final String isCompanyExistsEmailPassword = "SELECT * FROM couponsproject1.companies where email= ? AND password= ?";
	private static final String isCompanyExistsID = "SELECT * FROM couponsproject1.companies where id=?";
	private static final String isCompanyExistsEmail = "SELECT * FROM couponsproject1.companies where email=?";
	private static final String isCompanyExistsName = "SELECT * FROM couponsproject1.companies where name=?";
	private static final String getCompanyIdbyEmailPassword = "SELECT ID FROM couponsproject1.companies where email= ? AND password= ?";
	private static final String addCompany = "INSERT INTO `couponsproject1`.`companies` (`ID`, `NAME`, `EMAIL`, `PASSWORD`) VALUES (?,?,?,?)";;
	private static final String deleteCompany = "DELETE FROM `couponsproject1`.`companies` WHERE (`ID` = ?)";
	private static final String updateCompany = "UPDATE `couponsproject1`.`companies` SET `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
	private static final String getAllCompanies = "SELECT * FROM couponsproject1.companies";
	private static final String getOneCompanyByID = "SELECT * FROM couponsproject1.companies WHERE (`ID` = ?)";

	@Override
	public boolean isCompanyExists(String email, String password) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCompanyExistsEmailPassword;
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
	public boolean isCompanyExists(int companyID) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCompanyExistsID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
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
	public boolean isCompanyExists(String email) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCompanyExistsEmail;
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

	// Check for adminFacade
	public boolean isCompanyNameExists(String name) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = isCompanyExistsName;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
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

	public int getCompanyID(String email, String password) {
		Connection connection = null;
		int id = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getCompanyIdbyEmailPassword;
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
	public void addCompany(Company company) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = addCompany;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, company.getId());
			statement.setString(2, company.getName());
			statement.setString(3, company.getEmail());
			statement.setString(4, company.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	public void deleteCompany(int companyID) {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			// Delete all purchases of coupons belong to this company from C_V_C
			// Delete all coupons from coupons table
			CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
			ArrayList<Coupon> coupons = couponsDBDAO.getAllCouponsByCompanyID(companyID);
			for (Coupon coupon : coupons) {
				couponsDBDAO.deleteCouponPurchase(coupon.getId());
				couponsDBDAO.deleteCoupon(coupon.getId());
			}

			// Delete from companies table
			String sql = deleteCompany;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	public void updateCompany(Company company) {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = updateCompany;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getEmail());
			statement.setString(2, company.getPassword());
			statement.setInt(3, company.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	public ArrayList<Company> getAllCompanies() {
		ArrayList<Company> companies = new ArrayList<Company>();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getAllCompanies;
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);
				companies.add(new Company(id, name, email, password, null));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return companies;
	}

	public Company getOneCompany(int companyID) {

		Company company = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getOneCompanyByID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);
				company = new Company(companyID, name, email, password, null);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return company;
	}

}
