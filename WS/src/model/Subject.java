package model;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

public class Subject {
	private ArrayList<Student> listeners;
	private Demonstrator demonstrator;

	public Subject(@NotNull ArrayList<Student> listeners, @NotNull Demonstrator demonstrator) {
		this.listeners = listeners;
		this.demonstrator = demonstrator;
	}




	public ArrayList<Student> getListeners() {
		return listeners;
	}

	public void setListeners(@NotNull ArrayList<Student> listeners) {
		this.listeners = listeners;
	}

	public Demonstrator getDemonstrator() {
		return demonstrator;
	}

	public void setDemonstrator(@NotNull Demonstrator demonstrator) {
		this.demonstrator = demonstrator;
	}



}
