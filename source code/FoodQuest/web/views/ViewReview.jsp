<%@page import="com.foodquest.models.RecipeReviewBean"%>
<%@page import="com.foodquest.models.RecipeBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="com.foodquest.repository.IRecipeReviewRepository"%>
<%@ page
	import="com.foodquest.repository.impl.RecipeReviewRepositoryImpl"%>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@page import="com.foodquest.models.UserBean"%>
<%@page import="com.foodquest.models.OrderItem"%>
<%@page import="com.foodquest.models.OrderBean"%>
<%@page import="com.foodquest.services.impl.UserManagerImpl"%>
<%@page import="com.foodquest.services.impl.OrderManagerImpl"%>
<%@page import="com.foodquest.services.IUserManager"%>
<%@page import="com.foodquest.services.IOrderManager"%>

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
	System.out.println("\nin view review, first stmt");
	IRecipeReviewRepository iRecipeReviewRepository = RecipeReviewRepositoryImpl.getInstance();
	RecipeReviewBean recipeReview = new RecipeReviewBean();
	List<RecipeReviewBean> reviewList = new ArrayList<RecipeReviewBean>();
	int recipeID = Integer.parseInt(request.getParameter("recipe_id").toString());
	//Integer.parseInt(session.getAttribute("recipeid").toString());
	reviewList = iRecipeReviewRepository.getReviewsByRecipeId(recipeID);
%>
	<!-- Navigation -->
	<%@include file="/web/views/customer/outheader.jsp"%>

	<!-- Page Content -->
	<div class="container">

		<div class="row">

			<div class="col-sm-3">
				<%@include file="/web/views/customer/navbar.jsp"%>
	<table border="1", width="60%" >
	<th>Parameters</th><th>Review Details</th>
	
<%
		for (RecipeReviewBean review : reviewList) {
	%>
		<tr>
		<td>User Name</td>
		<td><%=review.getUserName()%></td>
		</tr>
		<tr>
		
		<td>User Occupation</td>
		<td><%=review.getUserOcuupation()%> </td>
		</tr>
		<tr>
		<td>Review Rating</td>
		<td><%=review.getRating()%></td>
		</tr>
		<tr>
		<td>Review Date</td>
		<td><%=review.getReviewdate()%></td>
		</tr>
		<tr>
		<td>Review Text</td>
		<td><%=review.getComment()%></td>
		</tr>
		
			
<%
						}
					%>
	</div>
			</div>
		</div>
		</table>
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