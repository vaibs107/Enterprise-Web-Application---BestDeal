
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

<%
	System.out.println(session.getAttribute("orderId").toString());
	OrderBean bean = orderManager.findById(Integer.parseInt(session.getAttribute("orderId").toString()));
	orderManager.placeOrder(bean.getId(), null);
%>
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

			<h3>Payment Success</h3>
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
