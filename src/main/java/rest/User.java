package rest;

public class User {
	//id
	String email;
	String password;
	String firstname;
	String lastname;
	boolean isAdmin;
	String address;
	String city;
	String postal;
	String phone;
	double credit;
	Boolean wantsNewsletter;
	String activation;
	
	public User(String email, String password, String firstname, String lastname, boolean isAdmin, 
			String address, String city, String postal, String phone, double credit,
			boolean wantsNewsletter, String activation) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.isAdmin = isAdmin;
		this.address = address;
		this.city = city;
		this.postal = postal;
		this.credit = credit;
		this.wantsNewsletter = wantsNewsletter;
		this.activation = activation;
	}

	protected String getEmail() {
		return email;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	protected String getFirstname() {
		return firstname;
	}

	protected void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	protected String getLastname() {
		return lastname;
	}

	protected void setLastname(String lastname) {
		this.lastname = lastname;
	}

	protected boolean isAdmin() {
		return isAdmin;
	}

	protected void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	protected String getAddress() {
		return address;
	}

	protected void setAddress(String address) {
		this.address = address;
	}

	protected String getCity() {
		return city;
	}

	protected void setCity(String city) {
		this.city = city;
	}

	protected String getPostal() {
		return postal;
	}

	protected void setPostal(String postal) {
		this.postal = postal;
	}

	protected String getPhone() {
		return phone;
	}

	protected void setPhone(String phone) {
		this.phone = phone;
	}

	protected double getCredit() {
		return credit;
	}

	protected void setCredit(double credit) {
		this.credit = credit;
	}

	protected Boolean getWantsNewsletter() {
		return wantsNewsletter;
	}

	protected void setWantsNewsletter(Boolean wantsNewsletter) {
		this.wantsNewsletter = wantsNewsletter;
	}

	protected String getActivation() {
		return activation;
	}

	protected void setActivation(String activation) {
		this.activation = activation;
	}
	
	public String toString() {
		return  this.email + " " + this.password + " " + this.firstname + " " + this.lastname + " " + this.isAdmin + " " + this.address + " " + this.city + " "  + this.postal + " " + this.credit + " " + this.wantsNewsletter +  " " +this.activation;
	}
}