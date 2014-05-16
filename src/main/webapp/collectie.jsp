<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="org.postgresql.Driver" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Collectie - Concordia</title>
	<%@include file="main.jsp" %>
 
 <sql:setDataSource var="snapshot" driver="org.postgresql.Driver"
     url="jdbc:postgresql://localhost:5432/Kunstuitleen"
     user="postgres"  password="dude"/>
 
<sql:query dataSource="${snapshot}" var="artpieces">
SELECT a.name, a.source, b.artist, b.height, b.width, b.style, b.technique, b.orientation, b.price, b.rating, b.rented FROM art a, artpiece b
WHERE a.id=b.id
<%
if(request.getAttribute("Search") != null) {
	List<String> l = (List<String>)request.getAttribute("Search"); %>
 AND (a.name='<%=l.get(0)%>'
	<%for(int i=0; i<l.size(); i++) { %>
 OR a.name='<%=l.get(i)%>'
 OR b.artist='<%=l.get(i)%>'
 OR b.technique='<%=l.get(i)%>'
<%
}}
if(request.getAttribute("Search") != null)%>)
;
</sql:query>
<CENTER><H1>Collectie</H1><CENTER>
	 <div style="width:400px;">
	 	  <form class="navbar-form" method="POST" action="/concordia/search">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="zoeken" name="srch-term" id="srch-term">
          <div class="input-group-btn">
            <button class="btn btn-default" style="height:34px;" type="submit"><span class="glyphicon glyphicon-search"></span></button>
          </div>
        </div>
      </form>
	 </div>
	 <%
		if(request.getAttribute("Error") != null) { %>
<h4><font color="FF0000"><%= request.getAttribute("Error") %></font></h4><br>
<% } %>
<% String euroteken = "\u20ac"; %>
<div class="container">
<div class="row">
  <c:forEach var="row" items="${artpieces.rows}">
    <div class="col-md-4">
  	    <div class="thumbnail">
	  		<a href=<c:out value="${row.source}"/>><img src=<c:out value="${row.source}"/> alt="..." style="height:250px;"></a>
	  	<div class="caption">
        <h3><c:out value="${row.name}"/></h3>
        <div><p>Artiest: <c:out value="${row.artist}"/></p>
        <p>Afmetingen: <c:out value="${row.width}"/> x <c:out value="${row.height}"/> px</p>
        <p>Stijl: <c:out value="${row.style}"/></p>
        <p>Techniek: <c:out value="${row.technique}"/></p>
        <p>Orientatie: <c:out value="${row.orientation}"/></p>
        <p>Beoordeling: <c:out value="${row.rating}"/></p>
        <h3>Prijs: <c:out value="${row.price}"/>0 euro</h3>
        <p>Uitgeleend: <c:out value="${row.rented}"/></p>
        </div>
        <p>  <div class="btn-group"> <a href="#" class="btn btn-primary" role="button">Reserveer</a>
  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
    Delen <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" role="menu">
    <li><a href="http://www.facebook.com/sharer.php?u=http://www.concordia.nl/kunstverhuur" target="_blank"><img src="http://www.simplesharebuttons.com/images/somacro/facebook.png" alt="Facebook" /></a>
</li>
    <li><a href="http://twitter.com/share?url=http://www.concordia.nl/kunstverhuur&text=Geweldig werk gezien bij concordia! // via @Concordia053" target="_blank"><img src="http://www.simplesharebuttons.com/images/somacro/twitter.png" alt="Twitter" /></a></li>
    <li><a href="https://plus.google.com/share?url=http://www.concordia.nl/kunstverhuur" target="_blank"><img src="http://www.simplesharebuttons.com/images/somacro/google.png" alt="Google" /></a></li>
<li><a href="mailto:?Subject=Bekijk dit kunstwerk bij Concordia kunstuitleen!&Body=I%20saw%20this%20and%20thought%20of%20you!%20 http://www.concordia.nl/kunstverhuur"><img src="http://www.simplesharebuttons.com/images/somacro/email.png" alt="Email" /></a>
</a></li>

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
 
 </body>
 </html>
