<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ page import="java.util.*, java.text.*"%>
<title>Concordia | Gegevens</title>
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
	<jsp:include page="main.jsp" />
	<div class="head">
		<center>
			<form action="subscriptions.jsp" method="get">
				<h1>Uw Abonnementen</h1>
				<hr>
				<table width="500">
					<tr style="font-weight: bold;">
						<td style="padding-right: 10px; text-align: center;" width="50%">
							Regulier abonnement</td>
						<td style="padding-right: 10px; text-align: center;" width="50%">
							Spaarabonnement</td>
					</tr>
					<tr>
						<td style="text-align: center;">7,50 euro per maand</td>
						<td style="text-align: center;">14,50 euro per maand<br>
							<i>Hiervan is 7,50 euro voor het abonnement en wordt er 7,00
								per maand weggelegd als spaartegoed.</i>
						</td>
					</tr>
					<tr>
						<td style="text-aligN: center;">
							<button type="submit" name="abonnement" value="regulier" class="btn btn-primary" id="regulier">Sluit
								regulier abonnement af</button>
						</td>
						<td style="text-align: center;">
							<button type="submit" name="abonnement" value="spaar" class="btn btn-primary" id="spaar">Sluit
								spaarabonnement af</button>
						</td>
					</tr>
				</table>
			</form>
			<%
				String abonnement = request.getParameter("abonnement");
				DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
				Calendar cal = Calendar.getInstance();
				Date now = cal.getTime();
				cal.add(Calendar.MONTH, 6);
				Date exp = cal.getTime();
				if (abonnement != null) {
			%>
			<hr>
			<%
				if (abonnement.equals("regulier")) {
			%>
			<h2>Regulier</h2>
			Weet u zeker dat u een <b>regulier abonnement</b> wilt afsluiten?<br>
			<table>
				<tr>
					<td style="padding-right: 10px;">Begindatum</td>
					<td style="padding-left: 10px;"><%=dateFormat.format(now)%>
					</td>
				</tr>
				<tr>
					<td style="padding-right: 10px;">Einddatum</td>
					<td style="padding-left: 10px;"><%=dateFormat.format(exp)%>
					</td>
				</tr>
				<tr>
					<td style="padding-right: 10px;">Kosten per maand</td>
					<td style="padding-left: 10px;">7,50</td>
				</tr>
			</table>
			<form action="subscriptions" method="post">
				<input type="hidden" name="ideal" value="regulier"> <input
					type="image" src="img/iDEAL-klein.jpg" id="ideal">
			</form>
			<%
				} else {
			%>
			<h2>Spaar</h2>
			Weet u zeker dat u een <b>spaarabonnement</b> wilt afsluiten?<br>
			<table>
				<tr>
					<td style="padding-right: 10px;">Begindatum</td>
					<td style="padding-left: 10px;"><%=dateFormat.format(now)%>
					</td>
				</tr>
				<tr>
					<td style="padding-right: 10px;">Einddatum</td>
					<td style="padding-left: 10px;"><%=dateFormat.format(exp)%>
					</td>
				</tr>
				<tr>
					<td style="padding-right: 10px;">Kosten per maand</td>
					<td style="padding-left: 10px;">14,50</td>
				</tr>
			</table>
			<form action="subscriptions" method="post">
				<input type="hidden" name="ideal" value="spaar"> <input
					type="image" src="img/iDEAL-klein.jpg">
			</form>
			<%
				}
				}
				if (request.getAttribute("done") != null) {
					if ((Boolean) request.getAttribute("done")) {
			%>
			<hr>
			U heeft succesvol een <b> <%
 			if (request.getAttribute("ideal").equals("regulier")) {
 				%>
				regulier abonnement <%
 				} else {
 			%> spaarabonnement <%
 			}
 				%>
			</b> afgesloten.
			<%
				} else {
					%> 
					<hr> <img src="res/redCross.png" height="14px;" alt="ERROR:"><font color="red"> Er is iets mis gegaan, probeer het later opnieuw.</font> <%
				}
				}
			%>
		
	</div>