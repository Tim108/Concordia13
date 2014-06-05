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
    <div class="head">
    	<center><h1>Registreren</h1><hr>
    	<form method="POST" action="/concordia/register">
    		<table>
    		<tr><td><h4>Contactgegevens</h4></td></tr>
    		<tr><td><td><font color="#E00000">${NameError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">Voornaam:<td style="padding-right:10px; padding-bottom:10px"> <input type="text" name="name" <% if(request.getParameter("name") != null) { %> value= <%= request.getParameter("name") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${SurnameError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">Achternaam:<td style="padding-right:10px; padding-bottom:10px"> <input type="text" name="surname" <% if(request.getParameter("surname") != null) { %> value= <%= request.getParameter("surname") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${AddressError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">Adres: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="address" <% if(request.getParameter("address") != null) { %> value= <%= request.getParameter("address") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${CityError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">Woonplaats: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="city" <% if(request.getParameter("city") != null) { %> value= <%= request.getParameter("city") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${PostalError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">Postcode: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="postal" <% if(request.getParameter("postal") != null) { %> value= <%= request.getParameter("postal") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${PhoneError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">Telefoon: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="phone" <% if(request.getParameter("phone") != null) { %> value= <%= request.getParameter("phone") %> <% } %> ></tr>
			<tr><td><h4>Inloggegevens</h4></td></tr>
			<tr><td><td><font color="#E00000">${EmailError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">E-Mail: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="email" <% if(request.getParameter("email") != null) { %> value= <%= request.getParameter("email") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${PassError}</font></tr>
			<tr><td style="padding-right:10px; padding-bottom:10px">Password: <td style="padding-right:10px; padding-bottom:10px"><input type="password" name="pass" <% if(request.getParameter("pass") != null) { %> value= <%= request.getParameter("pass") %> <% } %> ></tr>
			</table><br>
			<input type="checkbox" name="newsletter" checked=true> Ja, ik wil een maandelijkse nieuwsbrief ontvangen.<br>
			<input type="submit" value="Submit">
		</form></center><hr>
    </div>