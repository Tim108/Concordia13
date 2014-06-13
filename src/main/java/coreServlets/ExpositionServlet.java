package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExpositionServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession s = request.getSession();
		int id = 0;
		if(request.getParameter("id") == null) {
			id = (int)s.getAttribute("hasExposition");
		}
		else {
			id = Integer.parseInt(request.getParameter("id"));
		}
		
		if(id == 0) {
			request.setAttribute("Error", "U hebt nog geen eigen Expositie! <a href=\"/concordia/search\">Maak hem hier!<//a>");
			request.getRequestDispatcher("/expositie.jsp").forward(request, response);
			return;
		}
		
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		List<Integer> arts = new ArrayList<Integer>();
		int userID = 0;
		List<String> names = new ArrayList<String>();
		try{
			try(PreparedStatement ps = conn.prepareStatement("SELECT customer FROM has WHERE collection = ?")) {
				ps.setInt(1, id);
				try(ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
						userID = rs.getInt("customer");
					}
					else {
						request.setAttribute("Error", "Deze expositie bestaat nog niet!");
					}
				}
			}
			try(PreparedStatement ps = conn.prepareStatement("SELECT art FROM belongs_to WHERE collection = ?")) {
				ps.setInt(1, id);
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						arts.add(rs.getInt("art"));
					}
				}
			}
			try(PreparedStatement ps = conn.prepareStatement("SELECT c.name, u.name, u.surname FROM Collection c, Customer u WHERE c.id = ? AND u.id = ?")) {
				ps.setInt(1, id);
				ps.setInt(2, userID);
				try(ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
						names.add(rs.getString(1));
						names.add(rs.getString(2));
						names.add(rs.getString(3));
					}
				}
			}
			request.setAttribute("Arts", arts);
			request.setAttribute("Names", names);
			request.getRequestDispatcher("/expositie.jsp").forward(request, response);
		} 
		catch (SQLException e) { e.printStackTrace(); }
	}
}
