package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ReservationRemoveServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession s = request.getSession();
		
		int artID = Integer.parseInt(request.getParameter("id"));
		
		int userID = (Integer)s.getAttribute("Logged");
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		List<String> sources = new ArrayList<String>();
		List<Date> dates = new ArrayList<Date>();
		List<Integer> ids = new ArrayList<Integer>();
		try{
			try(PreparedStatement ps = conn.prepareStatement("DELETE FROM reservation WHERE artpiece = ? AND customer = ?;")) {
				ps.setInt(1, artID);
				ps.setInt(2, userID);
				((Map<Integer, java.sql.Date>)request.getSession().getAttribute("Reservations")).remove(artID);
				ps.execute();
			}
			request.setAttribute("Succes", "U hebt de reservatie succesvol verwijderd!");
			request.getRequestDispatcher("/reservations").forward(request, response);
		} 
		catch (SQLException e) { e.printStackTrace(); }
	}
}
