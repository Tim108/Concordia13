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
	<jsp:include page="main.jsp">
		<jsp:param name="currentpage" value="reservations" />
	</jsp:include>
	
	<%
	List<String> sources = (List<String>)request.getAttribute("Sources");
	List<java.sql.Date> dates = (List<java.sql.Date>)request.getAttribute("Dates");
	List<Integer> ids = (List<Integer>)request.getAttribute("IDs");
	%>
	<center>
	<% if(request.getAttribute("Succes") != null) { %>
	
	<div onclick="this.remove();" style="display:inline-block;padding-left:10px;padding-right:10px;-webkit-border-radius: 10px;-moz-border-radius: 10px;border-radius: 10px;border:1px solid #000000;background-color:#FFCC66;-webkit-box-shadow: #B3B3B3 10px 10px 10px;-moz-box-shadow: #B3B3B3 10px 10px 10px; box-shadow: #B3B3B3 10px 10px 10px;"> ${Succes} </div>
	
	<% } %>
	<table style="border-collapse:separate; border-spacing:0 10px;">
		<tr><td style="padding-right:10px; padding-bottom:10px;"><b>Nummer</b><td style="padding-right:10px; padding-bottom:10px;"><b>Start huur</b><td style="padding-left:10px; padding-bottom:10px;"><b>Preview</b></td></tr>
		<%
		for(int i=0; i<sources.size(); i++) { %>
			<tr style="background-color:#E8E8E8;"><td style="border-radius: 10px 0 0 10px; -moz-border-radius: 10px 0 0 10px; text-align: center;"><%=i + 1%><td style="text-align: center;"><%= dates.get(i) %><td style="padding-right:10px; padding-left:10px; padding-top:10px; padding-bottom:10px;"><img src="img/<%= sources.get(i) %>" alt="<%= sources.get(i) %>" style="width:200px; max-heigth:100%; border-radius: 10px 10px 10px 10px; -moz-border-radius: 10px 10px 10px 10px;"/><td style="border-radius: 0 10px 10px 0; -moz-border-radius: 0 10px 10px 0; padding-top:10px; padding-right:10px; padding-bottom:10px;">
			<h2>
			<form action="<%=request.getContextPath()%>/removeReservation" method="post">
				<input type="hidden" name="id" value="<%=ids.get(i)%>" />
				<font color="darkred"><button onclick="popUp()" onmouseover="document.getElementById('remove<%=i%>').setAttribute('class','glyphicon glyphicon-remove-circle')" onmouseout="document.getElementById('remove<%=i%>').setAttribute('class','glyphicon glyphicon-remove')" style="padding: 0; border: none; background: none;"><span id="remove<%= i %>" class="glyphicon glyphicon-remove" /></button></font></h2></td></tr>
			</form>
			<script>
			function popUp() {
				var r = confirm("Weet je zeker dat je dit werk wilt verwijderen?");
				if(r) form.submit();
			}
			</script>
		<%
		}
		%>
	</table>
	</center>
</body>
</html>