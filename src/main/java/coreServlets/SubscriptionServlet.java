package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubscriptionServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession s = request.getSession();
		int id = (Integer) s.getAttribute("Logged");
		List<String> allIDs = new ArrayList<String>();
		List<Date> allBeginDates = new ArrayList<Date>();
		List<Date> allEndDates = new ArrayList<Date>();
		List<Boolean> allPremiums = new ArrayList<Boolean>();
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties prop = new Properties();
		String path = "res/dbprops.txt";
		try {
			prop.load(new FileInputStream(getServletContext().getRealPath(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String user = prop.getProperty("username");
		String pass1 = prop.getProperty("pass");
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String dbname = prop.getProperty("dbname");
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM subscription s, pays_a p "
									+ "WHERE p.customer= '"
									+ id
									+ "'"
									+ " AND s.id = p.subscription;")) {
				while (rs.next()) {
					allIDs.add(rs.getString("id"));
					allBeginDates.add(rs.getDate("startingdata"));
					allEndDates.add(rs.getDate("endingdate"));
					allPremiums.add(rs.getBoolean("premium"));
				}
				request.setAttribute("ids", allIDs);
				request.setAttribute("starts", allBeginDates);
				request.setAttribute("einds", allEndDates);
				request.setAttribute("premiums", allPremiums);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		request.getRequestDispatcher("/subscriptions.jsp").forward(request, response);
	}
}