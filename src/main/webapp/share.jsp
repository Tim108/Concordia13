<% String source = request.getParameter("source"); %>
<div class="btn-group dropup">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"> Delen <span class="caret"></span></button>
	<ul class="dropdown-menu pull-right" role="menu">
	<table>
		<tr>
		<td style="padding-right: 10px; padding-bottom: 10px"><a
			href="http://www.facebook.com/sharer.php?u=http://datainfo.ewi.utwente.nl<%=request.getContextPath()%>/img/<%=source%>"
			target="_blank"><img
			src="http://www.simplesharebuttons.com/images/somacro/facebook.png"
			alt="Facebook" style="height: 50px;" /></a>
		</td>
		<td style="padding-bottom: 10px;"><a
			href="http://twitter.com/share?url=http://datainfo.ewi.utwente.nl<%=request.getContextPath()%>/img/<%=source%>&text=Geweldig werk gezien bij concordia! // via @Concordia053"
			target="_blank"><img
			src="http://www.simplesharebuttons.com/images/somacro/twitter.png"
			alt="Twitter" style="height: 50px;" /></a>
		</td>
		</tr>
		<tr>
		<td style="padding-right: 10px;"><a
			href="https://plus.google.com/share?url=http://datainfo.ewi.utwente.nl<%=request.getContextPath()%>/img/<%=source%>"
			target="_blank"> <img
			src="http://www.simplesharebuttons.com/images/somacro/google.png"
			alt="Google" style="height: 50px;" /></a>
		</td>
		<td><a
			href="mailto:?Subject=Bekijk dit kunstwerk bij Concordia kunstuitleen!&Body=I%20saw%20this%20and%20thought%20of%20you!%20 http://datainfo.ewi.utwente.nl<%=request.getContextPath()%>/img/<%=source%>"><img
			src="http://www.simplesharebuttons.com/images/somacro/email.png"
			alt="Email" style="height: 50px;" /></a>
		</td>
		</tr>
	</table>
	</ul>
</div>