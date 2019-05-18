package entity.interfaces;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnection {
	public static Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Connection conn = null;

        try (FileInputStream f = new FileInputStream("D:\\Defensive_Programming_Project\\Project_For_DefProg\\WS\\src\\entity\\interfaces\\db.properties")) {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            // load the properties file
            Properties pros = new Properties();
            pros.load(f);
            String url = pros.getProperty("url");
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");

            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
