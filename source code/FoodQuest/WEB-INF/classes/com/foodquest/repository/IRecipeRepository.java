package com.foodquest.repository;

import java.util.List;

import com.foodquest.models.RecipeBean;
import com.foodquest.models.RecipeBean.RecipeStatus;
import com.foodquest.utils.lang.AppException;

public interface IRecipeRepository {
	Integer create(RecipeBean bean) throws AppException;
	
	RecipeBean findById(Integer id) throws AppException;
	
	List<RecipeBean> findAll(RecipeStatus status, String searchName) throws AppException;
	
	List<RecipeBean> findAll(List<RecipeStatus> statuses, String serachKey) throws AppException;
	
	Boolean update(RecipeBean bean) throws AppException;
	
	Boolean delete(Integer id) throws AppException;
}
