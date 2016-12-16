
<!DOCTYPE html>
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

	public void jspInit() {
		try {
			orderManager = OrderManagerImpl.getInstance();
			userManager = UserManagerImpl.getInstance();
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
					<div class="container">
						<h2>SELL YOUR DISH</h2>
						<form method="post" action="/FoodQuest/recipe/_create" enctype="multipart/form-data">
							<div class="row">
								<div class="form-group col-sm-8">
									<br> <label for="product_name">Recipe Name</label> <input
										type="text" class="form-control" name="recipeName"
										placeholder="Enter Product Name" required>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-8">
									<label for="product_description">Recipe Description</label> <input
										type="text" class="form-control" name="recipeDesc"
										placeholder="Enter Recipe description" required>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-8">
									<label for="product_description">Recipe Cost</label> <input
										type="text" class="form-control" name="recipeCost"
										placeholder="Recipe cost" required>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-8">
									<label for="product_description">Recipe Quantity</label> <input
										type="number" class="form-control" name="recipeQuantity"
										placeholder="Enter Recipe quantity" min="1" max="50" required>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-8">
									<label for="product_description">Recipe Category</label> <select
										name="recipeCategoryName" required>
										<option value="VEG">Vegetarian</option>
										<option value="NON VEG">Non vegetarian</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-8">
									<label for="product_description">Cuisine Type</label> <select
										name="recipeCuisineName" required>
										<option value="Mexican">Mexican</option>
										<option value="Italian">Non Italian</option>
										<option value="Indian">Indian</option>
										<option value="Thai">Thai</option>
										<option value="Chinese">Chinese</option>
										<option value="Japanese">Japanese</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-8">
									<br> <label for="product_name">Date of serving</label> <input
										type="date" class="form-control" name="dateOfServing" required>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-8">
									<div class="panel panel-default">
										<div class="panel-heading">
											<strong>Upload Image</strong>
										</div>
										<div class="panel-body">

											<!-- Standar Form -->
											<h4>Select files from your computer</h4>
											<div class="form-inline">
												<div class="form-group">
													<input type="file" name="file" required>
												</div>
											</div>
										</div>
									</div>
									<div class="row" style="margin-left: 20%">
										<div class="form-group">
											<button type="submit" class="btn btn-primary btn-lg">Submit</button>
											<button type="reset" class="btn btn-danger btn-lg">Reset</button>
										</div>
									</div>
						</form>

					</div>

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
