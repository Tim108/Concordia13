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
		int idint = (Integer) s.getAttribute("Logged");
		int count = 0;
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
									+ idint
									+ "'"
									+ " AND s.id = p.subscription;")) {
				while (rs.next()) {
					request.setAttribute("id" + "" +Integer.toString(count), rs.getString("id"));
					request.setAttribute("start" + "" +Integer.toString(count), rs.getDate("startingdata"));
					request.setAttribute("eind" + "" +Integer.toString(count), rs.getDate("endingdate"));
					request.setAttribute("premium" + "" +Integer.toString(count), rs.getBoolean("premium"));
					System.out.println("id"+ Integer.toString(count));
					count++;
					request.setAttribute("rowcount", count);
				}
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