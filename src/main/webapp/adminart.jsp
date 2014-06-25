<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
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
	<%if(request.getSession().getAttribute("Logged") == null) { %> <meta http-equiv="refresh" content="0;/concordia/login.jsp"> <% 
	} else if((Boolean)request.getSession().getAttribute("isAdmin") == false){%> <meta http-equiv="refresh" content="0;/concordia"> <% } %>
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
	
	<%
		String path = "res/dbprops.txt";
		Properties prop = new Properties();
		prop.load(new FileInputStream(getServletContext().getRealPath(path)));
		String user = prop.getProperty("username");
		String pass1 = prop.getProperty("pass");
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String dbname = prop.getProperty("dbname");
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		
		String temp = request.getParameter("customers");
		if(temp.equals("none")) {
			response.sendRedirect("adminExpositie");
			return;
		}
		List<Integer> artIDs = (List<Integer>)request.getSession().getAttribute("Arts");
		request.getSession().removeAttribute("Arts");
	%>

	<sql:setDataSource var="snapshot" driver="org.postgresql.Driver"
		url="<%=url%>" user="<%=user%>" password="<%=pass1%>" />

	<sql:query dataSource="${snapshot}" var="artpiece">
		SELECT a.id, a.name, a.source, b.website, c.collection FROM art a, external_picture b, uploaded u, belongs_to c
		WHERE a.id=b.id
		AND u.external_picture=b.id
		AND u.customer = <%= Integer.parseInt(temp) %>
		AND c.art = b.id
		AND (
		<% for(int i=0; i<artIDs.size(); i++) { %>
		a.id=<%= artIDs.get(i) %> OR
		<% } %>
		a.id=<%= artIDs.get(0) %>);
	</sql:query>
	
	<center>
		<c:forEach var="row" items="${artpiece.rows}">
			<div>
				<div class="item">
					<div class="thumbnail">
						<div style="height: 250px;">
								<a class="plaatje" rel="gallery" href="${row.source}"
								caption='<h5>${row.name}</h5>${row.website}'>
								<img src="${row.source}" alt="${row.source}"
								style="max-height: 100%; max-width: 100%;" />
							</a>
						</div>
						<div class="caption">
							<h3>
								<c:out value="${row.name}" />
							</h3>
							<div>
								<p>
									Website:
									<c:out value="${row.website}" />
								</p>
								<form action="verwijderUitExpositie" method="post" onsubmit="return popUp()">
									<input type="hidden" name="id" value=" ${row.id}"> 
									<input type="hidden" name="external" value="true">
									<input type="hidden" name="coll" value="${row.collection}">
									<input type="submit" value="Verwijder uit expositie" class="btn btn-success">
								</form>
								<script>
									function popUp() {
										var r = confirm("Weet je zeker dat je dit werk wilt verwijderen?\nAls dit het laatste werk is, word de Expositie verwijderd.'");
										return r;
									}
								</script>
							</div>
						</div>
					</p>
				</div>			
			</div>
		</c:forEach>
	</center>
</body>
</html>