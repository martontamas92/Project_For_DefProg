package model;

import business_model.Name;
import business_model.Neptun_Code;

public class Student extends User{

	private Neptun_Code neptun;

	private Name name;
	private String uname;
	private String p;

	private Student(Name name, Neptun_Code neptun, String uname, String p) {
		super();
		this.neptun = neptun;
		this.name = name;
		this.uname = uname;
		this.p = p;
	}

	public Student studentBuilder() {
		return null;
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

	public Neptun_Code getNeptun() {
		return neptun;
	}

	public void setNeptun(Neptun_Code neptun) {
		this.neptun = neptun;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	// maybe we will not need it
	@Override
	public String toString() {
		return "Student [neptun=" + neptun + ", getName()=" + getName() + ", getP()=" + getP() + ", getClass()="
				+ getClass() + ", toString()=" + super.toString() + "]";
	}


}

