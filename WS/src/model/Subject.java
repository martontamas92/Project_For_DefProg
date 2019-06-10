package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jetbrains.annotations.NotNull;

@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String subjectName;
	private String subjectMajor;
	private Demonstrator demonstrator;

	private Subject() {
	}

	public Subject(@NotNull Integer id, @NotNull String subjectName, @NotNull String subjectMajor,
			@NotNull Demonstrator demonstrator) {
		super();
		this.id = id;
		this.subjectName = subjectName;
		this.subjectMajor = subjectMajor;
		this.demonstrator = demonstrator;
	}

	/*
	 * public Subject(@NotNull Integer id, @NotNull String subjectName, @NotNull
	 * Demonstrator demonstrator) { super(); this.id = id; this.subjectName =
	 * subjectName; this.demonstrator = demonstrator; }
	 */

	public Subject(@NotNull String subjectName,@NotNull String subjectMajor, @NotNull Demonstrator demonstrator) {
		super();

		this.subjectName = subjectName;
		this.subjectMajor = subjectMajor;
		this.demonstrator = demonstrator;
	}

	public String getSubjectMajor() {
		return subjectMajor;
	}

	public void setSubjectMajor(String subjectMajor) {
		this.subjectMajor = subjectMajor;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	/*
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((demonstrator == null) ? 0 :
	 * demonstrator.hashCode()); result = prime * result + ((subjectName == null) ?
	 * 0 : subjectName.hashCode()); return result; }
	 *
	 *
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; Subject other = (Subject) obj; if (demonstrator == null) { if
	 * (other.demonstrator != null) return false; } else if
	 * (!demonstrator.equals(other.demonstrator)) return false; if (subjectName ==
	 * null) { if (other.subjectName != null) return false; } else if
	 * (!subjectName.equals(other.subjectName)) return false; return true; }
	 *
	 *
	 * @Override public String toString() { return "Subject [subjectName=" +
	 * subjectName + ", demonstrator=" + demonstrator + "]"; }
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((demonstrator == null) ? 0 : demonstrator.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((subjectMajor == null) ? 0 : subjectMajor.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (subjectMajor == null) {
			if (other.subjectMajor != null)
				return false;
		} else if (!subjectMajor.equals(other.subjectMajor))
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
		return "Subject [id=" + id + ", subjectName=" + subjectName + ", subjectMajor=" + subjectMajor
				+ ", demonstrator=" + demonstrator + "]";
	}

}
