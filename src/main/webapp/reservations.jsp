<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%if(request.getSession().getAttribute("Logged") == null) { %> <meta http-equiv="refresh" content="0;<%=request.getContextPath()%>/login.jsp"> <% 
	} %>
	<title>Concordia</title>
	<!-- Bootstrap -->
	<link href="res/css/bootstrap.min.css" rel="stylesheet">
	<link href="res/css/carousel.css" rel="stylesheet">
	<link rel="icon" type="image/x-icon" href="favicon.ico"/>
	
	<!-- Fancybox -->
	<script type="text/javascript" src="fancybox/jquery.fancybox.pack.js"></script>
	<link rel="stylesheet" href="fancybox/jquery.fancybox.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="fancybox/jquery.fancybox-buttons.css" type="text/css" media="screen" />
	<script type="text/javascript" src="fancybox/jquery.fancybox-buttons.js"></script>

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
	<jsp:include page="main.jsp" />
	
	<%
	List<String> sources = (List<String>)request.getAttribute("Sources");
	List<java.sql.Date> dates = (List<java.sql.Date>)request.getAttribute("Dates");
	%>
	<center>
	<table>
		<tr><td style="padding-right:10px; padding-bottom:10px;"><b>Reservatie Nr.</b><td style="padding-right:10px; padding-bottom:10px;"><b>Start huur</b><td style="padding-bottom:10px;"><b>Preview</b></td></tr>
		<%
		for(int i=0; i<sources.size(); i++) { %>
			<tr><td style="padding-right:10px; padding-bottom:10px;"><%=i + 1%><td style="padding-right:10px; padding-bottom:10px;"><%= dates.get(i) %><td style="padding-right:10px; padding-bottom:10px;"><img src="img/<%= sources.get(i) %>" alt="<%= sources.get(i) %>" style="width:200px; max-heigth:100%;"/><td style="padding-bottom:10px;">
			<h2><font color="darkred"><button onmouseover="document.getElementById('remove<%=i%>').setAttribute('class','glyphicon glyphicon-remove-circle')" onmouseout="document.getElementById('remove<%=i%>').setAttribute('class','glyphicon glyphicon-remove')" style="padding: 0; border: none; background: none;"><span id="remove<%= i %>" class="glyphicon glyphicon-remove" /></button></font></h2></td></tr>
		<%
		}
		%>
		<tr><td style="padding-right:10px; padding-bottom:10px;">
	</table>
	</center>
</body>
</html>