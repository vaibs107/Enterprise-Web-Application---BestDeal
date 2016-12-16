<!DOCTYPE html>
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
                <a href="/FoodQuest/web/views/admin/ApproveRecipe.jsp" class="list-group-item list-group-item-success">Recipes</a>
    			<a href="/FoodQuest/web/views/admin/DisableUser.jsp" class="list-group-item list-group-item-success">Users</a>
  </div>
</div>
</div>
			<div class="col-md-12">
				<div class="row">
				Welcome Admin
				</div>
			</div>
		</div>
	 <div class="container" style="text-align: center; color: white; background-color: black; width: 100%;">


        <!-- Footer -->
        
            <div class="row" style="padding: 1em;">
                <div class="col-lg-12">
                    <p>Copyright &copy; FoodQuest Inc</p>
                </div>
            </div>

    </div>
    </body>
    </html>



