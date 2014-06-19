package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExpositionRemoveServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		int id = Integer.parseInt((String)request.getParameter("id"));
		
		HttpSession s = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		int collID = (int)s.getAttribute("hasExposition");
		try {
			try (PreparedStatement ps = conn.prepareStatement("DELETE FROM belongs_to WHERE art = ? AND collection = ?")) 
			{
				ps.setInt(1, id);
				ps.setInt(2, collID);
				if(ps.execute()) {
				}
			}
			response.sendRedirect("/concordia/expositie?id=" + collID);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}