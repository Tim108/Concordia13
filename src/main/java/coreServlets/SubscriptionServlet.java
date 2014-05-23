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

public class SubscriptionServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String id = getInfo("id", request, response);
		request.setAttribute("id", id);
		
		String startDate = getInfo("startingdata", request, response);
		request.setAttribute("startDate", startDate);
		
		String endDate = getInfo("endingdate", request, response);
		request.setAttribute("endDate", endDate);
		
		String premium = getInfo("premium", request, response);
		request.setAttribute("premium", premium);
		request.getRequestDispatcher("/subscriptions.jsp").forward(request, response);
	}
	
	public String getInfo(String info, HttpServletRequest request, HttpServletResponse response){
		HttpSession s = request.getSession();
		int id = (Integer)s.getAttribute("Logged");
		String foundInfo = "Niet gevonden!";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties prop = new Properties();
		 String path = "res/dbprops.txt";
		 try{
		 prop.load(new FileInputStream(getServletContext().getRealPath(path)));}catch(Exception e){e.printStackTrace();}
		 String user = prop.getProperty("username");
		 String pass1 = prop.getProperty("pass");
		 String host = prop.getProperty("host");
		 String port = prop.getProperty("port");
		 String dbname = prop.getProperty("dbname");
		 String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT " + info + " FROM subscription s, pays_a p "+ 
									 "WHERE p.customer=' "+ id + 
									 "WHERE s.id = p.subscription';")) {
				if(!rs.next()) {
					return foundInfo;
				}
				 foundInfo = rs.getString(info);
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return foundInfo;
	}
}
