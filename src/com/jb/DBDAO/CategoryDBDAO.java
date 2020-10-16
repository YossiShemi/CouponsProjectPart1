package com.jb.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jb.DAO.CategoryDAO;
import com.jb.beans.Category;
import com.jb.database.ConnectionPool;

//couponsproject1.categories
public class CategoryDBDAO implements CategoryDAO {

	private static final String getCategoryID = "SELECT ID FROM couponsproject1.categories where NAME=?;";
	private static final String getCategoryName = "SELECT NAME FROM couponsproject1.categories where ID=?;";

	public int getCategoryID(Category category) {
		Connection connection = null;
		int x = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getCategoryID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, category.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				x = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return x;
	}

	public Category getCategoryName(int ID) {
		Connection connection = null;
		String category = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = getCategoryName;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, ID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				category = resultSet.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return Category.valueOf(category);
	}

}
