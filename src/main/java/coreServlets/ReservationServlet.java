package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReservationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { doGet(request, response); }
		
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession s = request.getSession();
		int userID = (Integer)s.getAttribute("Logged");
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		List<String> sources = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		List<Integer> ids = new ArrayList<Integer>();
		try{
			try(PreparedStatement ps = conn.prepareStatement("SELECT a.id, a.source, r.startingdate FROM art a, reservation r WHERE a.id = r.artpiece AND r.customer = ?")) {
				ps.setInt(1, userID);
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						ids.add(rs.getInt("id"));
						sources.add(rs.getString("source"));
						dates.add(rs.getDate("startingdate"));
					}
				}
			}
			if(ids.isEmpty()) {
				request.setAttribute("Error", "U hebt nog geen reservaties!");
			}
			else {
				request.setAttribute("Sources", sources);
				request.setAttribute("Dates", dates);
				request.setAttribute("IDs",ids);
			}
			request.getRequestDispatcher("/reservations.jsp").forward(request, response);
		} 
		catch (SQLException e) { e.printStackTrace(); }
	}
}
