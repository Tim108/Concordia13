
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
<link rel="icon" type="image/x-icon" href="favicon.ico" />

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
	<%
		HttpSession s = request.getSession();
System.out.println("account: " + s.getAttribute("account"));
System.out.println("activated: " + s.getAttribute("activated"));
System.out.println("Logged: " + s.getAttribute("Logged"));
		if (s.getAttribute("activated") == null
				&& s.getAttribute("Logged") == null) {
			response.sendRedirect("");
		} else if (s.getAttribute("account") != null
				&& s.getAttribute("account").equals("activated")) {
	%>
	Account is al geactiveerd.
	<%
		} else if (s.getAttribute("activated")!=null && s.getAttribute("activated").equals("wrongCode")) {
	%>
	Onjuiste code, probeer het opnieuw met de juiste code.
	<%
		} else if (s.getAttribute("activated")!=null &&  s.getAttribute("activated").equals("activated")) {
	%>
	Account geactiveerd! U kunt nu abonnmenten afsluiten en uw eigen
	expositie samenstellen!
	<%
	s.setAttribute("account", "activated");
		} else if (s.getAttribute("activated") == null
				&& s.getAttribute("Logged") != null) {
	%>
	<form method="get" action="/concordia/activateAccount">
		<input type="text" name="link">
		<button type="submit">Activeer</button>
	</form>
	<%
		} else {
	%>
	Er is iets misgegaan, probeer het opnieuw.
	<%
		}
		s.removeAttribute("activated");
	%>