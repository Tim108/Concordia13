<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="org.postgresql.Driver"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Concordia - Expositie</title>
    
<!-- Bootstrap -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.0.0.js"></script>
	<link href="res/css/bootstrap.min.css" rel="stylesheet">
	<link href="res/css/carousel.css" rel="stylesheet">

<!--  OWL-contents -->
	<link href="res/owl-carousel/owl.carousel.css" rel="stylesheet">
    <link href="res/owl-carousel/owl.theme.css" rel="stylesheet">
    <script src="res/owl-carousel/owl.carousel.js"></script>
    
     <style>
    #owl-demo .item{
        margin: 3px;
    }
    #owl-demo .item img{
        display: block;
        width: 100%;
        height: auto;
    }
    </style>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
   	<![endif]-->

	<meta content="width=320px, initial-scale=1, user-scalable=yes" name="viewport" />
	<style type="text/css">
	.content {
	width: 800px;
	margin: auto;
	}
</style>
</head>
<body>

	<jsp:include page="main.jsp">
		<jsp:param name="currentpage" value="expositie" />
	</jsp:include>

<!-- DB Connection -->
	<%
		String path = "res/dbprops.txt";
			Properties prop = new Properties();
			prop.load(new FileInputStream(getServletContext().getRealPath(path)));
			String user = prop.getProperty("username");
			String pass1 = prop.getProperty("pass");
			String host = prop.getProperty("host");
			String port = prop.getProperty("port");
			String dbname = prop.getProperty("dbname");
			String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
	%>


	<div id="title">
      <div class="container">
        <div class="row">
          <div class="span12">
            <h1>Expositie - Mijn favorieten</h1>
            <h4>"Geile expositie van Klaas-baas"
          </div>
        </div>
      </div>
    </div>

      <div id="demo">
        <div class="container">
          <div class="row">
            <div class="span12">

              <div id="owl-demo" class="owl-carousel owl-theme">
                <div class="item">
<div class="thumbnail">
      <img data-src="holder.js/300x200" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>1</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
	                
                </div>
                <div class="item">
	              <div class="thumbnail">
      <img data-src="holder.js/300x200" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>2</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>  
	                
                </div>
                <div class="item">
	                <div class="thumbnail">
      <img data-src="holder.js/300x200" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>3</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
                </div>
                <div class="item">
	                <div class="thumbnail">
      <img data-src="holder.js/300x200" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>4</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
                </div>
                <div class="item">
	                <div class="thumbnail">
      <img data-src="holder.js/300x200" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>...</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
                </div>
                <div class="item">
	                <div class="thumbnail">
      <img data-src="holder.js/300x200" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>...</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
                </div>
                <div class="item">
	                <div class="thumbnail">
      <img data-src="holder.js/300x200" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>...</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
                </div>
                </div>

            </div>
          </div>
        </div>

    </div>

 <!-- Rest of scripts -->
 
    <script src="res/owl-carousel/assets/bootstrap-collapse.js"></script>
    <script src="res/owl-carousel/assets/bootstrap-transition.js"></script>
    <script src="res/owl-carousel/assets/bootstrap-tab.js"></script>

    <script src="res/owl-carousel/assets/google-code-prettify/prettify.js"></script>
	<script src="res/owl-carousel/assets/application.js"></script>
	
	<script>
    $(document).ready(function() {

      $("#owl-demo").owlCarousel({
    pagination: false,
    navigation: true,
    navigationText: [
      '<h2><span class="glyphicon glyphicon-chevron-left"></span></h2>',
      '<h2><span class="glyphicon glyphicon-chevron-right"></span></h2>'
      ],
 
  });
    });
    </script>
	
</body>
</html>