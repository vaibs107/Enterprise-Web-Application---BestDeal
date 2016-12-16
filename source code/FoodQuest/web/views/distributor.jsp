<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.2/moment.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body>
	<%@include file="/web/views/customer/inheader.jsp"%>
	<div class="container custom">
		<h3>Registration</h3>
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<ul class="nav navbar-nav">
					<li class="active"><a href="registration.jsp">Customer</a></li>
					<li><a href="distributor.jsp">Distributor</a></li>
				</ul>
			</div>
		</nav>
	</div>

	<div class="container">
		<form action="/FoodQuest/user/distributor/register" method="post">
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="fname">First Name</label> <input type="text"
						class="form-control" name="fname" placeholder="Enter First Name">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="lname">Last Name</label> <input type="text"
						class="form-control" name="lname" placeholder="Enter Last Name">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="gender">Gender</label>
					<div class="radio">
						<label> <input type="radio" name="gender" id="gender"
							value="MALE" checked>Male</input>
						</label> <label> <input type="radio" name="gender" id="gender"
							value="FEMALE">Female</input>
						</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="exampleInputEmail1">Email address</label> <input
						type="email" class="form-control" name="email" placeholder="Email">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="password">Password</label> <input type="password"
						class="form-control" name="password" placeholder="Enter Password">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="fname">Company name</label> <input type="text"
						class="form-control" name="distributorName"
						placeholder="Enter Distributor Company name ">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="fname">Website URL</label> <input type="text"
						class="form-control" name="websiteUrl"
						placeholder="Enter Website URL ">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="fname">Organisation number</label> <input type="text"
						class="form-control" name="phone"
						placeholder="Enter Organisation phone">
				</div>
			</div>
			<div class="row">
			<div class="form-group col-sm-8">
				<label>Address</label> <input type="text" class="form-control"
					name="addressLine" placeholder="Enter Street Address ">
			</div>
		</div>
	<div class="row">
		<div class="form-group col-sm-2">
			<label>Zip code</label> <input type="text" class="form-control"
				name="zipcode" placeholder="Enter Zip Code">
		</div>
		<div class="form-group col-sm-2">
			<label for="fname">City</label> <input type="text"
				class="form-control" name="city" placeholder="Enter City Name">
		</div>
		<div class="form-group col-sm-2">
			<label for="fname">State</label> <input type="text"
				class="form-control" name="state" placeholder="Enter State Name">
		</div>
	</div>
	<div class="row" style="text-align: center;">
		<div class="form-group">
			<button type="submit" class="btn btn-primary btn-lg">Submit</button>
			<button type="reset" class="btn btn-danger btn-lg">Reset</button>
		</div>
	</div>
	</form>
	</div>
	<footer class="footer navbar-inverse">
		<small> &copy; </small>
	</footer>
</body>
</html>