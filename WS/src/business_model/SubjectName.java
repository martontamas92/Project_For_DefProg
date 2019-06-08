package business_model;

public class SubjectName {
	private String subjectName;



	public static SubjectName subjectNameBuilder(String subjectName) {
		if(canBuild(subjectName)) {
			return new SubjectName(subjectName);
		}
		throw new IllegalArgumentException("A tantárgy név nem megfelelõ");
	}
	private static boolean canBuild(String subjectName) {
		if(subjectName == "" || subjectName.length() < 4) {
			return false;
		}
		return true;
	}

	private SubjectName(String subjectName) {

		this.subjectName = subjectName;
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
		SubjectName other = (SubjectName) obj;
		if (subjectName == null) {
			if (other.subjectName != null)
				return false;
		} else if (!subjectName.equals(other.subjectName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return subjectName;
	}



}
