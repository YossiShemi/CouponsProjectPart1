package com.jb.DAO;

import com.jb.beans.Category;

//couponsproject.categories
public interface CategoryDAO {

	int getCategoryID(Category category);

	Category getCategoryName(int ID);
}
