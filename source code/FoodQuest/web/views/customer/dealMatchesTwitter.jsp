<!DOCTYPE html>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.foodquest.models.RecipeBean.RecipeStatus"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.foodquest.models.RecipeBean"%>
<%@page import="com.foodquest.services.impl.RecipeManagerImpl"%>
<%@page import="com.foodquest.services.IRecipeManager"%>
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
<%!IRecipeManager recipeManager;

	public void jspInit() {
		try {
			recipeManager = RecipeManagerImpl.getInstance();
		} catch (Exception e) {

		}
}%>

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
					<%
try {
	//List to hold matched recipes
	List<RecipeBean> matchedProducts = new ArrayList<>();
	HashMap<RecipeBean, String> recipesToTweetMap = new HashMap<>();
	// Read recipes
	List<RecipeStatus> filterStatuses = new ArrayList<>();
	filterStatuses.add(RecipeStatus.COMPLETED);
	filterStatuses.add(RecipeStatus.PUBLISHED);
	List<RecipeBean> recipes = recipeManager.findAll(filterStatuses, null);
	
	
	
	BufferedReader reader = new BufferedReader(
			new FileReader(new File(request.getServletContext().getRealPath("/") + "DealMatches.txt")));
	String line = "";
	String tweets="";
	while ((line = reader.readLine()) != null) {
		if (!line.isEmpty()) {
			for(RecipeBean bean : recipes) {
				if(line.indexOf(bean.getName()) > 0)
				{
				matchedProducts.add(bean);
				recipesToTweetMap.put(bean, line);
				}
			}
		}
	}
	%>
	
	
<%	if(recipesToTweetMap.size() == 0)
	{
	tweets="NO tweets Found";
	}%>
	<h1 style=color:Indigo> Find the Deal Matches <h1>
	<h4 style=color:blue><%=tweets%></h4>
	
	<%
	String msg="";
	
	if(recipesToTweetMap.size() == 0)
	{
	msg = "No Products matched";
	}
	//else
//	{
//		msg="The Products are :";
//	}
	%>
	<h3>Tweets</h3>
	<% 
	for(String tweet : recipesToTweetMap.values())
	{	
	%>
			<h2 style=color:blue> <%=tweet%> </h2>
	<%		
	}%>
	<h3>Recipes Matched<h3/>
	<%for(RecipeBean bean : recipesToTweetMap.keySet())
	{	
	%>
			<h2 style=color:blue> <%=bean.getName()%> </h2>
			<img style="width:120px; height:120px;" src = <%=bean.getImage().getImageUrl()%> style="45%">
	<%		
	}
	
	
}
 catch (Exception e) {
	e.printStackTrace();
}
	
%>	
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