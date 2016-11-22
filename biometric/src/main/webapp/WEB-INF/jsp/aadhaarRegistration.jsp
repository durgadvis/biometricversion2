<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/ico/favicon.ico">

    <title>Bio Metric</title>

    <!-- Bootstrap core CSS -->
     <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/formValidation.min.css"/>" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.min.css"/>" rel="stylesheet">


    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <!-- Static navbar -->
    <div class="navbar navbar-default" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/biometric-1">BIOMETRIC SHOPPING</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
                        <li class=""><a href="/biometric-1">Home</a></li>
                        <li class="active"><a href="/biometric-1/registration/aadhaar">Aadhar Registration</a></li>
                        <li class=""><a href="/biometric-1/registration/bank">Bank Registration</a></li>
                        <li class=""><a href="/biometric-1/shop">Shop</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

	
	<div id="aadharwrap">
	    <div class="container">
		    <div class="row">
			    <div class="col-lg-6 col-lg-offset-3">
				    <img src = "http://localhost:8080/biometric-1/resources/img/aadhaar.png" style="width:279px;height:143px;">
				    <h5 style="color:#3b5998;font-size:30px;text-align:center;">Enter Aadhaar Details </h5>
			    </div>
			</div>
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<form:form method="POST" action="/biometric-1/registration/aadhaar" modelAttribute="userDetail" id="aadhaarRegistrationForm" class="form-horizontal">
						<div class="form-group">
							<form:label path="name" class="control-label col-xs-4">Name: </form:label>
							<div class="col-xs-5">
								<form:input path="name" class="form-control" name="name" required="required"/>
							</div>
						</div>

						<div class="form-group">
						<form:label path="phonenumber" class="control-label col-xs-4">Phone Number: </form:label>
							<div class="col-xs-5">
								<form:input path="phonenumber"  class="form-control" name="phoneNumber" required="required" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="emailId" class="control-label col-xs-4">Email Id: </form:label>
							<div class="col-xs-5">
								<form:input path="emailId"  class="form-control" name="emailId" required="required" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="address" class="control-label col-xs-4">Address: </form:label>
							<div class="col-xs-5">
								<form:input path="address"  class="form-control" name="address" required="required" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="age" class="control-label col-xs-4">Age: </form:label>
							<div class="col-xs-5">
								<form:input path="age"  class="form-control" name="age" required="required" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-9 col-xs-offset-0">
								<input type="submit" class="submit_buttom btn btn-mg" name ="scanSubmit" value="Scan and submit"/>
							</div>
						</div>

					</form:form>
					<div id="gif" style="visibility: hidden;">
						<span> Updating aadhar database.. </span>
						<img src="/biometric-1/resources/img/loader.gif"  style="display: block; margin: 0 auto; width: 100px;">
					</div>
				</div>
			</div><! --/row -->
	    </div> <!-- /container -->
	</div><! --/aadharwrap -->

	<section id="works"></section>


	<div id="social">
		<div class="container">
			<div class="row centered">
				<div class="col-lg-2">
					<a href="#"><i class="fa fa-dribbble"></i></a>
				</div>
				<div class="col-lg-2">
					<a href="#"><i class="fa fa-facebook"></i></a>
				</div>
				<div class="col-lg-2">
					<a href="#"><i class="fa fa-twitter"></i></a>
				</div>
				<div class="col-lg-2">
					<a href="#"><i class="fa fa-linkedin"></i></a>
				</div>
				<div class="col-lg-2">
					<a href="#"><i class="fa fa-instagram"></i></a>
				</div>
				<div class="col-lg-2">
					<a href="#"><i class="fa fa-tumblr"></i></a>
				</div>

			</div><! --/row -->
		</div><! --/container -->
	</div><! --/social -->

		<div id="footerwrap">
    		<div class="container">
    			<div class="row centered">
    				<div class="col-lg-4">
    					<p><b>BioMetric SHOPPING</b></p>
    				</div>

    				<div class="col-lg-4">
    					<p>From Nokia Folks</p>
    				</div>
    				<div class="col-lg-4">
    					<p>Nokia.com</p>
    				</div>
    			</div>
    		</div>
    	</div><! --/footerwrap -->



    <!-- Bootstrap core JavaScript
    ================================================== -->
   <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/biometric.home.js"/>"></script>
    <script src="<c:url value="/resources/js/formValidation/formValidation.min.js"/>"></script>
    <script src="<c:url value="/resources/js/formValidation/bootstrap.min.js"/>"></script>
  </body>
</html>
