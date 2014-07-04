<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*, rest.User"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="org.postgresql.Driver"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="res/css/additives.css" rel="stylesheet">

<html lang="en" ng-app="cc">
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
<link href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="favicon.ico" />

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
<% 
	if(request.getSession().getAttribute("nosubs")!=null && request.getSession().getAttribute("nosubs").equals("yes")) { %>
	<script>
	var result = confirm("U hebt geen abonnementen, wilt u een abonnement afsluiten?");
	var contextpath = "${pageContext.request.contextPath}";
	
	<%request.getSession().removeAttribute("nosubs"); %>
	if(result) {
		window.location.replace(contextpath + "/subscription");
	} else {
		this.close();
	}
	
	</script>
<%
}%>
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
SELECT a.id, a.name, a.source, b.artist, b.height, b.width, b.style, b.technique, b.orientation, b.price, b.rating FROM art a, artpiece b
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

	<sql:query dataSource="${snapshot}" var="rents">
SELECT * FROM rent r
	</sql:query>

	<CENTER>
		<H1>Collectie</H1>
		<script>
			var advanced = false;
		</script>
		<form class="navbar-form" id="search" method="POST"
			action="<%=request.getContextPath()%>/search">
			<div style="width: 400px;">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="zoeken"
						name="srch-term" id="srch-term">
					<div class="input-group-btn">
						<button class="btn btn-default" style="height: 34px;"
							type="button" onclick="submitForms();">
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
				<!-- <form id="advancedOpt" method="POST" action="<%=request.getContextPath()%>/search"> -->

				<div class="table-responsive">
					<table class="table table-condensed">
						<tr>
							<td>Prijs</td>
							<td>Artiest</td>
							<td>Afmetingen</td>
							<td>Stijl</td>
							<td>Techniek</td>
							<td>Orientatie</td>
							<td>Beoordeling</td>

						</tr>

						<tr>
							<td>
								<table>
									<tr>
										<td><input type="number" name="minPrice" id="minPrice"
											placeholder="Minimale prijs"></td>
									</tr>
									<tr>
										<td><input type="number" name="maxPrice" id="maxPrice"
											placeholder="Maximale prijs"></td>
									</tr>
								</table>
							</td>
							<td>
								<table>
									<%
										for(int i = 0; i<artists.size();i++){
									%>
									<tr>
										<td><input type="checkbox" name="<%=artists.get(i)%>"
											id="<%=artists.get(i)%>"><%=artists.get(i)%></td>
									</tr>
									<%
										}
									%>
								</table>
							</td>
							<td>
								<table>
									<tr>
										<td><input type="number" name="minBred" id="minBred"
											placeholder="Minimale breedte"></td>
									</tr>
									<tr>
										<td><input type="number" name="maxBred" id="maxBred"
											placeholder="Maximale breedte"></td>
									</tr>
									<tr>
										<td><input type="number" name="minHoog" id="minHoog"
											placeholder="Minimale hoogte"></td>
									</tr>
									<tr>
										<td><input type="number" name="maxHoog" id="maxHoog"
											placeholder="Maximale hoogte"></td>
									</tr>
								</table>
							</td>
							<td>
								<table>
									<%
										for(int i = 0; i<styles.size();i++){
									%>
									<tr>
										<td><input type="checkbox" name="<%=styles.get(i)%>"
											id="<%=styles.get(i)%>"><%=styles.get(i)%></td>
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
										<td><input type="checkbox" name="<%=techs.get(i)%>"
											id="<%=techs.get(i)%>"><%=techs.get(i)%></td>
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
										<td><input type="checkbox" name="<%=orients.get(i)%>"
											id="<%=orients.get(i)%>"><%=orients.get(i)%></td>
									</tr>
									<%
										}
									%>
								</table>
							</td>
							<td>
								<table>
									<tr>
										<td><input type="number" name="minRat" id="minRat"
											placeholder="Minimale rating"></td>
									</tr>
									<tr>
										<td><input type="number" name="maxRat" id="maxRat"
											placeholder="Maximale rating"></td>
									</tr>
									<tr>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!--
				<div class="input-group-submit">
					<input class="btn btn-default" style="height: 34px;" type="submit"
						value="Vernieuwen"> <span class="glyphicon"></span> </input>
				</div>
  -->

			</div>
		</form>
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
							<div style="height: 250px;">
								<a class="plaatje" rel="gallery" href="img/${row.source}"
									caption='<h5>${row.name}</h5>${row.artist}<br>${row.width} x ${row.height}<br>${row.rating}<br>&euro;${row.price}'>
									<img src="img/${row.source}" alt="${row.source}"
									style="max-height: 100%; max-width: 100%;" />
								</a>
							</div>
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
									<% 
									pageContext.setAttribute("currentDate",new java.sql.Date(new java.util.Date().getTime()));
									HttpSession s = request.getSession();
									if(s.getAttribute("isAdmin") != null && (Boolean)s.getAttribute("isAdmin") && s.getAttribute("Logged") != null) {
									%>
									<p>
									<form name="myForm" action="remove" onsubmit="return popUp()"
										method="post">
										<input type="hidden" name="removing" value="${row.id}">
										<div class="btn-group">
											<input type="submit" class="btn btn-primary" role="button"
												value="Verwijder">
										<jsp:include page="share.jsp">
											<jsp:param name="source" value="${row.source}" />
										</jsp:include>
										</div>
									</form>
	
									<script>
										function popUp() {
											var r = confirm("Weet je zeker dat je dit werk wilt verwijderen?");
											return r;
										}
									</script>
									<%
										} else {
									%>
									<c:choose>
									<c:when test="${fn:length(rents.rows) gt 0}">
									<c:forEach var="rentrow" items="${rents.rows}">
										<c:choose>
											<c:when test="${rentrow.artpiece == row.id}">
												<c:set var="endingdate" value="${rentrow.endingdate}" />
												<%
												Calendar start = Calendar.getInstance();
												Calendar end = Calendar.getInstance();
												String[] startDate = ((java.sql.Date)pageContext.getAttribute("currentDate")).toString().split("-");
												String[] endDate = ((java.sql.Date)pageContext.getAttribute("endingdate")).toString().split("-");
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
												<p>
													<% if(diffMonth != 0 && diffDay != 0) { %><font size=2 color='red'>Beschikbaar over <%=diffMonth%> <% if(diffMonth == 1) { %> maand <% } else { %> maanden <% } %> en <%=diffDay%> <% if(diffDay == 1) { %> dag <% } else { %> dagen. <% } %></font>
													<% } else if(diffMonth == 0) { %> <font size=2 color='red'>Beschikbaar over <%=diffDay%> <% if(diffDay == 1) { %> dag <% } else { %> dagen. <% } %></font>
													<% } else { %> <font size=2 color='red'>Beschikbaar over <%=diffMonth%> <% if(diffMonth == 1) { %> maand <% } else { %> maanden. <% } %></font>
													<% } %>
												</p>
												<p>
												<c:set var="beschikbaar" value="false" />
												<c:set var="artID" value ="${row.id}" />
												
												<% if(request.getSession().getAttribute("Reservations") != null && ((Map<Integer, java.sql.Date>)request.getSession().getAttribute("Reservations")).containsKey(pageContext.getAttribute("artID"))) { %>
												
												<form action="<%=request.getContextPath()%>/reservations" method="get">
													<div class="btn-group">
														<input type="submit" class="btn btn-primary" role="button" value="Gereserveerd" />
														<jsp:include page="share.jsp">
															<jsp:param name="source" value="${row.source}" />
														</jsp:include>
													</div>
												</form>
												<% } else { %>
												<form action="<%=request.getContextPath()%>/reserveer" method="post">
													<input type="hidden" name="id" id="" value="${row.id}" />
													<div class="btn-group">
														<input type="submit" class="btn btn-primary" role="button" value="Reserveer" />
														<jsp:include page="share.jsp">
															<jsp:param name="source" value="${row.source}" />
														</jsp:include>
													</div>
												</form>
												<% } %>
												<c:remove var="artID" />
											</c:when>
										</c:choose>
									</c:forEach>
									<c:if test="${empty beschikbaar}">
										<p>
											<font color='green'>Beschikbaar</font>
										</p>
										<p>
										<form action="<%=request.getContextPath()%>/huren" method="post">
										<input type="hidden" name="id" value="${row.id}" />
											<div class="btn-group">
												<input type="submit" class="btn btn-primary" role="button" value="Huur direct!" />
												<jsp:include page="share.jsp">
													<jsp:param name="source" value="${row.source}" />
												</jsp:include>
											</div>
										</form>
									</c:if>
									<c:remove var="beschikbaar" />
									</c:when>
									<c:otherwise>
										<p>
											<font color='green'>Beschikbaar</font>
										</p>
										<p>
											<form action="<%=request.getContextPath()%>/huren" method="post">
												<input type="hidden" name="id" value="${row.id}" />
												<div class="btn-group">
													<input type="submit" class="btn btn-primary" role="button" value="Huur direct!" />
													<jsp:include page="share.jsp">
														<jsp:param name="source" value="${row.source}" />
													</jsp:include>
												</div>
											</form>
										</p>
									</c:otherwise>
									</c:choose>
								<% } %>
								</div>
								<p>
								<div>
									<%
										if(s.getAttribute("Logged") != null) {
									%>
									<form name="myForm" action="voegToeExpositie" method="post" id="form124">
										<input type="hidden" name="id" value="${row.id}"> <select class="form-control" style="width:60%;"
											name="expo" onchange="namePopUp(this);submit();">
											<option value="">Voeg toe aan expositie</option>
											<%
												Map<Integer, String> expositions = (Map<Integer, String>)s.getAttribute("Expositions");
																				if(expositions != null)  {
																					for(Integer i : expositions.keySet()) {
											%>
											<option value=<%=i%>><%=expositions.get(i)%></option>
											<%
												}
																				}
											%>
											<option myid=1>Nieuwe Expositie</option>
										</select> 
									</form>

									<script>
										function namePopUp(select) {
											if (select.options[select.selectedIndex]
													.getAttribute("myid") == 1) {
												var expo = prompt(
														"Kies een naam voor uw Expositie",
														"Expositie");
												if (expo != null && expo != "") {
													select.options[select.selectedIndex].innerHTML = expo;
													select.options[select.selectedIndex]
															.setAttribute(
																	"value",
																	expo);
													document.getElementById("form124").submit();
												}
											}
										}
									</script>

									<%
										}
									%>

									<form action="search" method="post" id="ratingform" onmouseout="reset();">
										<input type="checkbox" name="ratingGroup" class="glyphicon glyphicon-star-empty rating-star" id="${row.id}c1" onmouseover="hover(this,${row.id});" onclick="clicked(this,${row.id});" value="1">&ensp;
										<input type="checkbox" name="ratingGroup" class="glyphicon glyphicon-star-empty rating-star" id="${row.id}c2" onmouseover="hover(this,${row.id});" onclick="clicked(this,${row.id});" value="2">&ensp; 
										<input type="checkbox" name="ratingGroup" class="glyphicon glyphicon-star-empty rating-star" id="${row.id}c3" onmouseover="hover(this,${row.id});" onclick="clicked(this,${row.id});" value="3">&ensp;  
										<input type="checkbox" name="ratingGroup" class="glyphicon glyphicon-star-empty rating-star" id="${row.id}c4" onmouseover="hover(this,${row.id});" onclick="clicked(this,${row.id});" value="4">&ensp;
										<input type="checkbox" name="ratingGroup" class="glyphicon glyphicon-star-empty rating-star" id="${row.id}c5" onmouseover="hover(this,${row.id});" onclick="clicked(this,${row.id});" value="5">&ensp;
										<input type="hidden" id="rating" name="rating" value="0&${row.id}">
									</form>
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
function hover(c, id) {
	  rating = parseInt(c.value);
	  for (var i = 1; i <= 5; i++) { 
		  document.getElementById(id + "c" + i).checked = false;
		}
	  
	  for (var i = 1; i <= rating; i++) { 
		  document.getElementById(id + "c" + i).checked = true;
		}
	}
	
function clicked(c, id){
	rating = parseInt(c.value);
	document.getElementById("rating").setAttribute("value", rating + "&" + id);
	document.getElementById("ratingform").submit();
	console.log("submitted");
}
</script>
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

	function submitForms() {
		document.getElementById("search").submit();
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".plaatje").fancybox({
			beforeLoad : function() {
				this.title = '<center>' + $(this.element).attr('caption');
			},
			openEffect : 'elastic',
			closeEffect : 'elastic',
			nextEffect : 'fade', // 'elastic', 'fade' or 'none'
			prevEffect : 'fade', // 'elastic', 'fade' or 'none'
			padding : 0,
			type : "image",
			helpers : {
				title : {
					type : 'outside',
				},
				overlay : {
					speedIn : 0,
					speedOut : 0,
					opacity : 0.6,
					css : {
						cursor : 'pointer',
						'background-color' : 'rgba(0, 0, 0, 0.70)' //Browsers who don`t support rgba will fall back to default color value defined at CSS file
					},
					closeClick : true
				}
			}
		})
	});
</script>
</html>
