package com.foodquest.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.foodquest.models.DistributorBean;
import com.foodquest.models.ImageBean;
import com.foodquest.models.LocationBean;
import com.foodquest.models.Pair;
import com.foodquest.models.RecipeBean;
import com.foodquest.models.RecipeBean.RecipeDistributorStatus;
import com.foodquest.models.RecipeBean.RecipeStatus;
import com.foodquest.models.UserBean;
import com.foodquest.models.UserBean.AccountStatus;
import com.foodquest.models.UserBean.Gender;
import com.foodquest.models.UserBean.UserType;
import com.foodquest.services.IDistributorManager;
import com.foodquest.services.IRecipeManager;
import com.foodquest.services.IUserManager;
import com.foodquest.services.impl.DistributorManagerImpl;
import com.foodquest.services.impl.RecipeManagerImpl;
import com.foodquest.services.impl.UserManagerImpl;
import com.foodquest.utils.lang.AppException;

@WebServlet({ "/user/customer/register", "/user/distributor/register", "/user/login", "/user/logout", "/user/home",
		"/user/customer/update", "/distributor/update" })

public class UserServlet extends HttpServlet {
	private static IUserManager userManager;
	private static IDistributorManager distributorManager;
	private static IRecipeManager recipeManager;
	
	@Override
	public void init() throws ServletException {
		super.init();
		userManager = UserManagerImpl.getInstance();
		distributorManager = DistributorManagerImpl.getInstance();
		recipeManager = RecipeManagerImpl.getInstance();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reqPath = req.getServletPath();
		resp.setContentType("text/html");
		try {
			if (reqPath.equalsIgnoreCase("/user/login")) {
				handleLogin(req, resp, req.getSession());
			} else if (reqPath.equalsIgnoreCase("/user/customer/register")) {
				handleCustomerRegistration(req, resp, req.getSession());
				// resp.getWriter().write("<h3>Registration Success</h3>");
			} else if (reqPath.equalsIgnoreCase("/user/distributor/register")) {
				handleDistributorRegistration(req, resp, req.getSession());
				// resp.getWriter().write("<h3>Registration Success</h3>");
			} else if (reqPath.equalsIgnoreCase("/user/customer/update")) {
				handleUpdateUser(req, resp, req.getSession());
			}else if (reqPath.equalsIgnoreCase("/distributor/update")) {
				handleDistributorUpdate(req, resp, req.getSession());
				// resp.getWriter().write("<h3>Registration Success</h3>");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void handleDistributorUpdate(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
		Integer recipeId = Integer.parseInt(req.getParameter("recipe_id"));
		RecipeBean rb = recipeManager.findById(recipeId);
		rb.setStatus(RecipeStatus.COMPLETED);
		rb.setRecipeDistributorStatus(RecipeDistributorStatus.DISPATCHED);
		recipeManager.update(rb);
		resp.sendRedirect("/FoodQuest/web/views/distributor/DistributorPage.jsp");
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String reqPath = req.getServletPath();
		try {
			if (reqPath.equalsIgnoreCase("/user/home")) {
				handleUserWelcome(req, resp, req.getSession(false));
			} else if (reqPath.equalsIgnoreCase("/user/logout")) {
				HttpSession session = req.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				resp.sendRedirect("/FoodQuest/web/views/MainPage.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleLogin(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
		// req.getRequestDispatcher("/web/views/customer/CustomerPage.jsp").forward(req,
		// resp);
		String email = req.getParameter("email");
		String pwd = req.getParameter("password");
		System.out.println(email + " " + pwd);
		UserBean bean = userManager.authenticateByEmail(email, pwd);
		System.out.println(bean);
		if (bean == null) {
			resp.sendRedirect("/FoodQuest/web/views/signin.jsp");
		} else {
			session.setAttribute("user_email", email);
			session.setMaxInactiveInterval(15 * 60);
			req.setAttribute("user", bean);
			if(bean.getStatus().equals(AccountStatus.DISABLED)) {
				resp.getWriter().write("<h3>Your account has been disabled</h3>");
			}
			if (bean.getType().equals(UserType.CUSTOMER)) {
				req.getRequestDispatcher("/web/views/customer/CustomerPage.jsp").forward(req, resp);
			} else if (bean.getType().equals(UserType.ADMIN)) {
				req.getRequestDispatcher("/web/views/admin/AdminPage.jsp").forward(req, resp);
			} else if (bean.getType().equals(UserType.DISTRIBUTOR)) {
				req.getRequestDispatcher("/web/views/distributor/DistributorPage.jsp").forward(req, resp);
			}
		}
	}

	private void handleCustomerRegistration(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
			throws Exception {
		UserBean bean = getCustomer(req);
		Integer customer_status = userManager.create(bean);
		if (customer_status != 0)
			req.getRequestDispatcher("/web/views/signin.jsp").forward(req, resp);
	}

	private UserBean getCustomer(HttpServletRequest req) {
		String firstName = req.getParameter("fname");
		String lastName = req.getParameter("lname");
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String addressLine = req.getParameter("addressLine");
		String zipcode = req.getParameter("zipcode");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String country = req.getParameter("country");
		String phone = req.getParameter("phone");
		UserBean bean = new UserBean();
		bean.setFirstName(firstName);
		bean.setLastName(lastName);
		if (gender != null) {
			bean.setGender(Gender.valueOf(gender));
			if (bean.getGender().equals(Gender.MALE)) {
				bean.setImage(getDefaultMaleImage());
			} else {
				bean.setImage(getDefaultFemaleImage());
			}
		}
		if (email != null && !email.isEmpty())
			bean.setEmail(email);
		if (password != null && !password.isEmpty())
			bean.setPassword(password);

		LocationBean location = setLocation(addressLine, zipcode, city, state, country);
		bean.setLocation(location);
		bean.setPhone(phone);
		bean.setType(UserType.CUSTOMER);
		return bean;
	}

	private void handleDistributorRegistration(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
			throws Exception {
		DistributorBean distributorbean = getDistributor(req);
		UserBean user = getCustomer(req);
		user.setType(UserType.DISTRIBUTOR);
		Pair<Integer, Integer> customer_status = distributorManager.createDistributorAndUser(distributorbean, user);
		req.getRequestDispatcher("/web/views/signin.jsp").forward(req, resp);
	}

	private DistributorBean getDistributor(HttpServletRequest req) {
		String addressLine = req.getParameter("addressLine");
		String company = req.getParameter("distributorName");
		String website = req.getParameter("websiteUrl");
		String orgNum = req.getParameter("phone");
		String zipcode = req.getParameter("zipcode");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String country = req.getParameter("country");
		System.out.println(orgNum);
		DistributorBean distributorbean = new DistributorBean();
		distributorbean.setName(company);
		distributorbean.setWebsiteUrl(website);
		distributorbean.setPhone(orgNum);
		LocationBean location = setLocation(addressLine, zipcode, city, state, country);
		distributorbean.setLocation(location);
		distributorbean.setImage(getDefaultDistributorImage());
		System.out.println(distributorbean);
		return distributorbean;
	}

	private LocationBean setLocation(String addressLine, String zipcode, String city, String state, String country) {
		LocationBean location = new LocationBean();
		location.setAddressLine(addressLine);
		location.setZipcode(zipcode);
		location.setCity(city);
		location.setState(state);
		location.setCountry(country);
		return location;
	}

	private void handleUpdateUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
			throws AppException, IOException {
		UserBean user = getCustomer(req);
		user.setId(Integer.parseInt(req.getParameter("userId")));
		user.setLocation(null);
		user.setImage(null);
		userManager.update(user);
		resp.sendRedirect("/FoodQuest/web/views/customer/CustomerPage.jsp");
	}

	private void handleUserWelcome(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
			throws Exception {
		req.getRequestDispatcher("/FoodQuest/web/views/MainPage.jsp").forward(req, resp);
	}

	private ImageBean getDefaultMaleImage() {
		ImageBean image = new ImageBean();
		image.setImageUrl("http://res.cloudinary.com/di2fxuii1/image/upload/v1480480935/irkvau0fw9d0vvrp15hg.png");
		image.setImageUrl("https://res.cloudinary.com/di2fxuii1/image/upload/v1480480935/irkvau0fw9d0vvrp15hg.png");
		image.setPublicId("irkvau0fw9d0vvrp15hg");
		return image;
	}

	private ImageBean getDefaultFemaleImage() {
		ImageBean image = new ImageBean();
		image.setImageUrl("http://res.cloudinary.com/di2fxuii1/image/upload/v1480489375/iq6u1h6el0v40yfzuram.jpg");
		image.setSecureUrl("https://res.cloudinary.com/di2fxuii1/image/upload/v1480489375/iq6u1h6el0v40yfzuram.jpg");
		image.setPublicId("iq6u1h6el0v40yfzuram");
		return image;
	}

	private ImageBean getDefaultDistributorImage() {
		ImageBean image = new ImageBean();
		image.setImageUrl("http://res.cloudinary.com/di2fxuii1/image/upload/v1480491536/e9hrnmrmphvvlafkxpea.jpg");
		image.setSecureUrl("https://res.cloudinary.com/di2fxuii1/image/upload/v1480491536/e9hrnmrmphvvlafkxpea.jpg");
		image.setPublicId("e9hrnmrmphvvlafkxpea");
		return image;
	}
}