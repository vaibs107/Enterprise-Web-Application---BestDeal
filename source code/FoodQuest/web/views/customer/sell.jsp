<%@include file="/web/views/partials/commonImport.jsp"%>


<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome User</title>

<%@include file="/web/views/partials/commonImport.jsp"%>

</head>

<body>

	<!-- Navigation -->
	<%@include file="/web/views/customer/outheader.jsp"%>
	<div class="container">
		<form>
			

			<div class="row">
				<div class="form-group col-sm-8">
					<h3 style="text-align: center; color: white">Sell your product
					</h3>
					<br> <label for="product_name">Product Name</label> <input
						type="text" class="form-control" id="product_name"
						placeholder="Enter Product Name">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="product_description">Product Description</label> <input
						type="text" class="form-control" id="product_description"
						placeholder="Enter Product description">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="product_description">Product Cost</label> <input
						type="text" class="form-control" id="product_cost"
						placeholder="Enter Product cost">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<label for="product_description">Product Quantity</label> <input
						type="number" class="form-control" id="product_quantity"
						placeholder="Enter Product quantity">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-8">
					<div class="panel panel-default">
						<div class="panel-heading">
							<strong>Upload Image</strong>
						</div>
						<div class="panel-body">

							<!-- Standar Form -->
							<h4>Select files from your computer</h4>
							<form action="" method="post" enctype="multipart/form-data"
								id="js-upload-form">
								<div class="form-inline">
									<div class="form-group">
										<input type="file" name="files[]" id="js-upload-files"
											multiple>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="margin-left: 20%">
				<div class="form-group">
					<button type="submit" class="btn btn-primary btn-lg">Submit</button>
					<button type="reset" class="btn btn-danger btn-lg">Reset</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
