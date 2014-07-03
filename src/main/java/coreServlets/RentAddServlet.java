package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RentAddServlet extends HttpServlet {
	private int rentAmount = 0;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int myReservations = 0;
		int check = 0;
		HttpSession s = request.getSession();
		int paintingid = Integer.parseInt(request.getParameter("id"));
		Map<Integer, List<Object>> rents = (Map<Integer, List<Object>>) s.getAttribute("Rents");
		System.out.println("First rents: " + rents);
		
		if(s.getAttribute("Logged")==null) {
			response.sendRedirect(request.getContextPath() + "/loginpage");
		}
		Calendar cal = Calendar.getInstance();
		java.sql.Date sqlnow = new java.sql.Date(cal.getTime().getTime());
		
		cal.add(Calendar.MONTH, 6);
		java.sql.Date sqlexp = new java.sql.Date(cal.getTime().getTime());
		
		Connection conn = (Connection) getServletContext().getAttribute(
				"DBConnection");
		try {
			try (Statement stmt = conn.createStatement();
					PreparedStatement ps = conn
							.prepareStatement("SELECT COUNT(p.subscription) AS subcount FROM pays_a p WHERE p.customer=?")) {
				ps.setInt(1, (int) request.getSession().getAttribute("Logged"));
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					rentAmount = (rs.getInt("subcount") - rents.size());
					System.out.println("Check: " + rentAmount);
					if (rentAmount > 0) {
						System.out.println("Check > 0");
						if (!rents.containsKey(paintingid)) {	
							PreparedStatement addRent = conn
									.prepareStatement("INSERT INTO rent(artpiece, customer, startingdate, endingdate, deliver) VALUES (?,?,?,?,?)");
							addRent.setInt(1, paintingid);
							addRent.setInt(2, (int) s.getAttribute("Logged"));
							addRent.setDate(3, sqlnow);
							addRent.setDate(4, sqlexp);
							addRent.setBoolean(5, false);
							System.out.println(addRent.toString());
							int updateCount = addRent.executeUpdate();
							System.out.println("updateCount: " + updateCount);
							if (updateCount > 0) {
								System.out.println("updateCount > 0");
								List<Object> rentinfo = new ArrayList<Object>();
								rentinfo.add(0, sqlnow);
								rentinfo.add(1, sqlexp);
								rentinfo.add(2, false);
								rents.put(paintingid, rentinfo);
							}
							response.sendRedirect(request.getContextPath() + "/gehuurdewerken");
						} else {
							System.out.println("staat al in gehuurde werken");
						}
					} else {
						System.out.println("geen subs left");
						s.setAttribute("nosubs", "yes");
						response.sendRedirect(request.getHeader("Referer"));
					}
				System.out.println(rents);
				 
					s.setAttribute("Rents", rents);
					System.out.println("Second rents: "
							+ s.getAttribute("Rents"));
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}

