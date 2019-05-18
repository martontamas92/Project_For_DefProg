package model;

import java.util.Date;

import business_model.QrCode;



public class Lecture {
	private Date day;
	private Demonstrator demonstrator;
	private Subject subject;
	private QrCode qr;

	public Lecture(Date day, Demonstrator demonstrator, Subject subject, QrCode qr) {
		super();
		this.day = day;
		this.demonstrator = demonstrator;
		this.subject = subject;
		this.qr = qr;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Demonstrator getDemonstrator() {
		return demonstrator;
	}

	public void setDemonstrator(Demonstrator demonstrator) {
		this.demonstrator = demonstrator;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public QrCode getQr() {
		return qr;
	}

	public void setQr(QrCode qr) {
		this.qr = qr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((demonstrator == null) ? 0 : demonstrator.hashCode());
		result = prime * result + ((qr == null) ? 0 : qr.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		Lecture other = (Lecture) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (demonstrator == null) {
			if (other.demonstrator != null)
				return false;
		} else if (!demonstrator.equals(other.demonstrator))
			return false;
		if (qr == null) {
			if (other.qr != null)
				return false;
		} else if (!qr.equals(other.qr))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lecture [day=" + day + ", demonstrator=" + demonstrator + ", subject=" + subject + ", qr=" + qr + "]";
	}



}
