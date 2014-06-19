<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="org.postgresql.Driver"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Concordia - Expositie</title>
    <link rel="icon" type="image/x-icon" href="favicon.ico"/>
    
<!-- Bootstrap -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.0.0.js"></script>
	<link href="res/css/bootstrap.min.css" rel="stylesheet">
	<link href="res/css/carousel.css" rel="stylesheet">
	
<!-- Fancybox -->
	<script type="text/javascript" src="fancybox/jquery.fancybox.pack.js"></script>
	<link rel="stylesheet" href="fancybox/jquery.fancybox.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="fancybox/jquery.fancybox-buttons.css" type="text/css" media="screen" />
	<script type="text/javascript" src="fancybox/jquery.fancybox-buttons.js"></script>

<!--  OWL-contents -->
	<link href="res/owl-carousel/owl.carousel.css" rel="stylesheet">
    <link href="res/owl-carousel/owl.theme.css" rel="stylesheet">
    <script src="res/owl-carousel/owl.carousel.js"></script>
    
     <style>
    #owl-demo .item{
        margin: 3px;
    }
    #owl-demo .item img{
        display: block;
        width: 100%;
        height: auto;
    }
    </style>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
   	<![endif]-->

	<meta content="width=320px, initial-scale=1, user-scalable=yes" name="viewport" />
	<style type="text/css">
	.content {
	width: 800px;
	margin: auto;
	}
</style>
</head>
<body>

	<jsp:include page="main.jsp">
		<jsp:param name="currentpage" value="expositie" />
	</jsp:include>

<!-- DB Connection -->
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
			
		List<Integer> ids = (List<Integer>)request.getAttribute("Arts");
		List<String> names = (List<String>)request.getAttribute("Names");
		String error = (String)request.getAttribute("Error");
		if(error == null) { 
	%>
	<sql:setDataSource var="snapshot" driver="org.postgresql.Driver"
		url="<%=url%>" user="<%=user%>" password="<%=pass1%>" />
	<sql:query dataSource="${snapshot}" var="artpieces">
SELECT a.id, a.name, a.source, b.artist, b.height, b.width, b.style, b.technique, b.orientation, b.price, b.rating, b.rented FROM art a, artpiece b
WHERE a.id=b.id
AND (
<%	for(int i=0; i<ids.size(); i++) { 
System.out.println(ids.get(i));%>
a.id = '<%=ids.get(i)%>' OR
<% } %>
a.id = '<%=ids.get(0)%>')
ORDER BY rating DESC
	</sql:query>	
	
	<div id="title">
      <div class="container">
        <div class="row">
          <div class="span12">
          	<h1><%=names.get(0)%></h1>
          	<h4>door <%=names.get(1)%> <%=names.get(2)%></h4>
          </div>
        </div>
      </div>
    </div>
    <% } else { %>
    <div id="title">
      <div class="container">
        <div class="row">
          <div class="span12">
            <h2><%=error%></h2>
          </div>
        </div>
      </div>
    </div>
    <% } %>

       <div id="demo">
        <div class="container">
          <div class="row">
            <div class="span12">

              <div id="owl-demo" class="owl-carousel owl-theme">
 	<c:forEach var="row" items="${artpieces.rows}">
                <div class="item">
                <CENTER>
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
									<div class="btn-group dropup">
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown">
										Delen <span class="caret"></span>
									</button>
									
									<form action="verwijderUitExpositie" method="post" onsubmit="return popUp()">
										<input type="hidden" name="id" value="${row.id}"> 
										<input type="submit" value="Verwijder uit expositie" class="btn btn-success">
									</form>
									
									<script>
										function popUp() {
											var r = confirm("Weet je zeker dat je dit werk wilt verwijderen?\nAls dit het laatste werk is, word de Expositie verwijderd.'");
											return r;
										}
									</script>

									<ul class="dropdown-menu pull-right" role="menu">
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
													" target="_blank"> <img
														src="http://www.simplesharebuttons.com/images/somacro/google.png"
														alt="Google" style="height: 50px;" /></a>
												<td><a
													href="mailto:?Subject=Bekijk dit kunstwerk bij Concordia kunstuitleen!&Body=I%20saw%20this%20and%20thought%20of%20you!%20 http://localhost:8080/concordia/img/${row.source}"><img
														src="http://www.simplesharebuttons.com/images/somacro/email.png"
														alt="Email" style="height: 50px;" /></a>
											</tr>




										</table>
									</ul>
									</div>

								</div>
								</p>
							</div>			
						</div>
						</CENTER>
					</div>
			</c:forEach>
                
                </div>

            </div>
          </div>
        </div>

    </div>
	<%if(error == null) {  %>
   <div class="customNavigation">
   <CENTER>
  	<a class="btn play"><span class="glyphicon glyphicon-play"></span> Autoplay</a>
    <a class="btn stop"><span class="glyphicon glyphicon-stop"></span> Stop</a>
  </CENTER>
</div>
<% } %>


 <!-- Rest of scripts -->
 
    <script src="res/owl-carousel/assets/bootstrap-collapse.js"></script>
    <script src="res/owl-carousel/assets/bootstrap-transition.js"></script>
    <script src="res/owl-carousel/assets/bootstrap-tab.js"></script>

	<script src="res/owl-carousel/assets/application.js"></script>
	
	<script>
	
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
		});
    	var owl = $("#owl-demo");
    	
      $("#owl-demo").owlCarousel({
   	items: 4,
   	slideSpeed: 800,
   	stopOnHover: true,
    pagination: false,
    navigation: true,
    navigationText: [
      '<h2><span class="glyphicon glyphicon-chevron-left"></span></h2>',
      '<h2><span class="glyphicon glyphicon-chevron-right"></span></h2>'
      ],
 
  });
   // Custom Navigation Events
      $(".play").click(function(){
    	owl.trigger('owl.next');
        owl.trigger('owl.play',5000); //owl.play event accept autoPlay speed as second parameter
      })
      $(".stop").click(function(){
        owl.trigger('owl.stop');
      })
    });
    </script>
	
</body>
</html>