package bbs.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import bbs.exception.SQLRuntimeException;

public class DBUtil {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/bbs";
	private static final String USER = "java";
	private static final String PASSWORD = "ic-com.Test";

	static {
		try{
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	public static Connection getConnection(){
		Connection connection = null;
		try{
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
		return connection;
	}

	public static void commit(Connection connection){
		try{
			connection.commit();
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static void rollback(Connection connection){
		try{
			connection.rollback();
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}
}
