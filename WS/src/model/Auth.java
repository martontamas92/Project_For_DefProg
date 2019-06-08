package model;

import java.io.Serializable;

import org.jetbrains.annotations.NotNull;

import business_model.Name;
import business_model.Password;
import business_model.UserName;

public class Auth implements Serializable{
	private Integer id;
	private UserName uname;
	private Password passwd;
	/*
	 * The password must contain one capital letter one number and one special character
	 *
	 * */


	private Auth() {}

	public Auth(@NotNull Integer id, @NotNull UserName uname, @NotNull Password passwd) {
		super();
		this.id = id;
		this.uname = uname;
		this.passwd = passwd;
	}

	public Auth( @NotNull UserName uname, @NotNull Password passwd) {
		super();

		this.uname = uname;
		this.passwd = passwd;
	}

	public Integer getId() {
		return id;
	}

	public UserName getUname() {
		return uname;
	}
	public void setUname(UserName uname) {
		this.uname = uname;
	}
	public Password getPasswd() {
		return passwd;
	}
	public void setPasswd(Password passwd) {
		this.passwd = passwd;
	}
	public void setId(Integer id) {
		this.id = id;
	}




}
