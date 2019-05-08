package entity.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
	public Connection getConnection() throws Exception
	{
		try
		{
			String connectionURL = "jdbc:mysql://192.168.0.165:3306/project_test";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "test", "abcd");
			return connection;
		}
		catch (SQLException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}
