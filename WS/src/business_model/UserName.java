package business_model;

import org.jetbrains.annotations.NotNull;

public class UserName {
	/*
	 * Author: Marton Tamas
	 * the username can't be empty and must be longer than 4 characters
	 * */
	private String uname;

	private UserName(@NotNull String uname) {
		this.uname=uname;
	}

	public static UserName userNameBuilder(@NotNull String uname) {
		if(canBuild(uname)) {
			return new UserName(uname);
		}else {
			throw new IllegalArgumentException("Argument doesn't fit the conditions");
		}
	}

	private static boolean canBuild(String uname) {
		if(!uname.equals("") && uname.length() >=4) {
			return true;
		}
		return false;

	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}



}
