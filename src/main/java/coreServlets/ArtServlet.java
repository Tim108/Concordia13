package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ArtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String title = (String)request.getAttribute("title");
		String artist = (String)request.getAttribute("artist");
		String width = (String)request.getAttribute("width");
		String heigth = (String)request.getAttribute("heigth");
		String technique = (String)request.getAttribute("technique");
		String orientation = (String)request.getAttribute("orientation");
		String file = (String)request.getAttribute("file");

		System.out.println(file);
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
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
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Art (name, source) VALUES(?,?);")){
				ps.setString(1, title);
				ps.setString(2, file);
			}
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Artpiece (artist, heigth, width, style, technique, orientation, price, rating) VALUES (????????);")) {
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
