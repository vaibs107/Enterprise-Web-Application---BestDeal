<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title></title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.css">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript" src ="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.js"></script>
	<script src ="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.2/moment.js"></script> 
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body>
<%@include file="/web/views/customer/inheader.jsp"%>
	<div class="container">
		<h3> Reviews </h3>
	</div>
	<div class="row">
<%@include file="/web/views/customer/navbar.jsp"%>
     	<div class="container">
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
			    			<input type="text" class="form-control" name="ReviewRating" placeholder="Enter Rating" required>
			  	</div>
			  		<div class="form-group col-sm-8">
			    		<label for="reviewdate">Review date</label>
			    			<input type="text" class="form-control" name="ReviewDate" placeholder="Enter Review date" required>
			  		</div>
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
	
		<footer class="footer navbar-inverse">
			<small>
				&copy; 
			</small>
		</footer>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script type="text/javascript" src ="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>