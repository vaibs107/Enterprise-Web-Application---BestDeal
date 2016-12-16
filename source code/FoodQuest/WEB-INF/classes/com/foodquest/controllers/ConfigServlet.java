package com.foodquest.controllers;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.models.Pair;
import com.foodquest.models.db.DBConnectionManager;
import com.foodquest.services.IRecipeTypeManager;
import com.foodquest.services.impl.RecipeTypeManagerImpl;
import com.foodquest.test.Test;
import com.foodquest.utils.RecipeTypeXmlParser;
import com.foodquest.utils.lang.AppException;

public class ConfigServlet extends HttpServlet {
	DBConnectionManager dbConnectionManager;
	private IRecipeTypeManager recipeTypeManager;
	private RecipeTypeXmlParser recipeTypeXmlParser;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			dbConnectionManager = DBConnectionManager.getInstance();
			recipeTypeManager = RecipeTypeManagerImpl.getInstance();
			recipeTypeXmlParser = RecipeTypeXmlParser.getInstance();
			populateRecipeTypes(config.getServletContext().getRealPath("/"));
			Test.createAdmin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		dbConnectionManager.closeConnections();
	}

	private void populateRecipeTypes(String rootPath) throws AppException {
		Pair<List<CategoryBean>, List<CusineTypeBean>> pair = recipeTypeXmlParser.getCategoriesAndCusines(rootPath);
		for (CategoryBean category : pair.getLeft()) {
			if (recipeTypeManager.findCategoryByName(category.getName()) == null) {
				recipeTypeManager.createCategory(category);
			}
		}
		for (CusineTypeBean cusine : pair.getRight()) {
			System.out.println(cusine);
			if (recipeTypeManager.findCusineTypeByName(cusine.getName()) == null) {
				recipeTypeManager.createCusineType(cusine);
			} else {
				cusine.setId(recipeTypeManager.findCusineTypeByName(cusine.getName()).getId());
				recipeTypeManager.updateCusineType(cusine);
			}
		}
	}
}
