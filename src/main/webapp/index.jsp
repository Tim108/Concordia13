 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	<title>Concordia</title>
	<!-- Bootstrap -->
	<link href="<%=request.getContextPath()%>/res/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/res/css/carousel.css" rel="stylesheet">
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
	<style>
	#containert {
   width: 100%;
   height: 320px;
}

#containert img {
   width: 100%;
   margin-top: -20%;
}
	</style>
</head>
<body>
	<jsp:include page="main.jsp">
		<jsp:param name="currentpage" value="home" />
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

	<sql:query dataSource="${snapshot}" var="art">
	SELECT COUNT(*) FROM artpiece
	</sql:query>
	
	<% 
	if(request.getAttribute("artheaders")==null) { response.sendRedirect(request.getContextPath()+"/rest/generateArt/frontpage"); return; } 
	List<String> artheaders = (List<String>) request.getAttribute("artheaders");
	request.removeAttribute("artheaders");
	%>
	<div class="head">
		<CENTER><H1 STYLE="font-size: 36pt;">Populaire werken</H1></CENTER>
    	<div id="myCarousel" class="carousel slide" data-ride="carousel">
     	 <!-- Indicators -->
      		<ol class="carousel-indicators">
        		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        		<li data-target="#myCarousel" data-slide-to="1"></li>
        		<li data-target="#myCarousel" data-slide-to="2"></li>
     		 </ol>
      	<div class="carousel-inner">
        	<div class="item active">
        	<div id="containert">
    			<img src="<%=request.getContextPath()%>/img/<%= artheaders.get(0) %>" alt="" style="margin-top:-<%=Math.random()*30%>%;"/>
			</div>
        	  <div class="container">
        	    <div class="carousel-caption">
        	      <h1>Maak je eigen online exposities</h1>
        	      <p></p>
        	      <p><a class="btn btn-lg btn-primary" href="<%=request.getContextPath()%>/expositie?id=0" role="button">Start</a></p>
        	    </div>
        	  </div>
        	  </div>
        	<div class="item">
        	 <div id="containert">
    			<img src="<%=request.getContextPath()%>/img/<%= artheaders.get(1) %>" alt="" style="margin-top:-<%=Math.random()*30%>%;"/>
			</div>
          <div class="container">
            <div class="carousel-caption">
            <c:forEach var="row" items="${art.rows}">
              <h1>${row.count} werken die je kunt huren</h1>
            </c:forEach>
              <p></p>
              <p><a class="btn btn-lg btn-primary" href="<%=request.getContextPath()%>/search" role="button">Bekijk Collectie</a></p>
            </div>
          </div>
        </div>
        <div class="item">
          <div id="containert">
    			<img src="<%=request.getContextPath()%>/img/<%= artheaders.get(2) %>" alt="" style="margin-top:-<%=Math.random()*30%>%;"/>
			</div>
          <div class="container">
            <div class="carousel-caption">
              <h1>Vanaf slechts 7,50 euro per maand</h1>
              <p></p>
              <p><a class="btn btn-lg btn-primary" href="<%=request.getContextPath()%>/subscriptions.jsp" role="button">Abonneer</a></p>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
      </div>
	  <CENTER><h1>Welkom bij Concordia</h1></CENTER>
	  <c:forEach var="row" items="${art.rows}">
	  <CENTER><p>Jouw portaal naar betaalbare kunst. Begin gelijk met zoeken in ${row.count} kunstwerken.</p></Center>
	  </c:forEach>
		<CENTER><div style="width:400px;">
	 	  <form class="navbar-form" method="POST" action="<%=request.getContextPath()%>/search">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Zoeken" name="srch-term" id="srch-term">
          <div class="input-group-btn">
            <button class="btn btn-default" style="height:34px;" type="submit"><span class="glyphicon glyphicon-search"></span></button>
          </div>
        </div></CENTER>
      </form>
	 </div>
	</div>
	</div>
	</body>
	<script>
	<script type="text/javascript">
	(function() {

	var img = document.getElementById('containert').firstChild;
	img.onload = function() {
		if(img.height > img.width) {
	        img.height = 'auto';
	        img.width = '100%';
	    }
	};

	}());
	</script>
	</html>
