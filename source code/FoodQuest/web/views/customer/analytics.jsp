<!DOCTYPE html>
<%@page import="com.foodquest.repository.impl.RecipeAnalyticsRepositoryImpl"%>
<%@page import="com.foodquest.repository.IRecipeAnalyticsRepository"%>
<%@page import="com.foodquest.models.RecipeBean"%>
<%@page import="java.util.List"%>
<%@page import="com.foodquest.models.UserBean"%>
<%@page import="com.foodquest.models.OrderItem"%>
<%@page import="com.foodquest.models.OrderBean"%>
<%@page import="com.foodquest.services.impl.UserManagerImpl"%>
<%@page import="com.foodquest.services.impl.OrderManagerImpl"%>
<%@page import="com.foodquest.services.IUserManager"%>
<%@page import="com.foodquest.services.IOrderManager"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome User</title>

<%@include file="/web/views/partials/commonImport.jsp"%>
<%!IOrderManager orderManager;
	IUserManager userManager;
	IRecipeAnalyticsRepository analyticsService;
	public void jspInit() {
		try {
			orderManager = OrderManagerImpl.getInstance();
			userManager = UserManagerImpl.getInstance();
			analyticsService = RecipeAnalyticsRepositoryImpl.getInstance();
		} catch (Exception e) {

		}
	}%>
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
					<h3>Top Rated Products</h3>
						<%
							List<RecipeBean> recipes = analyticsService.findTopRatedRecipes(5);
							for (RecipeBean b : recipes) {
						%>
						<div class="col-sm-4 col-lg-4 col-md-4">
							<div class="thumbnail">
								<img style="width:240px; height:120px;" src="<%=b.getImage().getSecureUrl()%>">
								<div class="caption">
									<h4 class="pull-right">$24.99</h4>
									<h4>
										<%=b.getName()%>
									</h4>
									<div class="wrapper" style="text-align: center";>
										<form action="/FoodQuest/web/views/customer/cart.jsp">
											<button type="submit" class="btn btn-primary">Add to
												Cart</button>
											<input type="hidden" value="<%=b.getId()%>" name="recipe_id">
										</form>
									</div>
									<div class="wrapper" style="text-align: center";>
										<form action="">
										<input type="hidden" value="<%=b.getId()%>" name="recipe_id">
										<button type="submit" class="btn btn-success"
											formaction="/FoodQuest/web/views/partials/submitReview.jsp">Write Review</button>
											
										<button type="submit" class="btn btn-warning"
											formaction="/FoodQuest/web/views/ViewReview.jsp">Read Review</button>
										</form>
									</div>
									<input type="hidden" value="<%=b.getId()%>" name="recipe_id">
								</div>
							</div>
						</div>

						<%
							}
						%>


					</div>
					
					<div class="row">
					<h3>Top Reviewed Products</h3>
						<%
							List<RecipeBean> recipesList = analyticsService.findTopReviewedRecipes(5);
							for (RecipeBean b : recipesList) {
						%>
						<div class="col-sm-4 col-lg-4 col-md-4">
							<div class="thumbnail">
								<img style="width:240px; height:120px;" src="<%=b.getImage().getSecureUrl()%>">
								<div class="caption">
									<h4 class="pull-right">$24.99</h4>
									<h4>
										<%=b.getName()%>
									</h4>
									<div class="wrapper" style="text-align: center";>
										<form action="/FoodQuest/web/views/customer/cart.jsp">
											<button type="submit" class="btn btn-primary">Add to
												Cart</button>
											<input type="hidden" value="<%=b.getId()%>" name="recipe_id">
										</form>
									</div>
									<div class="wrapper" style="text-align: center";>
										<form action="">
										<input type="hidden" value="<%=b.getId()%>" name="recipe_id">
										<button type="submit" class="btn btn-success"
											formaction="/FoodQuest/web/views/partials/submitReview.jsp">Write Review</button>
											
										<button type="submit" class="btn btn-warning"
											formaction="/FoodQuest/web/views/ViewReview.jsp">Read Review</button>
										</form>
									</div>
									<input type="hidden" value="<%=b.getId()%>" name="recipe_id">
								</div>
							</div>
						</div>

						<%
							}
						%>


					</div>
					
					<div class="row">
					<h3>Top Zipcodes </h3>
						<%
							List<String> zipcodes = analyticsService.findTopZipcodesBasedOnRecipeSale(5);
							for (String b : zipcodes) {
						%>
						<div class="col-sm-4 col-lg-4 col-md-4">
							<h4><%=b %></h4>
						</div>

						<%
							}
						%>


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

