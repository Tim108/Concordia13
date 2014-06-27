package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Map;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReservationServlet extends HttpServlet {
	private int reservationAmount = 2;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int myReservations = 0;
		int check = 0;
		HttpSession s = request.getSession();
		int paintingid = Integer.parseInt(request.getParameter("id"));
		Map<Integer, Date> reservations = (Map<Integer, Date>) s
				.getAttribute("Reservations");
		System.out.println("First reservations: " + reservations);

		Calendar cal = Calendar.getInstance();
		java.sql.Date sqlnow = new java.sql.Date(cal.getTime().getTime());

		Connection conn = (Connection) getServletContext().getAttribute(
				"DBConnection");
		try {
			try (Statement stmt = conn.createStatement();
					PreparedStatement ps = conn
							.prepareStatement("SELECT COUNT(p.subscription) AS subcount FROM pays_a p WHERE p.customer=?")) {
				ps.setInt(1, (int) request.getSession().getAttribute("Logged"));
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					reservationAmount = (reservationAmount + rs
							.getInt("subcount"));
					myReservations = reservations.size();
					check = (reservationAmount - myReservations);
					System.out.println("Check: " + check);
					if (check > 0) {
						if (!reservations.containsKey(paintingid)) {
							System.out.println("Check > 0");
							PreparedStatement addRes = conn
									.prepareStatement("INSERT INTO reservation(artpiece, customer, startingdate) VALUES (?,?,?)");
							addRes.setInt(1, paintingid);
							addRes.setInt(2, (int) s.getAttribute("Logged"));
							addRes.setDate(3, sqlnow);
							System.out.println(addRes.toString());
							int updateCount = addRes.executeUpdate();
							System.out.println("updateCount: " + updateCount);
							if (updateCount > 0) {
								System.out.println("updateCount > 0");
								reservations.put(paintingid, sqlnow);
							}
						} else {
							System.out.println("staat al in reservering");
						}
					} else {
						System.out.println("geen subs left");
					}
				System.out.println(reservations);
				 
					s.setAttribute("Reservations", reservations);
					System.out.println("Second reservations: "
							+ s.getAttribute("Reservations"));
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}

