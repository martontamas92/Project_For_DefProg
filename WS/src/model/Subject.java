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
	private String subjectName;
	//private ArrayList<Student> listeners;
	private Demonstrator demonstrator;



//	public Subject(@NotNull ArrayList<Student> listeners, @NotNull Demonstrator demonstrator) {
//		this.listeners = listeners;
//		this.demonstrator = demonstrator;
//	}
//
//	public ArrayList<Student> getListeners() {
//		return listeners;
//	}
//
//	public void setListeners(@NotNull ArrayList<Student> listeners) {
//		this.listeners = listeners;
//	}

	private Subject() {}

	public Subject(@NotNull String subjectName, @NotNull Demonstrator demonstrator) {
		super();

		this.subjectName = subjectName;
		this.demonstrator = demonstrator;
	}

	public Demonstrator getDemonstrator() {
		return demonstrator;
	}

	public void setDemonstrator(@NotNull Demonstrator demonstrator) {
		this.demonstrator = demonstrator;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((demonstrator == null) ? 0 : demonstrator.hashCode());
		result = prime * result + ((subjectName == null) ? 0 : subjectName.hashCode());
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
		Subject other = (Subject) obj;
		if (demonstrator == null) {
			if (other.demonstrator != null)
				return false;
		} else if (!demonstrator.equals(other.demonstrator))
			return false;
		if (subjectName == null) {
			if (other.subjectName != null)
				return false;
		} else if (!subjectName.equals(other.subjectName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subject [subjectName=" + subjectName + ", demonstrator=" + demonstrator + "]";
	}



}
