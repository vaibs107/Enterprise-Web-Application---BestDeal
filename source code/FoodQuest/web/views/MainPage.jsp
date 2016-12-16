<!DOCTYPE html>
<html lang="en">
<%@include file="./partials/commonImport.jsp"%>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
>

<style>
.list-group.panel>.list-group-item {
	border-bottom-right-radius: 4px;
	border-bottom-left-radius: 4px;
	background-color: black;
	color: white;
}
</style>

</head>

<body>

	<!-- from here -->
	<%@include file="/web/views/customer/inheader.jsp"%>'

	<!-- till here -->

	<div class="col-md-12 col-md-12">

		<div class="row carousel-holder">

			<div class="col-md-12">
				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel" data-interval="2500">
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>
					<div class="carousel-inner">

						<div class="item active">
							<img class="slide-image"
								src="/FoodQuest/web/images/veg-biryani.jpg" alt="">
						</div>
						<div class="item">
							<img class="slide-image"
								src="/FoodQuest/web/images/chicken-curry.jpg" alt="">
						</div>
						<div class="item">
							<img class="slide-image"
								src="/FoodQuest/web/images/ItalianPizza.jpg" alt="">
						</div>
						<div class="item">
							<img class="slide-image" src="/FoodQuest/web/images/thai.jpg"
								alt="">
						</div>

					</div>
					<a class="left carousel-control" href="#carousel-example-generic"
						data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left"></span>
					</a> <a class="right carousel-control" href="#carousel-example-generic"
						data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right"></span>
					</a>
				</div>
			</div>

		</div>

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
