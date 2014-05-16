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
	<%s.removeAttribute("Logged");%>
    <div class="head">
    	<center><h4>Uitgelogd!</h4>
    	<input type="button" value="Terug" onclick="window.location='/concordia';"></center>
    </div>