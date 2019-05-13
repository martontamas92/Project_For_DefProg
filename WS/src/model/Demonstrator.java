package model;

import org.jetbrains.annotations.NotNull;

import business_model.Name;

public class Demonstrator extends User{

	private Name name;

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



}
