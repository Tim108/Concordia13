package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubscriptionServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datums nog even netjes doen via attributes via GET ipv in servlet
		response.setContentType("text/html");
		HttpSession s = request.getSession();
		
		Calendar cal = Calendar.getInstance();
		java.sql.Date sqlnow = new java.sql.Date(cal.getTime().getTime());
		
		cal.add(Calendar.MONTH, 6);
		java.sql.Date sqlexp = new java.sql.Date(cal.getTime().getTime());
		
		int id = (Integer) s.getAttribute("Logged");
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		int addedID = 0;
		boolean premium = false;
		boolean done = false;
		try{
		if (request.getParameter("ideal")!=null) {
			if (request.getParameter("ideal").equals("spaar")) {
				premium = true;
			}
				try( PreparedStatement ps = conn.prepareStatement("INSERT INTO subscription (startingdate, endingdate, premium)"
				    		+ " VALUES(?,?,?) RETURNING id;")
				    		){
					 ps.setDate(1, sqlnow);
					   ps.setDate(2, sqlexp);
					   ps.setBoolean(3, premium);
					   try(ResultSet rs = ps.executeQuery()){
						   if(rs.next()){
							   addedID = rs.getInt("id");
						   }
					  }
					   
					   try( PreparedStatement custSub = conn.prepareStatement("INSERT INTO pays_a (customer, subscription)"
					    		+ " VALUES((SELECT id FROM customer WHERE id=?), (SELECT id FROM subscription WHERE id=?));")
					    		){
						 custSub.setInt(1, id);
						   custSub.setInt(2, addedID);
						   custSub.execute();
						   done = true;
						   }
				 } catch (Exception e) { 
					 e.printStackTrace();
					 done = false;
				 }
			
			
		 }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("done", done);
		request.setAttribute("ideal", request.getParameter("ideal"));
		request.getRequestDispatcher("/subscriptions.jsp").forward(request,
				response);
	}
}