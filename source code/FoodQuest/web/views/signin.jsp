<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sign In</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<%@include file="/web/views/customer/inheader.jsp"%>
	<%@include file="./partials/commonImport.jsp"%>
	<div class="container">
		<div class="col-lg-7">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">SIGN IN</h3>
				</div>
				<div class="panel-body">
					<div class="card card-container">
						<form class="form-signin" action="/FoodQuest/user/login"
							method="post">
							<span id="reauth-email" class="reauth-email"></span> <input
								type="email" name="email" class="form-control"
								placeholder="Email address" required="yes" autofocus> <br>
							<input type="password" name="password" class="form-control"
								placeholder="Password" required="yes"> <br>
							<!-- <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div> -->
							<button class="btn btn-lg btn-primary btn-block btn-signin"
								type="submit">Sign in</button>
						</form>
						<!-- /form -->
						<a href="#" class="forgot-password"> Forgot the password? </a>
					</div>
				</div>
				<!-- /card-container -->
			</div>
			<!-- /container -->
		</div>
	</div>
	</div>
	<br>
	</div>
	<div class="container">
		<footer class="footer navbar-inverse">
			<h5 style="color: white; text-align: center";>&copy;Foodquest</h5>

		</footer>
	</div>
	</di>
</body>
</html>