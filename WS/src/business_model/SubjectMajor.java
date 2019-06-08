package business_model;

public class SubjectMajor {
	private String subjectMajor;

	public SubjectMajor subjectMajorBuilder(String subjectMajor) {
		if(canBuild(subjectMajor)) {
			return new SubjectMajor(subjectMajor);
		}
		throw new IllegalArgumentException("A szak neve nem megfelelõ!");
	}


	private SubjectMajor(String subjectMajor) {
		super();
		this.subjectMajor = subjectMajor;

	}



	private boolean canBuild(String subjectMajor) {
		if(subjectMajor == "" || subjectMajor.length() < 4) {
			return false;
		}
		return true;
	}

	public String getSubjectMajor() {
		return subjectMajor;
	}

	public void setSubjectMajor(String subjectMajor) {
		this.subjectMajor = subjectMajor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subjectMajor == null) ? 0 : subjectMajor.hashCode());
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
		SubjectMajor other = (SubjectMajor) obj;
		if (subjectMajor == null) {
			if (other.subjectMajor != null)
				return false;
		} else if (!subjectMajor.equals(other.subjectMajor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  subjectMajor;
	}




}
