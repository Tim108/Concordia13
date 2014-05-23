<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="org.postgresql.Driver"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="res/css/additives.css" rel="stylesheet">

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Collectie - Concordia</title>

<!-- Bootstrap -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.0.0.js"></script>
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
<script type="text/javascript" src="fancybox/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="fancybox/jquery.fancybox.css"
	type="text/css" media="screen" />
<link rel="stylesheet" href="fancybox/jquery.fancybox-buttons.css"
	type="text/css" media="screen" />
<script type="text/javascript" src="fancybox/jquery.fancybox-buttons.js"></script>
<style type="text/css">
.content {
	width: 800px;
	margin: auto;
}
</style>

</head>
<body>
	<jsp:include page="main.jsp">
		<jsp:param name="currentpage" value="collection" />
	</jsp:include>


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
	%>

	<sql:setDataSource var="snapshot" driver="org.postgresql.Driver"
		url="<%=url%>" user="<%=user%>" password="<%=pass1%>" />

	<sql:query dataSource="${snapshot}" var="artpieces">
SELECT a.name, a.source, b.artist, b.height, b.width, b.style, b.technique, b.orientation, b.price, b.rating, b.rented FROM art a, artpiece b
WHERE a.id=b.id
<%
		if (request.getAttribute("Search") != null) {
			List<String> l = (List<String>) request
			.getAttribute("Search");
	%>
 AND (a.name='<%=l.get(0)%>'
	<%
		for (int i = 1; i < l.size(); i++) {
	%>
 OR a.name='<%=l.get(i)%>'
<%
		}}
		if(request.getAttribute("Search") != null)
	%>)
