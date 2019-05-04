package model;

import business_model.Name;

public class Auth {
	private Name name;
	private String uname;
	private String passwd;

	protected Name getName() {
		return name;
	}
	protected void setName(Name name) {
		this.name = name;
	}
	protected String getUname() {
		return uname;
	}
	protected void setUname(String uname) {
		this.uname = uname;
	}
	protected String getPasswd() {
		return passwd;
	}
	protected void setPasswd(String passwd) {
		this.passwd = passwd;
	}


}
