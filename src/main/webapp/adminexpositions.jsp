<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%if(request.getSession().getAttribute("Logged") == null) { %> <meta http-equiv="refresh" content="0;/concordia/login.jsp"> <% 
	} else if((Boolean)request.getSession().getAttribute("isAdmin") == false){%> <meta http-equiv="refresh" content="0;/concordia"> <% } %>
	<title>Concordia</title>
	<!-- Bootstrap -->
	<link href="res/css/bootstrap.min.css" rel="stylesheet">
	<link href="res/css/carousel.css" rel="stylesheet">
	<link rel="icon" type="image/x-icon" href="favicon.ico"/>

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
	<jsp:include page="main.jsp"/>
	<center>
		<%
		List<String> userNames = (List<String>)request.getAttribute("Names");
		List<Integer> userIDs = (List<Integer>)request.getAttribute("Users");
		List<Integer> artIDs = (List<Integer>)request.getAttribute("Arts");
		List<Integer> userIDs2 = (List<Integer>)request.getAttribute("Users2");
		request.getSession().setAttribute("Arts", artIDs);
		%>
		<form action="/concordia/adminart.jsp" method="post">
		<select name="customers">
			<option value=none>Klanten met een extern werk</option>
			<% for(int i=0; i<userNames.size(); i++) { %>
			<option value="<%= userIDs2.get(i) %>"><%= userNames.get(i) %></option>
			<% } %>
		</select><br><br>
		<input type="submit" class="btn btn-default" value="Ga" />
		</form>
	</center>
</body>
</html>