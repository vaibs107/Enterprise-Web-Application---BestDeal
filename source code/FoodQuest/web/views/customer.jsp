<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title></title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
     <div class="container">
			<form action="/FoodQuest/user/customer/register" method="post">
			<div class="row">
			<div class="form-group col-sm-8">
			    <label for="fname">First Name</label>
			    <input type="text" class="form-control" name="fname" placeholder="Enter First Name">
			  </div>
			  </div>
			  <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="lname">Last Name</label>
			    <input type="text" class="form-control" name="lname" placeholder="Enter Last Name">
			  </div>
			  </div>
			  <div class="row">
			  <div class="form-group col-sm-8">
			  <label for="gender">Gender</label>
			  <div class="radio">
			  <label>
			    <input type="radio" name="gender" value="MALE" checked>
			    Male
			  </label>
			  <label>
			    <input type="radio" name="gender" value="FEMALE">
			    Female
			  </label>
			</div>
			</div>
			</div>
			<div class="row">
			  <div class="form-group col-sm-8">
			    <label for="exampleInputEmail1">Email address</label>
			    <input type="email" class="form-control" name="email" placeholder="Email">
			  </div>			 
			  </div> 	
			  	<div class="row">
			  <div class="form-group col-sm-8">
			    <label for="password">Password</label>
			    <input type="password" class="form-control" name="password" placeholder="Enter Password">
			  </div>
			  </div>
			  <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="fname">Address</label>
			    <input type="text" class="form-control" name="addressLine" placeholder="Enter Street Address ">
			  </div>
			  </div>
			  <div class="row">
				  <div class="form-group col-sm-3">
				    <label for="fname">Zip code</label>
				    <input type="text" class="form-control" name="zipcode" placeholder="Enter Zip Code">
				  </div>
				  <div class="form-group col-sm-2">
				    <label for="fname">City</label>
				    <input type="text" class="form-control" name="city" placeholder="Enter City Name">
				  </div>
				  <div class="form-group col-sm-3">
				    <label for="fname">State</label>
				    <input type="text" class="form-control" name="state" placeholder="Enter State Name">
				  </div>
			  </div>
			  <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="phnum">Phone Number</label>
			    <input type="text" class="form-control" name="phone" placeholder="Enter Phone Number">
			  </div>
			  </div>

			  <div class="row" style="text-align:center;">
			  	<div class="form-group">
				 <button type="submit" class="btn btn-primary btn-lg">Submit</button>
				 <button type="reset" class="btn btn-danger btn-lg">Reset</button>
			  </div>
			  </div>
			  
			</form>
		</div>
	</div>

	<footer class="footer navbar-inverse">
		<small>
			&copy; FoodQuest.Inc
		</small>

	</footer>
</div>


	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript" src ="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</body>
</html>