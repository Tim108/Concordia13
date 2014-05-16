<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Concordia</title>
	<%@include file="main.jsp"%>
    <div class="head">
    	<center><h1>Registreren</h1><hr>
    	<form method="POST" action="/concordia/register">
    		<h4>Contactgegevens</h4>
    		<table>
    		<tr><td><td><font color="#E00000">${NameError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Voornaam:<td style="padding-right:10px; padding-bottom:10px"> <input type="text" name="name" <% if(request.getParameter("name") != null) { %> value= <%= request.getParameter("name") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${SurnameError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Achternaam:<td style="padding-right:10px; padding-bottom:10px"> <input type="text" name="surname" <% if(request.getParameter("surname") != null) { %> value= <%= request.getParameter("surname") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${AddressError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Adres: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="address" <% if(request.getParameter("address") != null) { %> value= <%= request.getParameter("address") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${CityError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Woonplaats: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="city" <% if(request.getParameter("city") != null) { %> value= <%= request.getParameter("city") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${PostalError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Postcode: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="postal" <% if(request.getParameter("postal") != null) { %> value= <%= request.getParameter("postal") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${PhoneError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Telefoon: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="phone" <% if(request.getParameter("phone") != null) { %> value= <%= request.getParameter("phone") %> <% } %> ></tr>
			</table>
			<h4>Inloggegevens</h4>
			<table>
			<tr><td><td><font color="#E00000">${EmailError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">E-Mail: <td style="padding-right:10px; padding-bottom:10px"><input type="text" name="email" <% if(request.getParameter("email") != null) { %> value= <%= request.getParameter("email") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${PassError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Password: <td style="padding-right:10px; padding-bottom:10px"><input type="password" name="pass" <% if(request.getParameter("pass") != null) { %> value= <%= request.getParameter("pass") %> <% } %> ></tr>
			</table><br>
			<input type="checkbox" name="newsletter" checked=true> Ja, ik wil een maandelijkse nieuwsbrief ontvangen.<br>
			<input type="submit" value="Submit">
		</form></center><hr>
    </div>