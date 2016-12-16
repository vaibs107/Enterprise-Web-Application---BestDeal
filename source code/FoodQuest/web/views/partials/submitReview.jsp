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
<%
	session.setAttribute("recipe_id", request.getParameter("recipe_id"));
%>

	<!-- Navigation -->
	<%@include file="/web/views/customer/outheader.jsp"%>

	<!-- Page Content -->
	<div class="container">

		<div class="row">

			<div class="col-sm-3">
				<%@include file="/web/views/customer/navbar.jsp"%>

				<div class="col-md-9">
					<form>
					<div class="form-group col-sm-8">
					    <label for="username">User Name</label>
					    <input type="text" class="form-control" name="username" placeholder="Enter User Name" required>
					</div>
			  		<div class="form-group col-sm-8">
					    <label for="zipcode">Zip code</label>
					    <input type="text" class="form-control" name="userzipcode" placeholder="Enter Zip code" required>
			  		</div>
			  		<div class="form-group col-sm-8">
			    		<label for="age">Age</label>
			    			<input type="text" class="form-control" name="UserAge" placeholder="Enter age" required>
			  		</div>			 
			  		<div class="form-group col-sm-8">
			    		<label for="gender">Gender</label>
			    			<input type="text" class="form-control" name="UserGender" placeholder="Enter Gender" required>
			  		</div>
			  		<div class="form-group col-sm-8">
			    		<label for="occupation">Occupation</label>
			    			<input type="text" class="form-control" name="UserOccupation" placeholder="Enter Occupation" required>
			  		</div>
			  		<div class="form-group col-sm-8">
			    		<label for="rating">Rating</label>
			    			<input type="number" class="form-control" name="ReviewRating" placeholder="Enter Rating" required min="1" max="5">
			  	 <div class="form-group col-sm-8">
      					<label for="comment">Comment:</label>
      					<textarea class="form-control custom-control" rows="5" placeholder="Enter Review" name="ReviewText" style="resize:none;" required></textarea>
    			</div>
				<div class="row" style="text-align:center;">
			  		<div class="form-group col-sm-8">
						<button type="submit" class="btn btn-primary btn-lg" formaction="/FoodQuest/web/views/CreateReview.jsp">Submit</button>
			  		</div>
			  	</div>
			</form>
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

