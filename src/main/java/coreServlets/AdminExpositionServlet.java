package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminExpositionServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		
		List<String> userNames = new ArrayList<String>();
		List<Integer> artIDs = new ArrayList<Integer>();
		List<Integer> userIDs = new ArrayList<Integer>();
		List<Integer> userIDs2 = new ArrayList<Integer>();
		
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		try {
			try(PreparedStatement ps = conn.prepareStatement("SELECT external_picture, customer FROM uploaded;")) {
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						artIDs.add(rs.getInt("external_picture"));
						userIDs.add(rs.getInt("customer"));
					}
				}
			}
			try(PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT c.name, c.surname, u.customer FROM customer c, uploaded u WHERE c.id=u.customer;")) {
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						userNames.add(rs.getString("name") + " " + rs.getString("surname"));
						userIDs2.add(rs.getInt("customer"));
					}
				}
			}
			request.setAttribute("Names", userNames);
			request.setAttribute("Arts", artIDs);
			request.setAttribute("Users", userIDs);
			request.setAttribute("Users2", userIDs2);
			request.getRequestDispatcher("/adminexpositions.jsp").forward(request, response);
			return;
		} catch (SQLException e1) { e1.printStackTrace(); }
	}
}
