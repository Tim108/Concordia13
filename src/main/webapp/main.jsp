<% String currentpage = request.getParameter("currentpage");
	if(currentpage==null){
		currentpage="EMPTY";
	}
	if(session.getAttribute("hasExposition") == null) {
		session.setAttribute("hasExposition",0);
	}
	%>
	<BR>
	<CENTER>
		<a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/res/logo-concordia.jpg" alt="Concordia" /></a>
	</CENTER>
	<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li <% if(currentpage.equals("home")){ %> class="active" <% } %>><a href="<%=request.getContextPath()%>" id="home">Home</a></li>
				<li <% if(currentpage.equals("collection")){ %> class="active" <% } %>><a href="<%=request.getContextPath()%>/search" id="collectie">Collectie</a></li>
				<li <% if(currentpage.equals("expositie")){ %> class="active" <% } %>><a href="<%=request.getContextPath()%>/expositie?id=<%=session.getAttribute("hasExposition")%>" id="expositie">Expositie</a></li>
				<li <% if(currentpage.equals("reservations")){ %> class="active" <% } %>><a href="<%=request.getContextPath()%>/reservations">Reserveringen</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
			<% HttpSession s = request.getSession();
				if(s.getAttribute("isAdmin") != null && (Boolean)s.getAttribute("isAdmin") && s.getAttribute("Logged") != null) { %>
				<li<% if(currentpage.equals("admin")){ %> class="active" <% } %>><a href="<%=request.getContextPath()%>/admin.jsp" id="cp">Controlepaneel</a></li>
				<% } %>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" id="acc">
					<%if(s.getAttribute("isAdmin") != null && (Boolean)s.getAttribute("isAdmin") && s.getAttribute("Logged") != null) { %>
					<span class="glyphicon glyphicon-tower"></span>
					<% } else{ %>
					<span class="glyphicon glyphicon-user"></span>
					<% } %>
					<% 
					if(s.getAttribute("Logged") == null) {
					%>
					Account <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a>
							<form method="post" action=<%=request.getContextPath()%>/login id="loginform">
								<table>
									<tr><td><input type="text" name="email" placeholder="E-mail" id="un"></td></tr>
									<tr><td><input type="password" name="password" placeholder="Wachtwoord" id="pw"></td></tr>
								</table>
								<input type="submit" class="btn btn-default" value="Log in" name="Log in">
							</form> <!--  -->
						</a></li>
					<li class="divider"></li>
					<li><a href="<%=request.getContextPath()%>/registerpage">Registeren</a></li>
					</ul>
					<% 
					}
					else {
					%>
					<%= session.getAttribute("Name") + " " + session.getAttribute("SurName") %> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="<%=request.getContextPath()%>/userinfo" id="userinfo">Gegevens</a></li>
            			<li><a href="<%=request.getContextPath()%>/reservations" id="reservations">Reserveringen</a></li>
            			<li><a href="<%=request.getContextPath()%>/expositie?id=<%=session.getAttribute("hasExposition")%>" id="exposition">Expositie</a></li>
            			<li><a href="<%=request.getContextPath()%>/gehuurde.jsp" id="rented">Mijn gehuurde werken</a></li>
            			<li><a href="<%=request.getContextPath()%>/subscription" id="subscription">Abonnement afsluiten</a></li>
            			<li class="divider"></li>
						<li><a href="<%=request.getContextPath()%>/loguit" id="logout">Log uit</a></li>

					</ul>
					<%
					}
					%>
				</li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>