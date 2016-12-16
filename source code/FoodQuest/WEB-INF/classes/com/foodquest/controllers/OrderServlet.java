package com.foodquest.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodquest.models.LocationBean;
import com.foodquest.models.OrderBean;
import com.foodquest.models.OrderBean.OrderStatus;
import com.foodquest.models.OrderItem;
import com.foodquest.models.RecipeBean;
import com.foodquest.models.UserBean;
import com.foodquest.models.UserBean.UserType;
import com.foodquest.services.IOrderManager;
import com.foodquest.services.IRecipeManager;
import com.foodquest.services.IUserManager;
import com.foodquest.services.impl.OrderManagerImpl;
import com.foodquest.services.impl.RecipeManagerImpl;
import com.foodquest.services.impl.UserManagerImpl;
import com.foodquest.utils.lang.AppException;
import com.foodquest.utils.lang.AuthorizationException;

@WebServlet({ "/order", "/order/updateStatus", "/order/cart", "/order/confirm", "/order/cnfForm", "/order/item",
		"/order/item/update", "/order/item/remove", "/order/pay", "/order/edit", "/order/delete" })
public class OrderServlet extends HttpServlet {
	IRecipeManager recipeManager;
	IOrderManager orderManager;
	IUserManager userManager;

	@Override
	public void init() throws ServletException {
		recipeManager = RecipeManagerImpl.getInstance();
		orderManager = OrderManagerImpl.getInstance();
		userManager = UserManagerImpl.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestPath = request.getServletPath();
		UserBean user = getUserFromSession(request);
		try {
			if (requestPath.equalsIgnoreCase("/order")) {
				String cnf = request.getParameter("orderCnf");
				System.out.println(cnf);
				OrderBean order = orderManager.findByCnf(cnf);

				if (order != null) {
					if (order.getCustomer().getId().compareTo(user.getId()) != 0
							&& user.getType().equals(UserType.CUSTOMER)) {
						throw new AuthorizationException("You don't have permission to view this order");
					}
					response.getWriter().write(getOrderHtml(order));
				} else {
					response.getWriter().write("<h4>No order found with the confirmation number <%=cnf %></h4>");
				}
			} else if (requestPath.equalsIgnoreCase("/order/updateStatus")) {
				OrderBean order = orderManager.findById(Integer.parseInt(request.getParameter("orderId")));
				String status = request.getParameter("status");
				orderManager.update(order);
				
				if (!status.equalsIgnoreCase("NONE")) {
					order.setStatus(OrderStatus.valueOf(status));
					StringBuilder builder = new StringBuilder();
					builder.append(getCommonOpeningHtml());
					builder.append("<h4>Order has been changed to " + order.getStatus() + " status successfully</h4>");
					builder.append(getCommonClosingHtml());
					response.getWriter().write(builder.toString());
				}
			} else if (requestPath.equalsIgnoreCase("/order/cart")) {
				OrderBean order = orderManager.findOrderInCartByUserId(user.getId());
				response.getWriter().write(getCartHtml(order));
			} else if (requestPath.equalsIgnoreCase("/order/confirm")) {
				int oid = Integer.parseInt(request.getParameter("oid"));
				OrderBean order = orderManager.findById(oid);
				response.getWriter().write(getOrderCnfHtml(order));
			} else if (requestPath.equalsIgnoreCase("/order/cnfForm")) {
				response.getWriter().write(getOrderCnfForm());
			} else if (requestPath.equals("/order/edit")) {
				response.getWriter().write(getEditOrderHtml());
			} else if (requestPath.equals("/order/delete")) {
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				orderManager.delete(orderId);
				// request.getRequestDispatcher(request.getHeader("REFERRER")).forward(request,
				// response);
				response.sendRedirect(request.getHeader("referer"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String requestPath = request.getServletPath();
			UserBean user = getUserFromSession(request);
			if (requestPath.equalsIgnoreCase("/order/item")) {
				handleAddOrderItem(request, response, request.getSession(), user);
			} else if (requestPath.equalsIgnoreCase("/order/item/update")
					|| requestPath.equalsIgnoreCase("/order/item/remove")) {
				handleUpdateOrderItem(request, response, request.getSession(), user);
			} else if (requestPath.equalsIgnoreCase("/order/pay")) {
				String[] saveOptions = request.getParameterValues("saveOptions");
				boolean isSaveCardSelected = false, isSaveAddressSelected = false;
				for (String option : saveOptions) {
					if (option.equalsIgnoreCase("saveCard")) {
						isSaveCardSelected = true;
					} else if (option.equalsIgnoreCase("saveAddress")) {
						isSaveAddressSelected = false;
					}
				}

				OrderBean order = handleOrderConfirmation(request, response, request.getSession(), user,
						isSaveCardSelected, isSaveAddressSelected);
				if (order != null) {
					StringBuilder builder = new StringBuilder();
					builder.append("<h1>Payment success</h1><br>");
					builder.append(
							"<h1>Use this number to track order :: &nbsp" + order.getConfirmationNumber() + "</h1>");

					response.getWriter().write(builder.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private UserBean getUserFromSession(HttpServletRequest request) {
		UserBean user = null;
		try {
			String email = request.getSession().getAttribute("user_email").toString();

			user = userManager.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private void handleAddOrderItem(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			UserBean user) throws Exception {
		int recipeId = Integer.parseInt(request.getParameter("recipeId"));
		OrderBean order = orderManager.findOrderInCartByUserId(user.getId());
		RecipeBean prod = recipeManager.findById(recipeId);
		if (order == null || order.getId() == null) {
			order = new OrderBean();
			order.addItem(prod, 1);
			order.setCustomer(user);

			orderManager.create(order);
		} else {
			System.out.println(order);
			System.out.println(prod);
			order.addItem(prod, 1);
			orderManager.update(order);
		}
		response.sendRedirect("/BestDeal/order/cart");
	}

	private void handleUpdateOrderItem(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			UserBean user) throws Exception {
		int recipeId = Integer.parseInt(request.getParameter("recipeId"));
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		boolean shouldAdd = request.getServletPath().equalsIgnoreCase("/order/item/update");
		orderManager.updateOrderItem(orderId, recipeId, shouldAdd, qty);
		response.sendRedirect("/FoodQuest/web/views/customer/removeSuccess.jsp");
	}

	private OrderBean handleOrderConfirmation(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, UserBean user, boolean shouldSaveCard, boolean shouldSaveAddress) throws Exception {
		int orderId = Integer.parseInt(request.getParameter("oid"));

		LocationBean locationBean = new LocationBean();
		locationBean.setAddressLine(request.getParameter("addressLine"));
		locationBean.setCity(request.getParameter("city"));
		locationBean.setState(request.getParameter("state"));
		locationBean.setCountry(request.getParameter("country"));
		locationBean.setZipcode(request.getParameter("zipCode"));

		OrderBean order = orderManager.placeOrder(orderId, locationBean);
		return order;
	}

	private String getOrderCnfForm() {
		StringBuilder builder = new StringBuilder();
		builder.append(getCommonOpeningHtml());
		builder.append("<form action=\"/BestDeal/order\">");
		builder.append("<label>Enter Confirmation number : </label>");
		builder.append("<input type=\"text\" name=\"orderCnf\" />");
		builder.append("<input type=\"submit\">");
		builder.append("</form>");
		builder.append(getCommonClosingHtml());
		return builder.toString();
	}

	private String getOrderHtml(OrderBean order) {
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		StringBuilder builder = new StringBuilder();
		builder.append("<p>Order total : $" + order.getTotal() + "</p>");
		builder.append("<p>Order Status : " + order.getStatus() + "</p>");

		builder.append("<p>Order Items</p>");
		builder.append("<table cellspacing=15>");
		builder.append("<tr>");
		builder.append("<th>Name</th>");
		builder.append("<th>Unit Price</th>");
		builder.append("<th>Total Quantity</th>");
		builder.append("</tr>");
		for (OrderItem item : order.getItems()) {
			builder.append("<tr>");
			builder.append("<td>" + item.getRecipe().getName() + " </td>");
			builder.append("<td>" + item.getRecipe().getPrice() + "</td>");
			builder.append("<td>" + item.getQuantity() + "</td>");
			builder.append("</tr>");
			builder.append("</table>");
		}
		if (order.getStatus() != OrderStatus.CANCELLED) {
			builder.append("<form action=\"/BestDeal/order/updateStatus\">");
			builder.append("<input type=\"submit\" value=\"Cancel order\">");
			builder.append("<input type=\"hidden\" name=\"orderId\" value=\"" + order.getId() + "\">");
			builder.append("<input type=\"hidden\" name=\"status\" value=\"" + OrderStatus.CANCELLED.name() + "\">");
			builder.append("</form>");
		}
		return builder.toString();
	}

	private String getCartHtml(OrderBean order) {
		StringBuilder builder = new StringBuilder();
		if (order != null) {
			builder.append("<!DOCTYPE html>");
			builder.append("<html>");
			builder.append("<head>");
			builder.append("<link rel=stylesheet href=\"/BestDeal/web/css/custom.css\">");
			builder.append("</head>");
			builder.append("<body>");
			builder.append("<table border=0>");
			System.out.println(order);
			for (OrderItem item : order.getItems()) {
				System.out.println(item);
				builder.append("<form action=\"/BestDeal/order/item/update\" method=\"post\">");
				builder.append("<input name=\"orderId\" type=\"hidden\" value=\"" + order.getId() + "\">");
				builder.append("<input name=\"recipeId\" type=\"hidden\" value=\"" + item.getRecipe().getId() + "\">");
				builder.append("<tr>");
				builder.append("<td style=\"border:0 none;\">Name</td>");
				builder.append("<td style=\"border:0 none;\"><h4>" + item.getRecipe().getName() + "</h4></td>");
				builder.append("</tr>");
				builder.append("<tr>");
				builder.append("<td style=\"border:0 none;\">Unit price</td>");
				builder.append("<td style=\"border:0 none;\">" + item.getRecipe().getPrice() + "</td>");
				builder.append("</tr>");
				builder.append("<tr>");
				builder.append("<td style=\"border:0 none;\">Total product price</td>");
				builder.append("<td style=\"border:0 none;\">" + item.getPrice()+ "</td>");
				builder.append("</tr>");
				builder.append(" <tr>");
				builder.append("<td style=\"border:0 none;\">Quantity</td>");
				builder.append("<td style=\"border:0 none;\">");
				builder.append(
						"<input name=\"qty\" type=\"number\" value=\"" + item.getQuantity() + "\" min=1 max=10>");
				builder.append("</td>");
				builder.append("</tr>");
				builder.append("<tr>");
				builder.append("<td>Action</td>");
				builder.append("<td>");
				builder.append("<button type=\"submit\">Update</button><br>");
				builder.append("<br>");
				builder.append("<button type=\"submit\" formaction=\"/BestDeal/order/item/remove\">Remove</button>");
				builder.append("</td>");
				builder.append("<tr>");
				builder.append("<td style=\"border:0 none;\"></td>");
				builder.append(" </tr>");
				builder.append("</form>");
			}
			builder.append("<tr>");
			builder.append("<td style=\"border:0 none;\">Total</td>");
			builder.append("<td style=\"border:0 none;\">$" + order.getTotal() + "</td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td style=\"border:0 none;\">Checkout To Place Order</td>");
			builder.append("<td style=\"border:0 none;\">");
			builder.append("<a href=\"/BestDeal/order/confirm?oid=" + order.getId() + "\">");
			builder.append("<h4>Checkout</h4>");
			builder.append("</a>");
			builder.append(" </td>");
			builder.append("</tr>");
			builder.append("</table>");
			builder.append("</body>");
			builder.append("</html>");
		} else {
			builder.append("<h1>No orders placed</h1>");
		}
		return builder.toString();
	}

	private String getOrderCnfHtml(OrderBean order) {
		StringBuilder builder = new StringBuilder();
		builder.append(getCommonOpeningHtml());
		builder.append("<form action=\"/BestDeal/order/pay\" method=\"post\">");
		builder.append("<table>");
		builder.append("<tr>");
		builder.append("<td><label><h3>Total price</h3></label></td>");
		builder.append("<td><h3>:</h3></td>");
		builder.append("<td><h3>" + order.getTotal() + "</h3></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>Address Line </label></td>");
		builder.append("<td>:</td>");
		builder.append("<td><input name=\"addressLine\" type=\"text\" required/></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>City</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td><input name=\"city\" type=\"text\" required/></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>State</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td><input name=\"state\" type=\"text\" required/></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>Country</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td><input name=\"country\" type=\"text\" required/></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label class=\"textlabel\" for=\"tagsInput\">Zipcode</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td><input name=\"zipCode\" type=\"text\" required/></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>Credit Card Number</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td>");
		builder.append("<input name=\"cardNum\" type=\"text\" required/>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>CVC Number</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td>");
		builder.append("<input name=\"cvcNum\" type=\"text\" required/>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>Expiry Month</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td>");
		builder.append("<input name=\"expMonth\" type=\"number\" min = 1 max = 12 required/>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>Expiry Year</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td>");
		builder.append("<input name=\"expYear\" type=\"text\" required/>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>Card Type</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td>");
		builder.append("<select name=\"cardType\">");
		builder.append("<option value=\"MasterCard\">MasterCard</option>");
		builder.append("<option value=\"Visa\">Visa</option>");
		builder.append("<option value=\"American Express\">American Express</option>");
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td><label>Select options</label></td>");
		builder.append("<td>:</td>");
		builder.append("<td>");
		builder.append(
				"<input type=\"checkbox\" name=\"saveOptions\" value=\"saveCard\">Save Card for future transaction<br>");
		builder.append("<input type=\"checkbox\" name=\"saveOptions\" value=\"saveAddress\" checked>Save address<br>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td></td>");
		builder.append("<td>");
		builder.append("<input type=\"submit\" value=\"PROCEED\"/>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("<input type=\"hidden\" value=\"" + order.getId() + "\" name=\"oid\"/>");
		builder.append("</form>  ");
		builder.append(getCommonClosingHtml());
		return builder.toString();
	}

	private String getEditOrderHtml() throws AppException {
		StringBuilder builder = new StringBuilder();
		builder.append(getCommonOpeningHtml());
		for (OrderBean order : orderManager.findAll(null)) {
			builder.append("<form action=\"/BestDeal/order/updateStatus\">");
			builder.append("   <input type=\"hidden\" value=\"" + order.getId() + "\" name=\"orderId\">");
			builder.append("   <table cellspacing=\"10\">");
			builder.append("      <tr>");
			builder.append("         <td> Order Id </td>");
			builder.append("         <td> : </td>");
			builder.append("         <td>" + order.getId() + "</td>");
			builder.append("      </tr>");
			builder.append("      <tr>");
			builder.append("         <td> Status </td>");
			builder.append("         <td> : </td>");
			builder.append("         <td>" + order.getStatus().name() + "</td>");
			builder.append("      </tr>");
			builder.append("      <tr>");
			builder.append("         <td>Delivery address</td>");
			builder.append("         <td> : </td>");
			builder.append("         <td>" + order.getDeliveryAddress().getAddressLine() + ","
					+ order.getDeliveryAddress().getZipcode() + "</td>");
			builder.append("      </tr>");
			builder.append("      <tr>");
			builder.append("         <td> Total Price </td>");
			builder.append("         <td> : </td>");
			builder.append("         <td>" + order.getTotal() + "</td>");
			builder.append("      </tr>");
			builder.append("      <tr>");
			builder.append("         <td> Change Status </td>");
			builder.append("         <td> : </td>");
			builder.append("         <td>");
			builder.append("            <select name=\"status\">");
			builder.append("               <option value=\"none\">None</option>");
			builder.append("               <option value=\"DELIVERED\">DELIVERED</option>");
			builder.append("               <option value=\"CANCELLED\">CANCELLED</option>");
			builder.append("            </select>");
			builder.append("         </td>");
			builder.append("      </tr>");
			builder.append("      <tr>");
			builder.append("         <td> Action </td>");
			builder.append("         <td> : </td>");
			builder.append("         <td>");
			builder.append("            <input type=\"submit\" value=\"Update\">");
			builder.append(
					"            <input formaction=\"/BestDeal/order/delete\" type=\"submit\" value=\"Delete Permanently\">");
			builder.append("         </td>");
			builder.append("      </tr>");
			builder.append("   </table>");
			builder.append("   <br>");
			builder.append("   <hr>");
			builder.append("</form>");
		}
		builder.append(getCommonClosingHtml());
		return builder.toString();
	}

	private String getCommonOpeningHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>");
		builder.append("<head>");
		builder.append("<link rel=\"stylesheet\" href=\"/BestDeal/web/css/style.css\">");
		builder.append("</head>");
		builder.append("<body style=\"background-color: #79a0e0\">");
		return builder.toString();
	}

	private String getCommonClosingHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append("</body>");
		builder.append("</html>");
		return builder.toString();
	}
}