ORDER BY rating DESC;
</sql:query>

	<CENTER>
		<H1>Collectie</H1>
		<script>
			var advanced = false;
		</script>

		<div style="width: 400px;">
			<form class="navbar-form" method="POST" action="/concordia/search">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="zoeken"
						name="srch-term" id="srch-term">
					<div class="input-group-btn">
						<button class="btn btn-default" style="height: 34px;"
							type="submit">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</div>
					<div class="input-group-btn">
						<button class="btn btn-default" style="height: 34px;"
							type="button" onclick="showDivContent();">
							<span class="glyphicon glyphicon-cog"></span>
						</button>
					</div>
				</div>
			</form>
		</div>

		<p id="spot1"></p>

		<%
			List<Double> prices = (List<Double>) request.getAttribute("prices");
				List<String> artists = (List<String>) request.getAttribute("artists");
				List<Double> widths = (List<Double>) request.getAttribute("widths");
				List<Double> heights = (List<Double>) request.getAttribute("heights");
				List<String> styles = (List<String>) request.getAttribute("styles");
				List<String> techs = (List<String>) request.getAttribute("techs");
				List<String> orients = (List<String>) request.getAttribute("orients");
				List<Double> ratings = (List<Double>) request.getAttribute("ratings");
		%>

		<div id="advancedSearchDiv" class="hidden-element">
			<div class="table-responsive">
				<table class="table table-condensed">
					<tr>
						<td align="center">Prijs</td>
						<td align="center">Artiest</td>
						<td align="center">Afmetingen</td>
						<td align="center">Stijl</td>
						<td align="center">Techniek</td>
						<td align="center">Orientatie</td>
						<td align="center">Beoordeling</td>

					</tr>
					<tr>
						<td>
							<table>
								<tr><td><input type="text" placeholder="Minimale prijs"></td></tr>
								<tr><td><input type="text" placeholder="Maximale prijs"></td></tr>
							</table>
						</td>
						<td>
							<table>
								<%
									for(int i = 0; i<artists.size();i++){
								%>
								<tr>
									<td><input type="checkbox"><%=artists.get(i)%></td>
								</tr>
								<%
									}
								%>
							</table>
						</td>
						<td>
							<table>
								<tr><td><input type="text" placeholder="Minimale breedte"></td></tr>
								<tr><td><input type="text" placeholder="Maximale breedte"></td></tr>
								<tr><td><input type="text" placeholder="Minimale hoogte"></td></tr>
								<tr><td><input type="text" placeholder="Maximale hoogte"></td></tr>
							</table>
						</td>
						<td>
							<table>
								<%
									for(int i = 0; i<styles.size();i++){
								%>
								<tr>
									<td><input type="checkbox"><%=styles.get(i)%></td>
								</tr>
								<%
									}
								%>
							</table>
						</td>
						<td>
							<table>
								<%
									for(int i = 0; i<techs.size();i++){
								%>
								<tr>
									<td><input type="checkbox"><%=techs.get(i)%></td>
								</tr>
								<%
									}
								%>
							</table>
						</td>
						<td>
							<table>
								<%
									for(int i = 0; i<orients.size();i++){
								%>
								<tr>
									<td><input type="checkbox"><%=orients.get(i)%></td>
								</tr>
								<%
									}
								%>
							</table>
						</td>
						<td>
							<table>
								<tr><td><input type="text" placeholder="Minimale rating"></td></tr>
								<tr><td><input type="text" placeholder="Maximale rating"></td></tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>

		<%
			if (request.getAttribute("Error") != null) {
		%>
		<h4>
			<font color="FF0000"><%=request.getAttribute("Error")%></font>
		</h4>
		<br>
		<%
			}
		%>
		<div class="container">
			<div class="row">
				<c:forEach var="row" items="${artpieces.rows}">
					<div class="col-md-4">
						<div class="thumbnail">
							<a class="plaatje" rel="gallery" href="img/${row.source}"
								caption='<h5>${row.name}</h5>${row.artist}<br>${row.width} x ${row.height}<br>${row.rating}<br>&euro;${row.price}'
								><img src="img/${row.source}" alt="${row.source}" style="height: 250px;" /></a>
							<div class="caption">

								<h3>
									<c:out value="${row.name}" />
								</h3>
								<div>
									<p>
										Artiest:
										<c:out value="${row.artist}" />
									</p>
									<p>
										Afmetingen:
										<c:out value="${row.width}" />
										x
										<c:out value="${row.height}" />
										px
									</p>
									<p>
										Stijl:
										<c:out value="${row.style}" />
									</p>
									<p>
										Techniek:
										<c:out value="${row.technique}" />
									</p>
									<p>
										Orientatie:
										<c:out value="${row.orientation}" />
									</p>
									<p>
										Beoordeling:
										<c:out value="${row.rating}" />
									</p>
									<h3>
										Prijs: &euro;
										<c:out value="${row.price}" />
									</h3>
									<c:choose>
										<c:when test="${row.rented==true}">
											<p>
												<font color='red'>Beschikbaar over 13 weken</font>
											</p>
										</c:when>
										<c:otherwise>
											<p>
												<font color='green'>Beschikbaar</font>
											</p>
										</c:otherwise>
									</c:choose>
								</div>
								<p>
									<c:choose>
										<c:when test="${row.rented==true}">
											<p>
											<div class="btn-group">
												<a href="#" class="btn btn-primary" role="button">Reserveer</a>
										</c:when>
										<c:otherwise>
											<p>
											<div class="btn-group">
												<a href="#" class="btn btn-primary" role="button">Huur
													direct!</a>
										</c:otherwise>
									</c:choose>
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown">
										Delen <span class="caret"></span>
									</button>
								<ul class="dropdown-menu" role="menu">
									<table>
										<tr>
											<td style="padding-right: 10px; padding-bottom: 10px"><a
												href="http://www.facebook.com/sharer.php?u=http://localhost:8080/concordia/img/${row.source}"
												target="_blank"><img
													src="http://www.simplesharebuttons.com/images/somacro/facebook.png"
													alt="Facebook" style="height: 50px;" /></a>
											<td style="padding-bottom: 10px;"><a
												href="http://twitter.com/share?url=http://localhost:8080/concordia/img/${row.source}&text=Geweldig werk gezien bij concordia! // via @Concordia053"
												target="_blank"><img
													src="http://www.simplesharebuttons.com/images/somacro/twitter.png"
													alt="Twitter" style="height: 50px;" /></a>
										</tr>
										<tr>
											<td style="padding-right: 10px;"><a
												href="https://plus.google.com/share?url=http://localhost:8080/concordia/img/${row.source}"
												" target="_blank"><img
													src="http://www.simplesharebuttons.com/images/somacro/google.png"
													alt="Google" style="height: 50px;" /></a>
											<td><a
												href="mailto:?Subject=Bekijk dit kunstwerk bij Concordia kunstuitleen!&Body=I%20saw%20this%20and%20thought%20of%20you!%20 http://localhost:8080/concordia/img/${row.source}"><img
													src="http://www.simplesharebuttons.com/images/somacro/email.png"
													alt="Email" style="height: 50px;" /></a>
										</tr>

									</table>

									<li class="divider"></li>
									<li><a href="#">Voeg toe aan expositie.</a></li>
								</ul>

							</div>
							</p>
						</div>
					</div>
			</div>
			</c:forEach>
		</div>
		</div>
	</CENTER>
</body>
<script>
	function showDivContent() {
		var spot = document.getElementById('spot1');
		var advancedSearch = document.getElementById('advancedSearchDiv').innerHTML;

		if (!advanced) {
			spot.innerHTML = advancedSearch;
			advanced = true;
		} else {
			spot.innerHTML = null;
			advanced = false;
		}
	}
</script>
<script type="text/javascript">
		$(document).ready(function() {
			$(".plaatje").fancybox({
				beforeLoad: function() {
			    	this.title = '<center>' + $(this.element).attr('caption');
			    },
				openEffect: 'elastic',
				closeEffect: 'elastic',
				nextEffect: 'fade', // 'elastic', 'fade' or 'none'
				prevEffect: 'fade', // 'elastic', 'fade' or 'none'
				padding : 0,
				type: "image",
				helpers	: {
					title: {
						type: 'outside',
					},
					overlay: {
						speedIn: 0,
						speedOut: 0,
						opacity: 0.6,
						css: {
							cursor: 'pointer',
							'background-color': 'rgba(0, 0, 0, 0.70)' //Browsers who don`t support rgba will fall back to default color value defined at CSS file
						},
						closeClick: true
					}
				}
			})	
		});
	</script>
</html>
