package com.jb.facade;

import java.util.ArrayList;

import com.jb.beans.Category;
import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.exceptions.IDDoesntExistException;
import com.jb.exceptions.LoginFailed;

public class CompanyFacade extends ClientFacade {

	private int companyID;

	@Override
	public boolean login(String email, String password) throws LoginFailed {
		if (!companiesDBDAO.isCompanyExists(email, password))
			throw new LoginFailed();
		this.companyID = companiesDBDAO.getCompanyID(email, password);
		return true;
	}

	public void addCoupon(Coupon coupon) {
		ArrayList<Coupon> coupons = couponsDBDAO.getAllCouponsByCompanyID(this.companyID);
		for (Coupon c : coupons) {
			if (c.getTitle() == coupon.getTitle()) {
				System.out.println("Coupon with same title already exists.");
				return;
			}
		}
		couponsDBDAO.addCoupon(coupon);

	}

	public void updateCoupon(Coupon coupon) throws IDDoesntExistException {
		if (!couponsDBDAO.isCouponExists(coupon.getId()))
			throw new IDDoesntExistException();
		couponsDBDAO.updateCoupon(coupon);
	}

	public void deleteCoupon(Coupon coupon) throws IDDoesntExistException {
		if (!couponsDBDAO.isCouponExists(coupon.getId()))
			throw new IDDoesntExistException();
		couponsDBDAO.deleteCouponPurchase(coupon.getId());
		couponsDBDAO.deleteCoupon(coupon.getId());
	}

	public ArrayList<Coupon> getAllCoupons() {
		return couponsDBDAO.getAllCouponsByCompanyID(this.companyID);
	}

	public ArrayList<Coupon> getAllCouponsByCategory(Category category) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		coupons = couponsDBDAO.getAllCouponsByCompanyID(this.companyID);
		ArrayList<Coupon> filteredCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getCategory() == category)
				filteredCoupons.add(coupon);
		}
		return filteredCoupons;
	}

	public ArrayList<Coupon> getAllCouponsUnderPrice(double price) {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		coupons = couponsDBDAO.getAllCouponsByCompanyID(this.companyID);
		ArrayList<Coupon> filteredCoupons = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() < price) {
				filteredCoupons.add(coupon);
			}
		}
		return filteredCoupons;

	}

	public Company getCompanyDetails() {
		Company company = companiesDBDAO.getOneCompany(this.companyID);
		company.setCoupons(getAllCoupons());
		return company;
	}

}
