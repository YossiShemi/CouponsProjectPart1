package com.jb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

	private static final int connect = 10;
	private static ConnectionPool instance = null;
	private Stack<Connection> connections = new Stack<>();

	private ConnectionPool() {
		for (int i = 1; i <= connect; i++) {
			try {
				Connection conn = DriverManager.getConnection(Database.getUrl(), Database.getUsername(),
						Database.getPassword());
				connections.push(conn);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static ConnectionPool getInstance() {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() throws InterruptedException {

		synchronized (connections) {
			if (connections.isEmpty()) {
				connections.wait();
			}

			return connections.pop();
		}
	}

	public void returnConnection(Connection conn) {

		synchronized (connections) {
			connections.push(conn);
			connections.notify();
		}
	}

	public void closeAllConnection() throws InterruptedException {

		synchronized (connections) {

			while (connections.size() < connect) {
				connections.wait();
			}

			for (Connection conn : connections) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
}