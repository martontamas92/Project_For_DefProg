package business_model;

import org.jetbrains.annotations.NotNull;

public class Password {
	private String passwd;

	private Password(@NotNull String passwd) {
		super();
		this.passwd = passwd;
	}

	private static boolean canBuild(@NotNull String passwd) {
		if(!passwd.equals("")) {
			return true;
		}
		return false;
	}

	public static Password passwordBuilder(@NotNull String passwd) {
		if(canBuild(passwd)) {
			return new Password(passwd);
		}else {}
			throw new IllegalArgumentException();
	}

	public String getPasswd() {
		return passwd;
	}




	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}




}
