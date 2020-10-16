package com.jb.facade;

import com.jb.DAO.CategoryDAO;
import com.jb.DAO.CompaniesDAO;
import com.jb.DAO.CouponsDAO;
import com.jb.DAO.CustomersDAO;
import com.jb.DBDAO.CategoryDBDAO;
import com.jb.DBDAO.CompaniesDBDAO;
import com.jb.DBDAO.CouponsDBDAO;
import com.jb.DBDAO.CustomersDBDAO;
import com.jb.exceptions.LoginFailed;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDBDAO;
	protected CustomersDAO customersDBDAO;
	protected CouponsDAO couponsDBDAO;
	protected CategoryDAO categoryDBDAO;

	public ClientFacade() {
		companiesDBDAO = new CompaniesDBDAO();
		customersDBDAO = new CustomersDBDAO();
		couponsDBDAO = new CouponsDBDAO();
		categoryDBDAO = new CategoryDBDAO();
	}

	public abstract boolean login(String email, String password) throws LoginFailed;

}
