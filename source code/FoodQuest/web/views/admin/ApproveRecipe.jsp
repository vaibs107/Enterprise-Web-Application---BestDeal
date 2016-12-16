<!DOCTYPE html>
<%@page import="com.foodquest.models.RecipeBean.RecipeStatus"%>
<%@page import="com.foodquest.repository.impl.RecipeRepositoryImpl"%>
<%@page import="com.foodquest.repository.IRecipeRepository"%>
<%@ page import="com.foodquest.models.RecipeBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome Admin</title>



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
						<a href="/FoodQuest/web/views/admin/ApproveRecipe.jsp"
							class="list-group-item list-group-item-success">Recipes</a> <a
							href="/FoodQuest/web/views/admin/DisableUser.jsp"
							class="list-group-item list-group-item-success">Users</a>
					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="row">
					<%!private static IRecipeRepository recipeManager = RecipeRepositoryImpl.getInstance();
	String htmlString = "";%>
					<table class="table" border="1px;">
						<thead class="thead-inverse"
							style="background-color: black; color: white;">
							<tr>
								<th>Recipe Name</th>
								<th>Description</th>
								<th>Cost</th>
								<th>Activity</th>
							</tr>
						</thead>
						<tbody>

							<%
								for (RecipeBean r : recipeManager.findAll(RecipeStatus.PENDING, null)) {
									htmlString += "<td>" + r.getName() + "</td>" + "<td>" + r.getDescription() + "</td>" + "<td>"
											+ r.getPrice() + "</td>"
											+ "<td><form method = 'post' action = '/FoodQuest/admin/update/products'>"
											+ "<input type = 'submit' value = 'Approve Recipe'/>"
											+ "<input type = 'hidden' name = 'recipe_id' value = '" + r.getId() + "'/></form></td>"
											+ "</tr>";
								}
								out.println(htmlString);
							%>
						</tbody>
					</table>
					<%
						
					%>
				</div>
			</div>
		</div>
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





