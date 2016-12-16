
<!DOCTYPE html>
<%@page import="com.foodquest.services.impl.RecipeManagerImpl"%>
<%@page import="com.foodquest.services.IRecipeManager"%>
<html lang="en">
<%@page import="com.foodquest.models.OrderItem"%>
<%@page import="com.foodquest.models.OrderBean"%>
<%@page import="com.foodquest.services.impl.UserManagerImpl"%>
<%@page import="com.foodquest.services.impl.OrderManagerImpl"%>
<%@page import="com.foodquest.services.IUserManager"%>
<%@page import="com.foodquest.services.IOrderManager"%>
<%@page import="com.foodquest.models.UserBean"%>


<title>Welcome User</title>
<%!IOrderManager orderManager;
	IUserManager userManager;
	IRecipeManager rcipeManager;

	public void jspInit() {
		try {
			orderManager = OrderManagerImpl.getInstance();
			userManager = UserManagerImpl.getInstance();
			rcipeManager = RecipeManagerImpl.getInstance();
		} catch (Exception e) {

		}
	}%>
<%@include file="/web/views/partials/commonImport.jsp"%>


<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome User</title>

<%@include file="/web/views/partials/commonImport.jsp"%>

</head>

<body>

	<!-- Navigation -->
	<%@include file="/web/views/customer/outheader.jsp"%>

	<!-- Page Content -->
	<div class="container">

		<div class="row">

			<div class="col-sm-3">
				<%@include file="/web/views/customer/navbar.jsp"%>
				<div class="col-md-9">

					<div class="row">


						<%
							String recipeId = request.getParameter("recipe_id");
							String quantity = request.getParameter("qty");
							System.out.println(recipeId);
							if (recipeId != null) {
								int id = Integer.parseInt(recipeId);
								UserBean user = getUserFromSession(request);
								OrderBean bean = orderManager.findOrderInCartByUserId(user.getId());
								System.out.println(bean);
								if (bean == null) {
									bean = new OrderBean();

									OrderItem item = new OrderItem(rcipeManager.findById(id));
									bean.addItem(rcipeManager.findById(id), 1);
									bean.setCustomer(user);
									orderManager.create(bean);
									System.out.println(bean);
								} else {
									if(quantity == null) {
										bean.addItem(rcipeManager.findById(id), 1);
									} else {
										bean.addItem(rcipeManager.findById(id), Integer.parseInt(quantity));
									}
									
									orderManager.update(bean);
								}
							}

							try {
								OrderBean bean = orderManager.findOrderInCartByUserId(getUserFromSession(request).getId());
								if (bean == null) {
						%>
						<h3>No items in cart</h3>
						<%
							} else {
								session.setAttribute("orderId", bean.getId());
						%>
						
						<div class="container">
							<table id="cart" class="table table-hover table-condensed">
								<thead>
									<tr>
										<th style="width: 50%">Product</th>
										<th style="width: 10%">Price</th>
										<th style="width: 8%">Quantity</th>
										<th style="width: 22%" class="text-center">Subtotal</th>
										<th style="width: 10%"></th>
									</tr>
								</thead>
								<tbody>
									<%
										for (OrderItem item : bean.getItems()) {
									%>
									<form method="post">
										<tr>
											<td data-th="Product">
												<div class="row">
													<div class="col-sm-4 hidden-xs">
														<img width=150px height=150px
															src="<%=item.getRecipe().getImage().getImageUrl()%>"
															alt="..." class="img-responsive" />
													</div>
													<div class="col-sm-4">
														<h4 class="nomargin"><%=item.getRecipe().getName()%></h4>
													</div>
												</div>
											</td>
											<td data-th="Price">$<%=item.getRecipe().getPrice()%></td>
											<td data-th="Quantity"><input name="qty" type="number"
												class="form-control text-center"
												value="<%=item.getQuantity()%>" min="1" max="5"></td>
											<td data-th="Subtotal" class="text-center"><%=item.getPrice()%></td>
											<td class="actions" data-th="">
												<button class="btn btn-info btn-sm"
													formaction="/FoodQuest/order/item/update">Update</button> <br>
												<button class="btn btn-danger btn-sm"
													formaction="/FoodQuest/order/item/remove">Remove</button>
											</td>
										</tr>
										<input type="hidden" name="recipeId"
											value="<%=item.getRecipe().getId()%>"> <input
											type="hidden" name="orderId" value="<%=bean.getId()%>">
									</form>
									<%
										}
									%>
								</tbody>
								<tfoot>
									<tr class="visible-xs">
										<td class="text-center"><strong>Total $<%=bean.getTotal()%></strong></td>
									</tr>
									<tr>
										<td><a href="Menu.jsp" class="btn btn-warning"><i
												class="fa fa-angle-left"></i> Continue Shopping</a></td>
										<td colspan="2" class="hidden-xs"></td>
										<td class="hidden-xs text-center"><strong>Total
												$<%=bean.getTotal()%></strong></td>
										<td><a href="/FoodQuest/web/views/customer/paymentSuccess.jsp" class="btn btn-success btn-block">Checkout
												<i class="fa fa-angle-right"></i>
										</a></td>
									</tr>
								</tfoot>
							</table>
						</div>
						<%
							}
							} catch (Exception e) {

							}
						%>
					</div>
				</div>

			</div>

		</div>
		<!-- /.container -->

		<div class="container"
			style="text-align: center; color: white; background-color: black; width: 100%;">


			<!-- Footer -->

			<div class="row" style="padding: 1em;">
				<div class="col-lg-12">
					<p>Copyright &copy; FoodQuest Inc</p>
				</div>
			</div>

		</div>
</body>

</html>
<%!private UserBean getUserFromSession(HttpServletRequest request) {
		UserBean user = null;
		try {
			String email = request.getSession().getAttribute("user_email").toString();

			user = userManager.findByEmail(email);
		} catch (Exception e) {

		}
		return user;
	}%>
