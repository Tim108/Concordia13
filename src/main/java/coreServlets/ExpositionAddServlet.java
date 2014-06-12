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
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class ExpositionAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		int id = Integer.parseInt((String)request.getParameter("id"));
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		HttpSession s = request.getSession();
		Properties prop = new Properties();
		 String path2 = "res/dbprops.txt";
		 prop.load(new FileInputStream(getServletContext().getRealPath(path2)));
		 String user = prop.getProperty("username");
		 String pass1 = prop.getProperty("pass");
		 String host = prop.getProperty("host");
		 String port = prop.getProperty("port");
		 String dbname = prop.getProperty("dbname");
		 String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		int collID = 0;
		int userID = (int)s.getAttribute("Logged");
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			if(s.getAttribute("hasExposition") == null) {
				try(PreparedStatement ps = conn.prepareStatement("INSERT INTO Collection (name) VALUES (?) RETURNING id;")) {
					ps.setString(1, "Expositie");
					try(ResultSet rs = ps.executeQuery()){
						if(rs.next()){
						   collID = rs.getInt("id");
						   s.setAttribute("CollID", collID);
						}
					}
				}
				try (PreparedStatement ps = conn.prepareStatement("INSERT INTO has (customer, collection) VALUES(?,?);")){
					ps.setInt(1, userID);
					ps.setInt(2, collID);
					if(ps.execute()) {
					}
				}
			}
			else collID = (int)s.getAttribute("hasExposition");
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO belongs_to (art, collection) VALUES(?,?);")){
				ps.setInt(1, id);
				ps.setInt(2, collID);
				if(ps.execute()) {
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
