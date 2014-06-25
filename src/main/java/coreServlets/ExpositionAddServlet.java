package coreServlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import rest.User;

public class ExpositionAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		int id = 0;
		if(request.getParameter("id") != null)
			id = Integer.parseInt((String)request.getParameter("id"));
		else
			id = (Integer)request.getAttribute("id");
		int expo = 0;
		String expoName = "Expositie";
		try {
			expo = Integer.parseInt(request.getParameter("expo"));
		}
		catch (NumberFormatException e) {
			expoName = request.getParameter("expo");
		}
		HttpSession s = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		int collID = 0;
		int userID = (int)s.getAttribute("Logged");
		Map<Integer, String> expositions = (Map<Integer, String>)s.getAttribute("Expositions");
		try {
			if(expo == 0) {
				try(PreparedStatement ps = conn.prepareStatement("INSERT INTO Collection (name) VALUES (?) RETURNING id;")) {
					ps.setString(1, expoName);
					try(ResultSet rs = ps.executeQuery()){
						if(rs.next()){
						   collID = rs.getInt("id");
						   expositions.put(collID, expoName);
						}
					}
				}
				try (PreparedStatement ps = conn.prepareStatement("INSERT INTO has (customer, collection) VALUES(?,?);")){
					ps.setInt(1, userID);
					ps.setInt(2, collID);
					ps.execute();
				}
			}
			else collID = expo;
			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM belongs_to WHERE art = ? AND collection = ?")) 
			{
				ps.setInt(1, id);
				ps.setInt(2, collID);
				try(ResultSet rs = ps.executeQuery()) {
					if(!rs.next())  {
						try (PreparedStatement ps2 = conn.prepareStatement("INSERT INTO belongs_to (art, collection) VALUES(?,?);")) {
							ps2.setInt(1, id);
							ps2.setInt(2, collID);
							ps2.execute();
						}
					}
				}
			}
			response.sendRedirect("/concordia/expositie?id=" + collID);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
