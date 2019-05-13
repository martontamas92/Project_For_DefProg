package entity.interfaces;

import model.Auth;

public interface IAuth {

	boolean add(Auth a);
	boolean userNameExists(String username);
	boolean valid(String username, String passwd);

}
