package com.foodquest.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.models.ImageBean;
import com.foodquest.models.RecipeBean;
import com.foodquest.models.RecipeBean.RecipeStatus;
import com.foodquest.models.UserBean;
import com.foodquest.services.IRecipeManager;
import com.foodquest.services.IRecipeTypeManager;
import com.foodquest.services.IUserManager;
import com.foodquest.services.impl.RecipeManagerImpl;
import com.foodquest.services.impl.RecipeTypeManagerImpl;
import com.foodquest.services.impl.UserManagerImpl;
import com.foodquest.utils.CloudinaryUtil;
import com.foodquest.utils.lang.AppException;
import com.google.gson.Gson;

@WebServlet({ "/recipe/_create", "/recipe/_search", "/recipe/ajax/_search" })
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class RecipeServlet extends HttpServlet {
	private static IRecipeManager recipeManager;
	private static IUserManager userManager;
	private static IRecipeTypeManager recipeTypeManager;

	public void init() throws ServletException {
		super.init();
		recipeManager = RecipeManagerImpl.getInstance();
		userManager = UserManagerImpl.getInstance();
		recipeTypeManager = RecipeTypeManagerImpl.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqPath = request.getServletPath();
		response.setContentType("text/html");
		try {
			if (reqPath.equalsIgnoreCase("/recipe/_create")) {
				handleCreateRecipe(request, response, request.getSession());
			} else if (reqPath.equalsIgnoreCase("/recipe/_search")) {
				handleFindRecipe(request, response, request.getSession());
			} else if (reqPath.equalsIgnoreCase("/recipe/ajax/_search")) {
				handleAjaxSearch(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleAjaxSearch(HttpServletRequest request, HttpServletResponse response)
			throws AppException, IOException {
		String searchKey = request.getParameter("query");
		List<RecipeBean.RecipeStatus> recipeStatuses = new ArrayList<RecipeBean.RecipeStatus>();
		recipeStatuses.add(RecipeStatus.COMPLETED);
		recipeStatuses.add(RecipeStatus.PUBLISHED);
		List<RecipeBean> list = recipeManager.findAll(recipeStatuses, request.getParameter("query").toString());
		HashMap<Integer, String> result = new HashMap<>();
		for (RecipeBean bean : list) {
			result.put(bean.getId(), bean.getName());
		}
		Gson gson = new Gson();
		response.getWriter().println(gson.toJson(result));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reqPath = req.getServletPath();
		resp.setContentType("text/html");
		try {
			if (reqPath.equalsIgnoreCase("/recipe/_create")) {
				handleCreateRecipe(req, resp, req.getSession());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleCreateRecipe(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {

		RecipeBean recipeBean = getRecipe(request, response);

		Integer create_recipe_status = recipeManager.create(recipeBean);
		if (create_recipe_status != 0)
			request.getRequestDispatcher("/web/views/customer/uploadRecipeSuccess.jsp").forward(request, response);
	}

	private void handleFindRecipe(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {

		String searchKey = request.getParameter("query");
		List<RecipeBean.RecipeStatus> recipe_list = new ArrayList<RecipeBean.RecipeStatus>();
		recipe_list.add(RecipeStatus.COMPLETED);
		recipe_list.add(RecipeStatus.PUBLISHED);
		List<RecipeBean> list = recipeManager.findAll(recipe_list, searchKey);
		String category = request.getParameter("category");
		String cusine = request.getParameter("recipe");
		Set<RecipeBean> recipes = new HashSet<>();
		if (category != null) {
			for (RecipeBean bean : list) {
				if (bean.getCategory().getName().equalsIgnoreCase(category)) {
					if (!recipes.contains(bean))
						recipes.add(bean);
				}
			}
		}
		if (cusine != null) {
			recipes.clear();
			recipes = new HashSet<>();
			for (RecipeBean bean : recipeManager.findAll(recipe_list, searchKey)) {
				if (bean.getCusineType().getName().equalsIgnoreCase(cusine)) {
					System.out.println(bean);

					recipes.add(bean);

				}
			}
		}
		String id = request.getParameter("id");
		if (id != null) {
			Integer idd = Integer.parseInt(id);
			for (RecipeBean b : recipeManager.findAll(recipe_list, searchKey)) {
				if (b.getId() == idd) {
					recipes.clear();
					recipes.add(b);
					break;
				}
			}
		}
		request.setAttribute("recipe_list", new ArrayList<>(recipes));
		request.getRequestDispatcher("/web/views/customer/recipeDisplay.jsp").forward(request, response);
	}

	private RecipeBean getRecipe(HttpServletRequest request, HttpServletResponse resp)
			throws IOException, ServletException, AppException, ParseException {
		RecipeBean bean = new RecipeBean();
		String recipeName = request.getParameter("recipeName");
		String recipeDesc = request.getParameter("recipeDesc");
		String recipeCost = request.getParameter("recipeCost");
		String recipeQuantity = request.getParameter("recipeQuantity");
		String recipeCategoryName = request.getParameter("recipeCategoryName");
		String recipeCuisineName = request.getParameter("recipeCuisineName");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(request.getParameter("dateOfServing"));
		bean.setDateOfServing(date.getTime());
		bean.setName(recipeName);
		bean.setDescription(recipeDesc);
		bean.setPrice(Double.parseDouble(recipeCost));
		bean.setTotalServings(Integer.parseInt(recipeQuantity));
		boolean shouldUseDefaultImage = false;
		Part filePart = null;
		String fileName = null;
		try {
			filePart = request.getPart("file");
			fileName = getFileName(filePart);
		} catch (Exception e) {
			shouldUseDefaultImage = true;
		}
		if (!shouldUseDefaultImage) {
			OutputStream out = null;
			InputStream filecontent = null;
			final PrintWriter writer = resp.getWriter();
			try {

				String path = getServletContext().getRealPath("/") + "/web/images/" + fileName;
				System.out.println(path);
				out = new FileOutputStream(new File(path));
				filecontent = filePart.getInputStream();

				int read = 0;
				final byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				File image = new File(path);
				ImageBean imgBean = CloudinaryUtil.getInstance().uploadFile(image);
				bean.setImage(imgBean);
			} catch (FileNotFoundException fne) {
				writer.println("You either did not specify a file to upload or are "
						+ "trying to upload a file to a protected or nonexistent " + "location.");
				writer.println("<br/> ERROR: " + fne.getMessage());

			} finally {
				if (out != null) {
					out.close();
				}
				if (filecontent != null) {
					filecontent.close();
				}
			}
		} else {
			bean.setImage(recipeTypeManager.findCusineTypeByName(bean.getCusineType().getName()).getImage());
		}
		CategoryBean cb = recipeTypeManager.findCategoryByName(recipeCategoryName);
		bean.setCategory(cb);

		CusineTypeBean ctb = recipeTypeManager.findCusineTypeByName(recipeCuisineName);
		bean.setCusineType(ctb);

		UserBean ub = getUserFromSession(request);
		bean.setOwner(ub);
		System.out.println(bean);
		return bean;
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private UserBean getUserFromSession(HttpServletRequest request) {
		UserBean user = null;
		try {
			String email = request.getSession().getAttribute("user_email").toString();
			user = userManager.findByEmail(email);
		} catch (Exception e) {

		}
		return user;
	}
}