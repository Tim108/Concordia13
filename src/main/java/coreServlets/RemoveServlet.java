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
		
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		try {
			try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Art WHERE id=?;")) {
				ps.setInt(1, id);
				if(ps.execute()) {
					System.out.println("Query Executed!");
				}
			}
			response.sendRedirect("search");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
