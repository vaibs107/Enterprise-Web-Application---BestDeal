<%@page import="com.foodquest.models.UserBean"%>
<%@page import="com.foodquest.services.impl.UserManagerImpl"%>
<%@page import="com.foodquest.services.IUserManager"%>
<%@page import="com.foodquest.models.RecipeReviewBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page import="java.io.*" %>
<%@ page import="com.foodquest.repository.IRecipeReviewRepository"%>
<%@ page import="com.foodquest.repository.impl.RecipeReviewRepositoryImpl"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.ParseException"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date" %>

	<%			
		try{
			System.out.println("\nin createreview, inside try");
			IUserManager userManager = UserManagerImpl.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//Date parsed = format.parse(date);
			int recipeID = Integer.parseInt(session.getAttribute("recipe_id").toString());
			System.out.println("\nin create review, recipeID = "+recipeID);
			UserBean bean = userManager.findByEmail(session.getAttribute("user_email").toString());
			System.out.println("\nin create review, user email = " + session.getAttribute("user_email").toString());
			System.out.println("\nin create review, bean = "+bean);
			int userID= bean.getId();
				//session.getAttribute("userid").toString();
			String sellerName = request.getParameter("username");
			String zipcode = request.getParameter("userzipcode");
			int userAge = Integer.parseInt(request.getParameter("UserAge"));
			String userGender = request.getParameter("UserGender");
			String userOccu = request.getParameter("UserOccupation");
			int reviewRating = Integer.parseInt(request.getParameter("ReviewRating"));	
			Date reviewDate = new Date();
			String reviewText = request.getParameter("ReviewText");
			
			
			
			RecipeReviewBean recipeReview = new RecipeReviewBean();
			recipeReview.setRecipeId(recipeID);
			recipeReview.setUserId(userID);
			recipeReview.setUserName(sellerName);
			recipeReview.setZipCode(zipcode);
			recipeReview.setUserAge(userAge);
			recipeReview.setUserGender(userGender);
			recipeReview.setUserOcuupation(userOccu);
			recipeReview.setRating(reviewRating);
			recipeReview.setReviewdate(reviewDate);
			recipeReview.setComment(reviewText);
			System.out.println(recipeReview);
			IRecipeReviewRepository reviewAdd = RecipeReviewRepositoryImpl.getInstance();
			reviewAdd.create(recipeReview);
			System.out.println(recipeReview);
			
%>
			<html>
				<head>
				</head>
				<body>
				<hr>
				<h2 align='center'>YOUR REVIEW HAS BEEN ADDED</h2>
				<div style='text-align:center'><a href='/FoodQuest/web/views/customer/CustomerPage.jsp'>HOME</a></div>
				<body>
				</html>
		

<%	
		} 

		catch (Exception e) {
			e.printStackTrace();
		}
	%>
</body>
</html>
