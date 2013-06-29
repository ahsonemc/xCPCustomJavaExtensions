package com.emc.smiles;

public class User {
	private String corpID;
	private String firstName;
	private String lastName;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String jobTitle;
	private String streetAddress;
	private String division;
	private String department;
	private String phoneNumber;
	private String manager;
	private String emailAddress;

	public String getCorpID() {
		return corpID;
	}
	public void setCorpID(String corpID) {
		this.corpID = corpID;
	}
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	@Override
	public String toString() {
		return "User [corpID=" + corpID + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", city=" + city + ", state="
				+ state + ", country=" + country + ", zipCode=" + zipCode
				+ ", jobTitle=" + jobTitle + ", streetAddress=" + streetAddress
				+ ", division=" + division + ", department=" + department
				+ ", phoneNumber=" + phoneNumber + ", manager=" + manager
				+ ", emailAddress=" + emailAddress + "]";
	}

}
