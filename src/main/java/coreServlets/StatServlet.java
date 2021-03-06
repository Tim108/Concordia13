package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StatServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession s = request.getSession();
		response.setContentType("text/html");
		
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		double customers;
		try {
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Customer;")) {
				rs.next();
				customers = rs.getInt(1);
				request.setAttribute("users", (int)customers);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Art;")) {
				rs.next();
				int count = rs.getInt(1);
				request.setAttribute("artpieces", count);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Rent;")) {
				rs.next();
				int count = rs.getInt(1);
				request.setAttribute("rented", count);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Reservation;")) {
				rs.next();
				int count = rs.getInt(1);
				request.setAttribute("reservations", count);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(DISTINCT customer) FROM pays_a;")) {
				rs.next();
				double count = rs.getInt(1);
				request.setAttribute("subscribers", (int)count);
				System.out.println(count);
				count = (count/customers)*100;
				System.out.println(count + "  " + customers);
				request.setAttribute("CusSub", Math.round(count));
			}
		} catch (SQLException e1) { e1.printStackTrace(); }
		request.getRequestDispatcher("/stats.jsp").forward(request, response);
	}
}
