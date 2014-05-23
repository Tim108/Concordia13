 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Concordia</title>
	<!-- Bootstrap -->
	<link href="res/css/bootstrap.min.css" rel="stylesheet">
	<link href="res/css/carousel.css" rel="stylesheet">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

	<meta content="width=320px, initial-scale=1, user-scalable=yes"
		name="viewport" />
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
	<style type="text/css">
	.content {
		width: 800px;
		margin: auto;
	}
	</style>
</head>
<body>
	<jsp:include page="main.jsp">
		<jsp:param name="currentpage" value="home" />
	</jsp:include>
	<div class="head">
		<CENTER><H1>Populaire werken</H1></CENTER>
    	<div id="myCarousel" class="carousel slide" data-ride="carousel">
     	 <!-- Indicators -->
      		<ol class="carousel-indicators">
        		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        		<li data-target="#myCarousel" data-slide-to="1"></li>
        		<li data-target="#myCarousel" data-slide-to="2"></li>
     		 </ol>
      	<div class="carousel-inner">
        	<div class="item active">
        	  <img src="img/frontpage/header1.png" style="width:100%;">
        	  <div class="container">
        	    <div class="carousel-caption">
        	      <h1>Maak je eigen online exposities.</h1>
        	      <p></p>
        	      <p><a class="btn btn-lg btn-primary" href="#" role="button">Start</a></p>
        	    </div>
        	  </div>
        	  </div>
        	<div class="item">
        	<div>
        	  <img src="img/frontpage/header2.png" style="width:100%;">
          <div class="container">
            <div class="carousel-caption">
              <h1>1.734 werken die je kunt huren.</h1>
              <p></p>
              <p><a class="btn btn-lg btn-primary" href="collectie.jsp" role="button">Bekijk Collectie</a></p>
            </div>
          </div>
        </div>
        </div>
        <div class="item">
          <img src="img/frontpage/header3.png" style="width:100%;">
          <div class="container">
            <div class="carousel-caption">
              <h1>Slechts 7,50 euro per maand</h1>
              <p></p>
              <p><a class="btn btn-lg btn-primary" href="#" role="button">Abonneer</a></p>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
      </div>
	  <CENTER><h1>Welkom bij Concordia,</h1></CENTER>
	  <CENTER><p>jouw portaal naar betaalbare kunst, begin gelijk met zoeken in 1.734 kunstwerken.</p></Center>
		<CENTER><div style="width:400px;">
	 	  <form class="navbar-form" method="POST" action="/concordia/search">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Zoeken" name="srch-term" id="srch-term">
          <div class="input-group-btn">
            <button class="btn btn-default" style="height:34px;" type="submit"><span class="glyphicon glyphicon-search"></span></button>
          </div>
        </div></CENTER>
      </form>
	 </div>
	</div>
	</div>
