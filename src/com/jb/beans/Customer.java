package com.jb.beans;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	private static int counter = 1;
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<Coupon> coupons;

	public Customer(String firstName, String lastName, String email, String password, ArrayList<Coupon> coupons) {
		this.id = counter++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public Customer(int id, String firstName, String lastName, String email, String password,
			ArrayList<Coupon> coupons) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		ArrayList<Integer> numbers = null;
		if (coupons != null) {
			numbers = new ArrayList<Integer>();
			for (Coupon coupon : coupons) {
				numbers.add(coupon.getId());
			}
		}
		return "[Customer id=" + String.format("%-3d", id) + "firstName=" + String.format("%-10s", firstName)
				+ "lastName=" + String.format("%-10s", lastName) + "email=" + String.format("%-18s", email)
				+ "password=" + String.format("%-10s", password) + "coupons=" + numbers + "]";
	}

}
