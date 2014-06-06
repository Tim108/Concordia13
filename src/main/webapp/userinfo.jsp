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
	<%
		SimpleDateFormat datum = new SimpleDateFormat("d MMM yyyy");
		List<String> allIDs = (List<String>) request.getAttribute("ids");
		List<Date> allBeginDates = (List<Date>) request
				.getAttribute("starts");
		List<Date> allEndDates = (List<Date>) request.getAttribute("einds");
		List<Boolean> allPremiums = (List<Boolean>) request
				.getAttribute("premiums");
		List<String> IDcheck = new ArrayList<String>();
	%>
	<jsp:include page="main.jsp" />
	<center>
		<h1>Gegevens</h1>
		<hr>

		<table
			style="display: inline-block; vertical-align: top; margin-right: 10px;">
			<tr style="font-weight: bold;">
				<td colspan="2"
					style="text-align: center; font-size: 20px; vertical-align: top">Contactgegevens</td>
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">Voornaam:

				
				<td style="padding-right: 10px; padding-bottom: 10px">${Naam}
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">Achternaam:

				
				<td style="padding-right: 10px; padding-bottom: 10px">${Achternaam}
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">Adres:
				<td style="padding-right: 10px; padding-bottom: 10px">${Adres}
				
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">Woonplaats:


				
				<td style="padding-right: 10px; padding-bottom: 10px">${Woonplaats}
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">Postcode:


				
				<td style="padding-right: 10px; padding-bottom: 10px">${Postcode}
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">Telefoon:


				
				<td style="padding-right: 10px; padding-bottom: 10px">${Telefoon}
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">E-mailadres:


				
				<td style="padding-right: 10px; padding-bottom: 10px">${Email}
			</tr>
			<tr>
				<td style="padding-right: 10px;">Kunsttegoed:
				<td>&euro;${Tegoed}</td>
			</tr>
		</table>

		<table
			style="display: inline-block; vertical-align: top; margin-left: 10px;">
			<tr style="font-weight: bold; text-alignment: center;">
				<td colspan="2"
					style="text-align: center; font-size: 20px; vertical-align: top">Abonnementgegevens</td>
			</tr>
			<tr style="font-weight: bold; text-alignment: center;">
				<td colspan="2" style="text-align: center; vertical-align: top">
					<input type="button" value="Nieuw abonnement afsluiten"
					onclick="window.location='/concordia/subscription';" class="btn btn-primary">
				</td>
			</tr>
			<%
			IDcheck.clear();
			int count = 1;
				for (int i = 0; i < allIDs.size(); i++) {
					if (!IDcheck.contains(allIDs.get(i).toString()))
						IDcheck.add(allIDs.get(i).toString());
				}
				for (int i = 0; i < IDcheck.size(); i++) {
				
			%>
			<tr style="font-weight: bold;">
				<td style="padding-right: 10px;">Abonnement <%=count%></td>
			</tr>
			<tr>
				<td style="padding-right: 10px;">Start datum:</td>
				<td style="padding-right: 10px;"><%=datum.format(allBeginDates.get(i))%></td>
			</tr>
			<tr>
				<td style="padding-right: 10px;">Eind datum:</td>
				<td style="padding-right: 10px;"><%=datum.format(allEndDates.get(i))%></td>
			</tr>
			<tr>
				<td style="padding-right: 10px; padding-bottom: 10px">Spaarabonnement:</td>
				<td style="padding-right: 10px; padding-bottom: 10px">
					<%
						if (!allPremiums.get(i)) {
					%>Nee <%
						} else {
					%> Ja <%
						}
					%>
				</td>
				<%
				count++;
					}
				allIDs.clear();
				allBeginDates.clear();
				allEndDates.clear();
				allPremiums.clear();
				%>
			</tr>
		</table>
	</center>
	<hr>