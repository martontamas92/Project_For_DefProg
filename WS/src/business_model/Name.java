package business_model;

import org.jetbrains.annotations.NotNull;

public class Name {

	private String firstName;
	private String lastName;
	//private String middleName;


	private Name (){}
	private Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		//this.middleName = middleName;
	}

	private static boolean canBuild(String firstName, String lastName) {
		return firstName != "" || lastName != "";
	}

	public static Name NameBuilder(@NotNull String firstName, @NotNull String lastName) { // have to get the not null annotation from somewhere
		if(canBuild(firstName, lastName)) {
			return new Name(firstName, lastName);
		}
		else {
			throw new IllegalArgumentException("A vezet�kn�v vagy a keresztn�v nem lehet �res!");
			}


	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());

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
		Name other = (Name) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Name [firstName=" + firstName + ", lastName=" + lastName + "]";
	}






}
