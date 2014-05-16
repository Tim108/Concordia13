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
    	<center><h1>Log in</h1><hr>
    	<form method="POST" action="/concordia/login">
    		<table>
    		<tr><td><td><font color="#E00000">${E-mailError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">E-mailadres:<td style="padding-right:10px; padding-bottom:10px"> <input type="text" name="name" <% if(request.getParameter("name") != null) { %> value= <%= request.getParameter("name") %> <% } %> ></tr>
			<tr><td><td><font color="#E00000">${PasswordError}</font></td>
			<tr><td style="padding-right:10px; padding-bottom:10px">Wachtwoord:<td style="padding-right:10px; padding-bottom:10px"> <input type="text" name="surname" <% if(request.getParameter("surname") != null) { %> value= <%= request.getParameter("surname") %> <% } %> ></tr>
			</table>
			<input type="submit" value="Submit">
		</form></center><hr>
    </div>