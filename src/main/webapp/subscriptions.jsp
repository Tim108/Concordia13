<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Concordia | Gegevens</title>
<!-- Bootstrap -->
<link href="res/css/bootstrap.min.css" rel="stylesheet">
<link href="res/css/carousel.css" rel="stylesheet">

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
	<div class="head">
		<center>
			<h1>Uw Abonnementen</h1>
			<hr>
			<table>
				</b>
				<tr>
					<td>ID</td>
					<td>Start datum</td>
					<td>Eind datum</td>
					<td>Premium</td>
				</tr>
				<%
					int identifier = 0;
					String idin = "id";
					String startin = "start";
					String eindin = "eind";
					String premiumin = "premium";
					for (int i = 0; i < Integer.parseInt(""
							+ request.getAttribute("rowcount")); i++) {
						idin = "id" + "" + Integer.toString(identifier);
						startin = "start" + "" + Integer.toString(identifier);
						eindin = "eind" + "" + Integer.toString(identifier);
						premiumin = "premium" + "" + Integer.toString(identifier);
						System.out.println(idin + startin + eindin + premiumin);
						out.println("<tr>");
						out.println("<td>" + request.getAttribute(idin).toString());
						out.println("<td>" + request.getAttribute(startin).toString());
						out.println("<td>" + request.getAttribute(eindin).toString());
						out.println("<td>" + request.getAttribute(premiumin).toString());
						out.println("</tr>");
						identifier++;
					}
				%>
			</table>
	</div>