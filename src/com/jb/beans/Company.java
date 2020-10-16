package com.jb.beans;

import java.util.ArrayList;
import java.util.List;

public class Company {

	private static int counter = 1;
	private int id;
	private String name;
	private String email;
	private String password;
	private List<Coupon> coupons;

	public Company(String name, String email, String password, ArrayList<Coupon> coupons) {
		this.id = counter++;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
		this.id = id;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "[Company id=" + String.format("%-3d", id) + "name=" + String.format("%-11s", name) + "email="
				+ String.format("%-20s", email) + "password=" + String.format("%-10s", password) + "coupons=" + numbers
				+ "]";
	}

}
