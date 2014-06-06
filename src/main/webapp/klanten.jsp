<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.ResultSet, java.util.List, java.util.ArrayList, java.sql.Connection"%>
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
		<form action="klantservlet" method="post">
			<h4>-
			<% for(int i=0; i<26; i++) { %>
			<button style="padding: 0; border: none; background: none;" type="submit" name="letter" value="<%= "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(i) %>"><%= "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(i) %></button> -
			<% } %>
			</h4>
		</form>
		<br>
		<form class="navbar-form" id="search" method="POST"
			action="klantservlet">
			<div style="width: 400px;">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="zoeken"
						name="srch-term" id="srch-term">
					<div class="input-group-btn">
						<button class="btn btn-default" style="height: 34px;"
							type="submit">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</div>
				</div>
			</div>
		</form>
		<%
		List<String> name = (List<String>)request.getAttribute("name");
		List<String> surname = (List<String>)request.getAttribute("surname");
		List<String> address = (List<String>)request.getAttribute("address");
		List<String> city = (List<String>)request.getAttribute("city");
		List<String> postal = (List<String>)request.getAttribute("postal");
		List<String> tel = (List<String>)request.getAttribute("tel");
		List<Double> credit = (List<Double>)request.getAttribute("credit");
		List<Boolean> newsl = (List<Boolean>)request.getAttribute("newsl");
		List<Integer> subs = (List<Integer>)request.getAttribute("subs");
		List<String> email = (List<String>)request.getAttribute("email");
		if(name != null) {%><hr><% 
			for(int i=0; i<name.size(); i++) {%>
				<b><%=name.get(i) %> <%=surname.get(i)%></b><br>
				E-Mail: <%=email.get(i)%><br>
				Adres: <%=address.get(i)%><br>
				Stad: <%=city.get(i)%><br>
				Postcode: <%=postal.get(i)%><br>
				Telefoon: <%=tel.get(i)%><br>
				Kooptegoed: <%=credit.get(i)%><br>
				Nieuwsbrief: <%=newsl.get(i)%><br>
				Aantal abonnementen: <%=subs.get(i)%><hr>
			<%}
		}%>
	</center>
</body>
</html>