package com.jb.facade;

import java.time.LocalDate;
import java.util.ArrayList;

import com.jb.beans.Category;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;
import com.jb.exceptions.IDDoesntExistException;
import com.jb.exceptions.InvalidAction;
import com.jb.exceptions.LoginFailed;

public class CustomerFacade extends ClientFacade {

	private int customerID;

	@Override
	public boolean login(String email, String password) throws LoginFailed {

		if (!customersDBDAO.isCustomerExists(email, password))
			throw new LoginFailed();
		this.customerID = customersDBDAO.getCustomerID(email, password);
		return true;

	}

	public void purchaseCoupon(Coupon coupon) throws IDDoesntExistException, InvalidAction {
		if (!couponsDBDAO.isCouponExists(coupon.getId()))
			throw new IDDoesntExistException();
		boolean flag = false; // Did customer buy this coupon?
		ArrayList<Coupon> userCoupons = couponsDBDAO.getAllCouponsByCustomerID(customerID);
		for (Coupon c : userCoupons) {
			if (c.getId() == coupon.getId())
				flag = true;
		}

		if (flag == true) {
			throw new InvalidAction("Sorry, Cannoy buy the same coupon twice.");
		}

		if (couponsDBDAO.getOneCoupon(coupon.getId()).getAmount() == 0) { // Did coupon still in store?
			throw new InvalidAction("Sorry, this coupon is no longer avaliable.");
		} else if (couponsDBDAO.getOneCoupon(coupon.getId()).getEndDate() // Did coupon expire today?
				.compareTo(java.sql.Date.valueOf(LocalDate.now())) == 0) {
			throw new InvalidAction("Sorry, cannot purchase. This coupon expiere today.");

		}

		else {
			couponsDBDAO.addCouponPurchase(this.customerID, coupon.getId());
		}
	}

	public ArrayList<Coupon> getAllCoupons() {
		return couponsDBDAO.getAllCouponsByCustomerID(customerID);
	}

	public ArrayList<Coupon> getAllCouponsByCategory(Category category) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		coupons = couponsDBDAO.getAllCouponsByCustomerID(this.customerID);
		ArrayList<Coupon> filteredCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getCategory() == category)
				filteredCoupons.add(coupon);
		}
		return filteredCoupons;
	}

	public ArrayList<Coupon> getAllCouponsUnderPrice(double price) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		coupons = couponsDBDAO.getAllCouponsByCustomerID(this.customerID);
		ArrayList<Coupon> filteredCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() < price) {
				filteredCoupons.add(coupon);
			}
		}
		return filteredCoupons;

	}

	public Customer getCustomerDetails() {

		Customer customer = customersDBDAO.getOneCustomer(this.customerID);
		customer.setCoupons(getAllCoupons());
		return customer;
	}

}
