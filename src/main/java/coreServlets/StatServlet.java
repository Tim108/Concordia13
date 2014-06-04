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
		Properties prop = new Properties();
		String path = "res/dbprops.txt";
		prop.load(new FileInputStream(getServletContext().getRealPath(path)));
		String user = prop.getProperty("username");
		String pass1 = prop.getProperty("pass");
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String dbname = prop.getProperty("dbname");
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Customer;")) {
				int count = rs.getInt(0);
				System.out.println(count);
				request.setAttribute("users", count);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Art;")) {
				int count = rs.getInt(0);
				System.out.println(count);
				request.setAttribute("users", count);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Rent;")) {
				int count = rs.getInt(0);
				System.out.println(count);
				request.setAttribute("users", count);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Reservation;")) {
				int count = rs.getInt(0);
				System.out.println(count);
				request.setAttribute("users", count);
			}
			try (Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT COUNT(DISTINCT customer) FROM pays_a;")) {
				int count = rs.getInt(0);
				System.out.println(count);
				request.setAttribute("users", count);
			}
		} catch (SQLException e1) { e1.printStackTrace(); }
		request.getRequestDispatcher("/stats.jsp").forward(request, response);
	}
}
