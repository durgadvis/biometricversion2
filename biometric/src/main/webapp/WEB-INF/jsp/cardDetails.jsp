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
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">

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
          <a class="navbar-brand" href="index.html">BIOMETRIC SHOPPING</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li class=""><a href="/biometric-1">Home</a></li>
            <li class=""><a href="/biometric-1/user/newUser">New User</a></li>
            <li class=""><a href="/biometric-1/shopPage">Shop</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

	<div id="headerwrap">
	    <div class="container">
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
				         <h2>Hi ${userDetail.name} please select any of the below card</h2>
                         <c:forEach var="arr" items="${userDetail.listCardDetails}">
                             <table class="table table-bordered textfont">
                                <tr>
                                    <td> Card Number  </td>
                                    <td> ${arr.cardNumber}</td>
                                </tr>
                                <tr>
                                    <td> Name on the card </td>
                                    <td> ${arr.nameOnCard}</td>
                                </tr>
                                <tr>
                                    <td> Expire Date </td>
                                    <td> ${arr.expiryDate}</td>
                                </tr>
                            </table>
						</c:forEach>
				</div>
			</div><! --/row -->
	    </div> <!-- /container -->
	</div><! --/headerwrap -->

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
					<p><b>WEB DESIGNER, DEVELOPER & GAME ADDICT</b></p>
				</div>

				<div class="col-lg-4">
					<p>Living in the amazing London.</p>
				</div>
				<div class="col-lg-4">
					<p>hello@instant.com</p>
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
  </body>
</html>