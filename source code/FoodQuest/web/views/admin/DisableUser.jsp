<!DOCTYPE html>
<%@page import="com.foodquest.models.UserBean.UserType"%>
<%@page import="com.foodquest.models.UserBean.AccountStatus"%>
<%@page import="com.foodquest.services.impl.UserManagerImpl"%>
<%@page import="com.foodquest.services.IUserManager"%>
<%@ page import="com.foodquest.models.UserBean"%>
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
                <a href="/FoodQuest/web/views/admin/ApproveRecipe.jsp" class="list-group-item list-group-item-success">Recipes</a>
    			<a href="/FoodQuest/web/views/admin/AdminPage.jsp" class="list-group-item list-group-item-success">Users</a>
		   </div>
</div>
</div></div>
<div class="container">
    <!-- from here -->
<%!
private static IUserManager userManager = UserManagerImpl.getInstance();
String htmlString = "";
%>
<table class="table" border="1px;">
  <thead class="thead-inverse" style="background-color: black; color: white;">
    <tr>
      <th>First name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Contact #</th>
      <th>Activity</th>
    </tr>
  </thead>
  <tbody>
<%
for(UserBean u : userManager.findAll(AccountStatus.ACTIVE))
{  if(u.getType().equals(UserType.CUSTOMER)){
	htmlString += "<td>" + u.getFirstName() + "</td>" + 
				"<td>" + u.getLastName() + "</td>" +
				"<td>" + u.getEmail() + "</td>" +
				"<td>" + u.getPhone() + "</td>" +
				"<td><form method = 'post' action = '/FoodQuest/admin/delete/customers'>" +
				"<input type = 'submit' value = 'Disable User'/>" +
				"<input type = 'hidden' name = 'user_id' value = '" + u.getId() + "'/></form></td>" +
				"</tr>";
	}
}
out.println(htmlString);
%>
    </tbody>
</table>
</div>
</div>
</div></div>
<div class="container" style="text-align: center; color: white; background-color: black; width: 100%;">  
            <div class="row" style="padding: 1em;">
                <div class="col-lg-12">
                    <p>Copyright &copy; FoodQuest Inc</p>
                </div>
            </div>

    </div>
    </body>
    </html>
    