package business_model;

import org.jetbrains.annotations.NotNull;

public class Password {
	private String passwd;

	private Password(@NotNull String passwd) {
		super();
		this.passwd = passwd;
	}

	private static boolean canBuild(@NotNull String passwd) {
		if(!passwd.equals("") && passwd.length() >= 8) {
			return true;
		}
		return false;
	}

	public static Password passwordBuilder(@NotNull String passwd) {
		if(canBuild(passwd) ) {
			passwd = PasswordUtils.digestPassword(passwd);
			return new Password(passwd);
		}
		throw new IllegalArgumentException("A jelszó nem lehet üres!");
	}

	public String getPasswd() {
		return passwd;
	}




	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}




}
