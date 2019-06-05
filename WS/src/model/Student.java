package model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jetbrains.annotations.NotNull;

import business_model.Name;
import business_model.Neptun_Code;
@Entity
public class Student extends User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="st_neptun")
	private Neptun_Code neptun;
	@Column(name="st_name")
	private Name name;

	private Student() {}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Student(@NotNull Name name,@NotNull Neptun_Code neptun) {
		super();
		this.neptun = neptun;
		this.name = name;

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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (neptun == null) {
			if (other.neptun != null)
				return false;
		} else if (!neptun.equals(other.neptun))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((neptun == null) ? 0 : neptun.hashCode());
		return result;
	}
	// maybe we will not need it
	@Override
	public String toString() {
		return "Student [neptun=" + neptun + ", getName()=" + getName().toString() ;
	}



}

