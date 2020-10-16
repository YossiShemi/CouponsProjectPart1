package com.jb.DAO;

import java.util.ArrayList;

import com.jb.beans.Company;

//couponsproject.companies
public interface CompaniesDAO {

	boolean isCompanyExists(String email, String password);

	boolean isCompanyExists(int companyID); // Check for exception

	boolean isCompanyExists(String email); // Check for exception

	public boolean isCompanyNameExists(String name); // Check for adminFacade

	int getCompanyID(String email, String password);

	void addCompany(Company company);

	void deleteCompany(int companyID);

	void updateCompany(Company company);

	ArrayList<Company> getAllCompanies();

	Company getOneCompany(int companyID);

}
