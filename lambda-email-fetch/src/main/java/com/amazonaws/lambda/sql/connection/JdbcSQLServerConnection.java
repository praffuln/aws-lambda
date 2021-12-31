package com.amazonaws.lambda.sql.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  * This program demonstrates how to establish database connection to
 * Microsoft  * SQL Server.    
 */
public class JdbcSQLServerConnection {

	public static void main(String[] args) {
		checkSqlConnection();
	}

	public static void checkSqlConnection() {
		Connection conn = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String dbURL = 	"jdbc:sqlserver://rds-mssql.cwtsp1cve8au.ap-south-1.rds.amazonaws.com:1433;databaseName=BluePrism;";
			String user = "sa";
			String pass = "server.123";
			conn = DriverManager.getConnection(dbURL, user, pass);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}