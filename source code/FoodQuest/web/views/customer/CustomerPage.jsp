<!DOCTYPE html>
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

	public void jspInit() {
		try {
			orderManager = OrderManagerImpl.getInstance();
			userManager = UserManagerImpl.getInstance();
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

					<div class="row"></div>
					<h3>
						Welcome
						<%=getUserFromSession(request).getFirstName()%></h3>
					<img alt=""
						src="<%=getUserFromSession(request).getImage().getSecureUrl()%>">
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

