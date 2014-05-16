package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String email = (String) request.getParameter("email");
		String pass = (String) request.getParameter("password");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/Kunstuitleen", "postgres",
				"dude")) {
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT id,pass,name,surname FROM Customer WHERE email='"+ email + "';")) {
				if(!rs.next()) {
					response.sendRedirect("/concordia/login.jsp");
					return;
				}
				String savedPass = rs.getString("pass");
				
				if(!savedPass.equals(RegisterServlet.hashThis(pass))){
					System.out.println("NIET INLOGGUH!");
					response.sendRedirect("/concordia/login.jsp");
					return;
				}
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				HttpSession s = request.getSession();
				System.out.println(s.getMaxInactiveInterval() + " " + s.isNew() + " " + s.getLastAccessedTime());
				s.setAttribute("Logged", id);
				s.setAttribute("Name", name);
				s.setAttribute("SurName", surname);
				response.sendRedirect("");
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
