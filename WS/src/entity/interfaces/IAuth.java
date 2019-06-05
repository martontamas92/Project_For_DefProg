package entity.interfaces;

import java.sql.SQLException;

import model.Auth;

public interface IAuth {

	boolean add(Auth a)  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	boolean userNameExists(String username)  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	boolean valid(String username, String passwd)  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;

}
