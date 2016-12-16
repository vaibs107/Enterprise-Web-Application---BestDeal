<!DOCTYPE html>
<html lang="en">
<%@include file="/web/views/partials/commonImport.jsp"%>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Payment page</title>
    <link rel="stylesheet" href="style.css">
    
</head>

<body>

<!-- from here -->
<div class="container">
    <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <li class="active">
      <a class="navbar-brand" href="#">FOOD QUEST</a>
      </li>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="MainPage.jsp">Home <span class="sr-only">(current)</span></a></li>
        
        <li>
                        <form class="navbar-form navbar-left">
                        <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search">
                        <div class="input-group-btn">
                        <button class="btn btn-default" type="submit">
                    <i class="glyphicon glyphicon-search"></i>
          </button>
        </div>
      </div>
    </form>
    </li>
        <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span>Cart</a></li>
      </ul> 
     <form class="navbar-form navbar-right" action="logout.jsp">
       <button type="submit" class="btn btn-primary">Logout</button>
     </form>      
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
     <div class="container">
			<form method="post" action="/FoodQuest/">
			<div class="row">
			<div class="form-group col-sm-8">
			    <label for="fname">Name</label>
			    <input type="text" class="form-control" id="fname" placeholder="Enter Name">
			  </div>
			  </div>
			<div class="row">
			  <div class="form-group col-sm-8">
			    <label for="exampleInputEmail1">Email address</label>
			    <input type="email" class="form-control" id="email" placeholder="Email">
			  </div>			 
			  </div> 	
			  <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="phoneno">Contact number</label>
			    <input type="email" class="form-control" id="email" placeholder="Contact Number">
			  </div>			 
			  </div> 	
			  <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="fname">Delivery Address</label>
			    <input type="text" class="form-control" id="addr1" placeholder="Enter Street Address ">
			  </div>
			  </div>
			  <div class="row">
				  <div class="form-group col-sm-2">
				    <label for="fname">Zip code</label>
				    <input type="text" class="form-control" id="zipcode" placeholder="Enter Zip Code">
				  </div>
				  <div class="form-group col-sm-3">
				    <label for="fname">City</label>
				    <input type="text" class="form-control" id="city" placeholder="Enter City Name">
				  </div>
				  <div class="form-group col-sm-3">
				    <label for="fname">State</label>
				    <input type="text" class="form-control" id="state" placeholder="Enter State Name">
				  </div>
			  </div>
			   <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="cardno">Card number</label>
			    <input type="text" class="form-control" id="cardno" placeholder="Enter Card Number">
			  </div>
			  </div>
			    <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="Expirydate">Expiry date</label>
			    <input type="text" class="form-control" id="edate" placeholder="Enter expiry date">
			  </div>
			  </div>
			  <div class="row">
			  <div class="form-group col-sm-8">
			    <label for="cvv">CVV</label>
			    <input type="text" class="form-control" id="cvv" placeholder="Enter CVV">
			  </div>
			  </div>

			  <div class="row" style="text-align:center;">
			  	<div class="form-group">
				 <button type="submit" class="btn btn-primary btn-lg">Pay</button>
				 <button type="reset" class="btn btn-danger btn-lg">Reset</button>
			  </div>
			  </div>
			  
			</form>
		</div>


	<footer class="footer navbar-inverse">
		<small>
			&copy; FoodQuest.Inc
		</small>

	</footer>
</div>

	</div>
	
</body>
</html>