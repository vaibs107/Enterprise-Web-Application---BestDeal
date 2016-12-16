<!DOCTYPE html>
<%@page import="com.foodquest.models.UserBean.Gender"%>
<%@page import="com.foodquest.models.RecipeBean"%>
<%@page import="java.util.List"%>
<%@page import="com.foodquest.models.UserBean"%>
<%@page import="com.foodquest.models.OrderItem"%>
<%@page import="com.foodquest.models.OrderBean"%>
<%@page import="com.foodquest.services.impl.UserManagerImpl"%>
<%@page import="com.foodquest.services.impl.OrderManagerImpl"%>
<%@page import="com.foodquest.services.IUserManager"%>
<%@page import="com.foodquest.services.IOrderManager"%>
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
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome User</title>

<%@include file="/web/views/partials/commonImport.jsp"%>
</head>
<%UserBean user = getUserFromSession(request);%>
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
						<div class="container">
							<form action="/FoodQuest/user/customer/update" method="post">
								<div class="row">
									<div class="form-group col-sm-8">
										<label for="fname">First Name</label> <input type="text"
											class="form-control" name="fname"
											placeholder="Enter First Name" value="<%=user.getFirstName()%>">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-8">
										<label for="lname">Last Name</label> <input type="text"
											class="form-control" name="lname"
											placeholder="Enter Last Name" value="<%=user.getLastName()%>">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-8">
										<label for="exampleInputEmail1">Email address</label> <input
											type="email" class="form-control" name="email"
											placeholder="Email" value="<%=user.getEmail()%>">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-8">
										<label for="password">Password</label> <input type="password"
											class="form-control" name="password"
											placeholder="Enter Password">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-8">
										<label for="fname">Address</label> <input type="text"
											class="form-control" name="addressLine"
											placeholder="Enter Street Address" value="<%=user.getLocation().getAddressLine()%>">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-3">
										<label for="fname">Zip code</label> <input type="text"
											class="form-control" name="zipcode"
											placeholder="Enter Zip Code" value="<%=user.getLocation().getZipcode()%>">
									</div>
									<div class="form-group col-sm-2">
										<label for="fname">City</label> <input type="text"
											class="form-control" name="city"
											placeholder="Enter City Name" value="<%=user.getLocation().getCity()%>">
									</div>
									<div class="form-group col-sm-3">
										<label for="fname">State</label> <input type="text"
											class="form-control" name="state"
											placeholder="Enter State Name" value="<%=user.getLocation().getState()%>">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-sm-8">
										<label for="phnum">Phone Number</label> <input type="text"
											class="form-control" name="phone"
											placeholder="Enter Phone Number" value="<%=user.getPhone()%>">
									</div>
								</div>

								<div class="row" style="text-align: center;">
									<div class="form-group">
										<button type="submit" class="btn btn-primary btn-lg">Update</button>
									</div>
								</div>
								<input type="hidden"
									value="<%=getUserFromSession(request).getId()%>" name="userId">
							</form>
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
<% %>
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