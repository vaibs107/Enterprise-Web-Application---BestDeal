<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<%@include file="./partials/commonImport.jsp"%>
</head>
<body>
	<%@include file="/web/views/customer/inheader.jsp"%>
	<%@include file="./partials/commonImport.jsp"%>
	<div class="container custom">
		<h3>Registration</h3>
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Customer</a></li>
					<li><a href="distributor.jsp">Distributor</a></li>
				</ul>
			</div>
		</nav>

		<%@include file="customer.jsp"%>


		<footer class="footer navbar-inverse">
			<small> &copy; </small>

		</footer>
	</div>
</body>
</html>