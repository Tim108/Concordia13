<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%if(request.getSession().getAttribute("Logged") == null) { %> <meta http-equiv="refresh" content="0;<%=request.getContextPath()%>/login.jsp"> <% 
	} else if((Boolean)request.getSession().getAttribute("isAdmin") == false){%> <meta http-equiv="refresh" content="0;<%=request.getContextPath()%>"> <% } %>
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
	<jsp:param name="currentpage" value="admin" />
	</jsp:include>
	<center>
	<h3>Concordia Controlepaneel</h3>
	<input type="submit" id="addWork" value="Voeg een werk toe" onclick="window.location='<%=request.getContextPath()%>/addart.jsp';" class="btn btn-default"/><br><br>
	<input type="submit" id="customerInfo" value="Vraag gegevens van een klant op" onclick="window.location='<%=request.getContextPath()%>/klantservlet';" class="btn btn-default"/><br><br>
	<input type="submit" value="Bekijk alle exposities met een extern werk" onclick="window.location='<%=request.getContextPath()%>/adminExpositie';" class="btn btn-default"/><br><br>
	<input type="submit" id="siteInfo" value="Bekijk gegevens van de site" onclick="window.location='<%=request.getContextPath()%>/stats';" class="btn btn-default"/>
	</center>
</body>
</html>