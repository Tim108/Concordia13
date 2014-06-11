package coreServlets;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		int id = Integer.parseInt((String)request.getParameter("removing"));
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties prop = new Properties();
		 String path2 = "res/dbprops.txt";
		 prop.load(new FileInputStream(getServletContext().getRealPath(path2)));
		 String user = prop.getProperty("username");
		 String pass1 = prop.getProperty("pass");
		 String host = prop.getProperty("host");
		 String port = prop.getProperty("port");
		 String dbname = prop.getProperty("dbname");
		 String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Art WHERE id=?;")) {
				ps.setInt(1, id);
				if(ps.execute()) {
					System.out.println("Query Executed!");
				}
			}
			conn.close();
			response.sendRedirect("search");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
