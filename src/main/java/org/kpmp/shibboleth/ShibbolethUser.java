package org.kpmp.shibboleth;

import org.springframework.stereotype.Component;

@Component
public class ShibbolethUser {

	private String firstName;
	private String lastName;
	private String displayName;
	private String email;
	private String shibId;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailAddress) {
		this.email = emailAddress;
	}

	@Override
	public String toString() {
		return "firstName: " + firstName + ", lastName: " + lastName + ", displayName: " + displayName + ", email: "
				+ email + ", shibId: " + shibId;
	}

	public String getShibId() {
		return shibId;
	}

	public void setShibId(String shibId) {
		this.shibId = shibId;
	}
}
