package com.jb.DBDAO;

import java.util.ArrayList;
import java.util.Date;

import com.jb.DAO.CouponsDAO;
import com.jb.beans.Coupon;

public class CouponsExpirationDaily extends Thread {

	private static final int sleep = 24 * 60 * 60 * 100; // 24 hours
	private CouponsDAO couponsDBDAO;
	private boolean quit;

	public CouponsExpirationDaily() {
		this.couponsDBDAO = new CouponsDBDAO();
		this.quit = false;
	}

	public void run() {
		while (!quit) {
			System.out.println("Start cleaner");

			ArrayList<Coupon> coupons = couponsDBDAO.getAllCoupons();
			for (Coupon coupon : coupons) {
				if (coupon.getEndDate().before(new Date())) {
					couponsDBDAO.deleteCouponPurchase(coupon.getId());
					couponsDBDAO.deleteCoupon(coupon.getId());
				}
			}

			try {

				System.out.println("Cleaner is going to sleep " + sleep);
				Thread.sleep(sleep);

			} catch (Exception e) {
				e.getMessage();
			}
		}

	}

	public void pause() {
		this.quit = true;
	}

}
