package com.foodquest.repository;

import java.util.List;
import com.foodquest.models.RecipeBean;
import com.foodquest.utils.lang.AppException;

public interface IRecipeAnalyticsRepository {
	
	List<RecipeBean> findTopRatedRecipes(int limit) throws AppException;

	List<String> findTopZipcodesBasedOnRecipeSale(int limit) throws AppException;

	List<RecipeBean> findTopReviewedRecipes(int limit) throws AppException;
}
