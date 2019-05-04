package model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jetbrains.annotations.NotNull;
@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

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
