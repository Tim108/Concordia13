 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="org.postgresql.Driver"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<jsp:include page="main.jsp">
		<jsp:param name="currentpage" value="home" />
	</jsp:include>
	
	<form id="generate" method="GET" action="rest/generateArt/genhtml">
	<table>
	<tr><td>Amount of paintings: </td><td><input type="number" name="amount"></td></tr>
	<tr><td>Alignment: </td><td><select name="align" form="generate"><option value="hor">Horizontaal</option><option name="ver">Verticaal</option></select></td></tr>
	<tr><td colspan="2"><input type="submit" value="Genereer link"></td></tr>
	</table>
	</form>