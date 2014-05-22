<!-- Bootstrap -->
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
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript" src="custom.js"></script>
<link type="text/css" rel="stylesheet" href="stylesheet.css" />

<style type="text/css">
.content {
	width: 800px;
	margin: auto;
}
</style>

</head>
<% String currentpage = request.getParameter("currentpage");
	if(currentpage==null){
		currentpage="EMPTY";
	}%>

<body>
	<BR>
	<CENTER>
		<a href="/concordia"><img src="res/logo-concordia.jpg" alt="Concordia" /></a>
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
				<li <% if(currentpage.equals("home")){ %> class="active" <% } %>><a href="/concordia">Home</a></li>
				<li <% if(currentpage.equals("collection")){ %> class="active" <% } %>><a href="collectie.jsp">Collectie</a></li>
				<li><a href="#">Expositie</a></li>
				<li><a href="easterEgg.jsp">Reserveringen</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
					<% 
					HttpSession s = request.getSession();
					if(s.getAttribute("Logged") == null) {
					%>
					Account <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a>
							<form method="post" action=/concordia/login>
								<table>
									<tr><td><input type="text" name="email" placeholder="E-mail" id="un"></td></tr>
									<tr><td><input type="password" name="password" placeholder="Wachtwoord" id="pw"></td></tr>
								</table>
								<input type="submit" class="btn btn-default" value="Log in" name="Log in">
							</form> <!--  -->
						</a></li>
					<li class="divider"></li>
					<li><a href="registerpage">Registeren</a></li>
					</ul>
					<% 
					}
					else {
					%>
					<%= session.getAttribute("Name") + " " + session.getAttribute("SurName") %> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/concordia/userinfo">Gegevens</a></li>
            			<li><a href="#">Reserveringen</a></li>
            			<li><a href="#">Expositie</a></li>
            			<li><a href="#">Abonnement</a></li>
            			<li class="divider"></li>
						<li><a href="/concordia/loguit">Log uit</a></li>

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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>