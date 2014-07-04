<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%if(request.getSession().getAttribute("Logged") == null) { %> <meta http-equiv="refresh" content="0;<%=request.getContextPath()%>/login.jsp"> <% 
	} %>
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
		<jsp:param name="currentpage" value="reservations" />
	</jsp:include>
	
	<%
	List<Integer> ids = (List<Integer>)request.getAttribute("IDs");
	List<String> sources = (List<String>)request.getAttribute("Sources");
	List<java.sql.Date> dates = (List<java.sql.Date>)request.getAttribute("Dates");
	List<Integer> names = (List<Integer>)request.getAttribute("Names");
	%>
	<center>
<% 
	if(request.getAttribute("Error") != null) {
	%>
	<br><font color="red"><h2>${Error}</h2></font>
	<% } else {%>
	<table style="border-collapse:separate; border-spacing:0 0px;">
		<%
		for(int i=0; i<sources.size(); i++) { %>
		
			<%
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			String[] startDate = (new java.sql.Date(new java.util.Date().getTime())).toString().split("-");
			String[] endDate = (dates.get(i)).toString().split("-");
			start.set(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[2]));
			end.set(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[2]));
												
			int diffMonth = 0;
			int diffDay = 0;
			if(start.get(Calendar.MONTH) > end.get(Calendar.MONTH)) {
				diffMonth = 12 - start.get(Calendar.MONTH) + end.get(Calendar.MONTH);
			}
			else 
				diffMonth = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
			if(start.get(Calendar.DAY_OF_MONTH) > end.get(Calendar.DAY_OF_MONTH)) {
				diffMonth--;
				diffDay = (int) 30.5 - (start.get(Calendar.DAY_OF_MONTH) - end.get(Calendar.DAY_OF_MONTH));
			}
			else 
				diffDay = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);
			%>
			<tr style="background-color:#E8E8E8;">
				<td style="border-radius:10px 0 0 0; -moz-border-radius: 10px 0 0 0; text-align: center; padding:10px; margin:0px;"><b><%=names.get(i)%></b></td>
				<td style="padding-right:10px; padding-left:10px; padding-top:10px; padding-bottom:10px;" rowspan="2">
					<img src="img/<%= sources.get(i) %>" alt="<%= sources.get(i) %>" style="width:200px; max-heigth:100%; border-radius: 10px 10px 10px 10px; -moz-border-radius: 10px 10px 10px 10px;"/>
				</td>
				<td rowspan="2" style="border-radius: 0 10px 10px 0; -moz-border-radius: 0 10px 10px 0; padding-top:10px; padding-right:10px; padding-bottom:10px;">
					<h2>
						<form action="<%=request.getContextPath()%>/removeReservation" method="post">
						<input type="hidden" name="id" value="<%=ids.get(i)%>" />
						<font color="darkred"><button onclick="popUp()" onmouseover="document.getElementById('remove<%=i%>').setAttribute('class','glyphicon glyphicon-remove-circle')" onmouseout="document.getElementById('remove<%=i%>').setAttribute('class','glyphicon glyphicon-remove')" style="padding: 0; border: none; background: none;"><span id="remove<%= i %>" class="glyphicon glyphicon-remove" /></button></font></h2></td></tr>
						</form>
						<script>
						function popUp() {
							var r = confirm("Weet je zeker dat je dit werk wilt verwijderen?");
							if(r) form.submit();
						}
						</script>
					</h2>
				</td>
			</tr>
			<tr style="background-color:#E8E8E8; padding-top:0px; margin:0px;">
				<td style="border-radius: 0px 0 0 10px; -moz-border-radius: 0px 0 0 10px; padding:10px;">
					<% if(diffMonth != 0 && diffDay != 0) { %><font color='red'>Beschikbaar over <%=diffMonth%> <% if(diffMonth == 1) { %> maand <% } else { %> maanden <% } %> en<br> <%=diffDay%> <% if(diffDay == 1) { %> dag <% } else { %> dagen. <% } %></font>
					<% } else if(diffMonth == 0) { %> <font color='red'>Beschikbaar over <%=diffDay%> <% if(diffDay == 1) { %> dag <% } else { %> dagen. <% } %></font>
					<% } else { %> <font color='red'>Beschikbaar over <%=diffMonth%> <% if(diffMonth == 1) { %> maand <% } else { %> maanden. <% } %></font>
					<% } %>
				</td>
			</tr>
			<tr>
			<td><br>
			</td>
			</tr>
		<%
		}
		%>
	</table>
	<% } %>
	</center>
</body>
</html>