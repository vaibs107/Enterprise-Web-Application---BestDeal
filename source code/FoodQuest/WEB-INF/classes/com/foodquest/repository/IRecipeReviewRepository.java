package com.foodquest.repository;

import java.util.List;

import com.foodquest.models.RecipeReviewBean;
import com.foodquest.utils.lang.AppException;

public interface IRecipeReviewRepository {
	String create(RecipeReviewBean review) throws AppException;

	List<RecipeReviewBean> getReviewsByRecipeId(Integer recipeId) throws AppException;

}
