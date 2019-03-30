package model;
import org.jetbrains.annotations.NotNull;

import business_model.Name;

public class Demonstrator extends User{
	private Name name;
	private String uname;
	private String p;

	public Demonstrator(@NotNull Name name, @NotNull String uname, @NotNull String p) {
		super();
		this.name = name;
		this.uname = uname;
		this.p = p;
	}

	@Override
	public String getUname() {
		// TODO Auto-generated method stub
		return this.uname;
	}

	@Override
	public void setUname(String uname) {
		// TODO Auto-generated method stub
		this.uname = uname;
	}

	@Override
	public Name getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setName(Name name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public String getP() {
		// TODO Auto-generated method stub
		return this.p;
	}

	@Override
	public void setP(String p) {
		// TODO Auto-generated method stub
		this.p = p;
	}

}
