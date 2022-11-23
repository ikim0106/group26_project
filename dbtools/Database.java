package dbtools;
import java.sql.*;

public class Database {

	public Connection dbConnection = null;

	private static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db26?autoReconnect=true&useSSL=false";
	private static String dbUsername = "Group26";
	private static String dbPassword = "CSCI3170";

	public void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.dbConnection = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
	}
}
