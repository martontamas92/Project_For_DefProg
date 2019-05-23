package model;

import org.jetbrains.annotations.NotNull;

import business_model.Name;

public class Demonstrator extends User{
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}



	private Name name;

	public Demonstrator() {}
	public Demonstrator(@NotNull Name name) {
		super();
		this.name = name;

	}



	@Override
	public Name getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setName(@NotNull Name name) {
		// TODO Auto-generated method stub
		this.name = name;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Demonstrator other = (Demonstrator) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Demonstrator [name=" + name + "]";
	}




}
