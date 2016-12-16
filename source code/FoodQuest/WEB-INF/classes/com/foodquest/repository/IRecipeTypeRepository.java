package com.foodquest.repository;

import java.util.List;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.utils.lang.AppException;

public interface IRecipeTypeRepository {
	Integer createCategory(CategoryBean bean) throws AppException;
	
	Integer createCusineType(CusineTypeBean bean) throws AppException;
	
	CategoryBean findCategoryById(Integer id) throws AppException;
	
	CategoryBean findCategoryByName(String name) throws AppException;
	
	List<CategoryBean> findAllCategories() throws AppException;
	
	CusineTypeBean findCusineTypeById(Integer id) throws AppException;
	
	CusineTypeBean findCusineTypeByName(String name) throws AppException;
	
	List<CusineTypeBean> findAllCusines() throws AppException;
	
	Boolean updateCategory(CategoryBean bean) throws AppException;
	
	Boolean updateCusineType(CusineTypeBean bean) throws AppException;
	
	Boolean deleteCategory(Integer id) throws AppException;
	
	Boolean deleteCusineType(Integer id) throws AppException;
} 
