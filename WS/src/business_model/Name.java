package business_model;

import org.jetbrains.annotations.NotNull;

public class Name {
	private String firstName;
	private String lastName;
	private String middleName;

	private Name(String firstName, String middleName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
	}

	private boolean canBuild(String firstName, String middleName, String lastName) {
		return firstName != "" ||
			   middleName != "" ||
			   lastName != "";
	}

	public Name NameBuilder(@NotNull String firstName, @NotNull String middleName, @NotNull String lastName) { // have to get the not null annotation from somewhere
		if(canBuild(firstName, middleName, lastName)) {
			return new Name(firstName,middleName,lastName);
		}
		else {
			throw new IllegalArgumentException(); // im working on it... MT
			}


	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	@Override
	public String toString() {
		return "Name [firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + "]";
	}






}
