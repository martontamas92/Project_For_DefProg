package model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jetbrains.annotations.NotNull;

import business_model.Name;
import business_model.Neptun_Code;

public class Student extends User implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private Integer id;


	private Neptun_Code neptun;

	private Name name;
	//private String uname;
	//private String p;

	private Student(@NotNull Name name,@NotNull Neptun_Code neptun) {
		super();
		this.neptun = neptun;
		this.name = name;

	}

	public static Student studentBuilder(@NotNull Name name,@NotNull Neptun_Code neptun,@NotNull String uname,@NotNull String p) {
		return new Student(name,neptun);
	}

	public static Student studentBuilder(@NotNull Name name,@NotNull Neptun_Code neptun) {
		return new Student(name,neptun);
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
		return "Student [neptun=" + neptun + ", getName()=" + getName().toString() ;
	}


}

