package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	
		String srch = (String)request.getParameter("srch-term");
		if(srch.equals("")) {
			request.getRequestDispatcher("/collectie.jsp").forward(request, response);
			return;
		}
		String[] srchterms = srch.split(" ");
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) { e1.printStackTrace(); }
		try (Connection conn = DriverManager.getConnection(
			"jdbc:postgresql://localhost:5432/Kunstuitleen",
			"postgres",
			"dude")) {
			for(int i=0; i<srchterms.length; i++) {
				try (PreparedStatement ps2 =
						conn.prepareStatement("SELECT * FROM Art WHERE name LIKE '%"+ srchterms[i] + "%';")){
					try (ResultSet rs = ps2.executeQuery()) {
						while ( rs.next() ) {
							request.setAttribute("Search", rs.getString("name"));
							request.getRequestDispatcher("/collectie.jsp").forward(request, response);
						} 
					}
				}
			} 
		} catch (SQLException e2) { e2.printStackTrace(); }
	}
}
