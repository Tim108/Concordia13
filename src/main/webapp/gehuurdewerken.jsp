<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*, rest.User"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="org.postgresql.Driver"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%if(request.getSession().getAttribute("Logged") == null) { response.sendRedirect(request.getContextPath() + "/loginpage"); } %>
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
		<jsp:param name="currentpage" value="rented" />
	</jsp:include>
	
	<%
	Map<Integer,List<Object>> rents = (Map<Integer,List<Object>>)request.getSession().getAttribute("Rents"); //( ID, <StartingDate, EndingDate, Deliver>)
	String path = "res/dbprops.txt";
	Properties prop = new Properties();
	prop.load(new FileInputStream(getServletContext().getRealPath(path)));
	String user = prop.getProperty("username");
	String pass1 = prop.getProperty("pass");
	String host = prop.getProperty("host");
	String port = prop.getProperty("port");
	String dbname = prop.getProperty("dbname");
	String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
	%>
	<sql:setDataSource var="snapshot" driver="org.postgresql.Driver"
		url="<%=url%>" user="<%=user%>" password="<%=pass1%>" />
	<sql:query dataSource="${snapshot}" var="artpieces">
	SELECT art.id, art.name, a.price, art.source FROM Art art, Artpiece a, Rent r 
	WHERE a.id = art.id AND art.id = r.artpiece AND r.customer = <%= request.getSession().getAttribute("Logged") %>
	</sql:query>	
	<center>
	<%
	if(request.getAttribute("Error") != null) {
	%>
	<br><font color="red"><h2>${Error}</h2></font>
	<% } else {%>
	<h2><b>Gehuurde Werken</b></h2>
	<table style="border-collapse:separate; border-spacing:0 10px;">
		<c:forEach var="row" items="${artpieces.rows}">
			<c:set var="id" value="${row.id}" />
			<tr style="background-color:#E8E8E8;">
				<td style="border-radius: 10px 0 0 10px; -moz-border-radius: 10px 0 0 10px; padding-right:10px; padding-left:10px;"><b><c:out value="${row.name}" /></b></td>
				<td style=" padding-right:10px; padding-left:10px;"><c:out value="${row.price}" /></td>
				<td style=" padding-right:10px; padding-left:10px;"><%=rents.get(pageContext.getAttribute("id")).get(1) %></td>
				<td style="padding-right:10px; padding-left:10px; padding-top:10px; padding-bottom:10px;">
					<img src="img/<c:out value="${row.source}" />" alt="<c:out value="${row.source}" />" style="width:200px; max-heigth:100%; border-radius: 10px 10px 10px 10px; -moz-border-radius: 10px 10px 10px 10px;"/>
				</td>
				<td style="border-radius: 0 10px 10px 0; -moz-border-radius: 0 10px 10px 0; padding-top:10px; padding-right:10px; padding-bottom:10px;"></td>
			</tr>
		</c:forEach>
	</table>
	<% } %>
	</center>
</body>
</html>