<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>Zoeken</title>
	<%@include file="main.jsp"%>
	<div class="head">
		<form class="navbar-form" method="POST" action="/concordia/search">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search"
					name="srch-term" id="srch-term">
				<div class="input-group-btn">
					<button class="btn btn-default" type="submit">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>