package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReservationServlet extends HttpServlet {
	private int reservations = 2;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int paintingid = Integer.parseInt(request.getParameter("id"));
		Connection conn = (Connection) getServletContext().getAttribute(
				"DBConnection");
		try {
			try (Statement stmt = conn.createStatement();
					PreparedStatement ps = conn
							.prepareStatement("SELECT COUNT(subscription) FROM pays_a WHERE customer=?;")) {
				ps.setInt(1, (int) request.getSession().getAttribute("Logged"));
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					reservations = reservations + rs.getInt("count");
				}
				System.out.println(reservations);
			}
		} catch (Exception e) {

		}
	}
}
