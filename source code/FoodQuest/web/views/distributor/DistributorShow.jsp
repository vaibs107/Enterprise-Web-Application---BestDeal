<!DOCTYPE html>
<%@page import="com.foodquest.models.RecipeBean.RecipeDistributorStatus"%>
<%@page import="com.foodquest.models.db.DBConnectionManager"%>
<%@page import="com.foodquest.services.impl.DistributorManagerImpl"%>
<%@page import="com.foodquest.services.impl.UserManagerImpl"%>
<%@page import="com.foodquest.models.UserBean"%>
<%@page import="com.foodquest.models.RecipeBean.RecipeStatus"%>
<%@page import="com.foodquest.models.RecipeBean"%>
<%@page import="com.foodquest.repository.impl.RecipeRepositoryImpl"%>
<%@page import="com.foodquest.repository.IRecipeRepository"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <title>Welcome Distributor</title>
	    <!-- Bootstrap Core CSS -->
	    <link href="/FoodQuest/web/css/style.css" rel="stylesheet">
	    <!-- Custom CSS -->
	    <link href="/FoodQuest/web/css/shop-homepage.css" rel="stylesheet">
	    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	    <![endif]-->
	</head>
	<body>
		<!-- Navigation -->
	     <%@include file="/web/views/admin/outheader.jsp"%>
	    <!-- Page Content -->
	    <div class="container">
	        <div class="row">
	            <div class="col-md-3">
	                <p class="lead">Profile</p>
	            	<div id="MainMenu">
	                	<div class="list-group panel">
	                		<a href="/FoodQuest/web/views/DistributorShow.jsp" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Orders</a>
	  					</div>
					</div>
				</div>
				<div class="col-md-9 col-md-12">
	    			<div class="row">
	    				<!-- from here -->
							<table class="table" border="1px;">
							  <thead class="thead-inverse" style="background-color: black; color: white;">
							    <tr>
							      <th>Recipe Name</th>
							      <th>Address</th>
							      <th>Activity</th>
							    </tr>
							  </thead>
							  <tbody>
							<%
							String htmlString = "";
							String pickup_address = "";
							IRecipeRepository recipeManager = RecipeRepositoryImpl.getInstance();
							for(RecipeBean rb : recipeManager.findAll(RecipeStatus.PUBLISHED, null))
							{  
								
								
								UserBean bean = getUserFromSession(request);
								Connection connection  = DBConnectionManager.getInstance().getConnection();
								String query = "select distributor_id from user_ditributor where user_id = "+bean.getId();
								ResultSet rs = connection.createStatement().executeQuery(query);
								rs.next();
								int id = rs.getInt(1);
								
								 if(rb.getDistributor() != null && rb.getStatus().equals(RecipeStatus.PUBLISHED) && rb.getDistributor().getId() == (id))
								{	
									pickup_address += rb.getOwner().getLocation().getAddressLine() + ", " + 
											  rb.getOwner().getLocation().getCity() + ", " +
											  rb.getOwner().getLocation().getState() + ", " + 
											  rb.getOwner().getLocation().getCountry() + " - " +
											  rb.getOwner().getLocation().getZipcode();
									htmlString += "<td>" + rb.getName() + "</td>" + 
											"<td>" + pickup_address + "</td>" +
											"<td><form method = 'post' action = '/FoodQuest/distributor/update'>" +
											"<input type = 'submit' value = 'Change Status'/>" +
											"<input type = 'hidden' name = 'recipe_id' value = '" + rb.getId() + "'/></form></td>" +
											"</tr>";
								}
							}
							
							out.println(htmlString);
							%>
	    					</tbody>
						</table>
					</div>
				</div>
				<div class="container" style="text-align: center; color: white; background-color: black; width: 100%;">  
	       	    	<div class="row" style="padding: 1em;">
	           			<div class="col-lg-12">
	                    	<p>Copyright &copy; FoodQuest Inc</p>
	                	</div>
	            	</div>
	    		</div>
	    	</div>
	    </div>
	</body>
</html>
<%!private UserBean getUserFromSession(HttpServletRequest request) {
		UserBean user = null;
		try {
			String email = request.getSession().getAttribute("user_email").toString();

			user = UserManagerImpl.getInstance().findByEmail(email);
		} catch (Exception e) {

		}
		return user;
	}%>