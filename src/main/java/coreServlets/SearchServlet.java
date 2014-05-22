package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		srch = srch.toLowerCase();
		String[] srchterms = srch.split(" ");
		List<String> attributes = new ArrayList<String>();
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) { e1.printStackTrace(); }
		try (Connection conn = DriverManager.getConnection(
			"jdbc:postgresql://localhost:5432/Kunstuitleen",
			"postgres",
			"dude")) {
			int width = 99999999;
			int heigth = 99999999;
			for(int i=0; i<srchterms.length; i++) {
				if(srchterms[i].equals("by") || srchterms[i].equals("bij") || srchterms[i].equals("x")) {
					if(srchterms.length != i+1 && i-1 > 0) {
						width = Integer.parseInt(srchterms[i-1]);
						heigth = Integer.parseInt(srchterms[i+1]);
					}
				}
				try (PreparedStatement ps2 =
						conn.prepareStatement("SELECT a.name FROM art a,artpiece ap WHERE a.id=ap.id "
								+ "AND (LOWER(a.name) LIKE '%" + srchterms[i] + "%' "
								+ "OR LOWER(ap.artist) LIKE '%" + srchterms[i] + "%' "
								+ "OR LOWER(ap.technique) LIKE '%" + srchterms[i] + "%'"
								+ "OR LOWER(ap.style) LIKE '%" + srchterms[i] + "%'"
								+ "OR ( (ap.width)>'" + width + "'"
								+ "AND  (ap.height)>'" + heigth + "')"
								+ ");")){
					try (ResultSet rs = ps2.executeQuery()) {
						while ( rs.next() ) {
							attributes.add(rs.getString("name"));
						} 
					}
				}
			} 
			if(attributes.isEmpty()) {
				request.setAttribute("Error", "Niks gevonden!");
				request.getRequestDispatcher("/collectie.jsp").forward(request, response);
				return;
			}
			request.setAttribute("Search", attributes);
			request.getRequestDispatcher("/collectie.jsp").forward(request, response);
		} catch (SQLException e2) { e2.printStackTrace(); }
	}
}
