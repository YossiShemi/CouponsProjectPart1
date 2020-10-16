package com.jb.DAO;

import java.util.ArrayList;

import com.jb.beans.Coupon;

//couponsproject.coupons
public interface CouponsDAO {

	boolean isCouponExists(int couponID); // Check for exception

	void addCoupon(Coupon coupon);

	void updateCoupon(Coupon coupon);

	void deleteCoupon(int couponID);

	ArrayList<Coupon> getAllCoupons();

	Coupon getOneCoupon(int couponID);

	void addCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int couponID);

	ArrayList<Coupon> getAllCouponsByCompanyID(int companyID); // For company facade

	ArrayList<Coupon> getAllCouponsByCustomerID(int customerID); // For user facade

}
