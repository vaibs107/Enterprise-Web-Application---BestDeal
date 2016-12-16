package com.foodquest.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodquest.models.RecipeBean;
import com.foodquest.models.RecipeBean.RecipeDistributorStatus;
import com.foodquest.models.RecipeBean.RecipeStatus;
import com.foodquest.models.UserBean;
import com.foodquest.services.IRecipeManager;
import com.foodquest.services.IUserManager;
import com.foodquest.services.impl.RecipeManagerImpl;
import com.foodquest.services.impl.UserManagerImpl;
import com.foodquest.utils.lang.AppException;

@WebServlet({ "/admin/update/customers", "/admin/delete/customers", "/admin/update/products",
		"/admin/delete/products" })

public class AdminServlet extends HttpServlet {
	private static IUserManager userManager;
	private static IRecipeManager recipeManager;

	@Override
	public void init() throws ServletException {
		super.init();
		userManager = UserManagerImpl.getInstance();
		recipeManager = RecipeManagerImpl.getInstance();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqPath = request.getServletPath();
		try {
			if (reqPath.equalsIgnoreCase("/admin/update/customers")) {
				handleUpdateCustomers(request, response);
			} else if (reqPath.equalsIgnoreCase("/admin/delete/customers")) {
				handleDeleteCustomers(request, response);
			} else if (reqPath.equalsIgnoreCase("/admin/update/products")) {
				handleUpdateProducts(request, response);
			} else if (reqPath.equalsIgnoreCase("/admin/delete/products")) {
				handleDeleteProducts(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void handleDeleteProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int recipeId = Integer.parseInt(request.getParameter("recipeId"));
		Boolean deleteStatus = recipeManager.delete(recipeId);
		if (deleteStatus == true)
			response.sendRedirect(request.getHeader("referer"));
	}

	private void handleUpdateProducts(HttpServletRequest request, HttpServletResponse response)
			throws AppException, IOException, ServletException {
		Integer recipeId = Integer.parseInt(request.getParameter("recipe_id"));
		RecipeBean rb = new RecipeBean();
		rb.setId(recipeId);
		rb.setRecipeDistributorStatus(RecipeDistributorStatus.ASSIGNED);
		rb.setStatus(RecipeStatus.PUBLISHED);
		recipeManager.update(rb);
		rb = recipeManager.findById(rb.getId());

		try {
			recipeManager.assignDistributor(rb, 5);
		} catch (Exception e) {
			recipeManager.assignDistributor(rb, 95);
		}
		recipeManager.update(rb);
		request.getRequestDispatcher("/web/views/admin/AdminPage.jsp").forward(request, response);
	}

	private void handleDeleteCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		Boolean deleteStatus = userManager.delete(customerId);
		if (deleteStatus == true)
			response.sendRedirect(request.getHeader("referer"));
	}

	private void handleUpdateCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		UserBean ub = userManager.findById(customerId);
		Boolean updateStatus = userManager.update(ub);
		if (updateStatus == true)
			response.sendRedirect(request.getHeader("referer"));
	}
}