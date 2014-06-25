package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rest.User;

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

		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		try{
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT id,pass,salt,name,surname,isadmin,activation FROM Customer WHERE email='"+ email + "';")) {
				if(!rs.next()) {
					request.setAttribute("E-mailError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Account niet gevonden!");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
				String savedPass = rs.getString("pass");
				
				if(!savedPass.equals(RegisterServlet.hashThis(pass, rs.getBytes("salt")))){
					request.setAttribute("PasswordError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Verkeerd wachtwoord!");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String activation = rs.getString("activation");
				Map<Integer, String> expositions = new HashMap<Integer, String>();
				
				HttpSession s = request.getSession();
				try(PreparedStatement ps = conn.prepareStatement("SELECT h.collection, c.name FROM has h, Collection c WHERE h.customer = ? AND c.id = h.collection;")) {
					ps.setInt(1, id);
					try(ResultSet rs2 = ps.executeQuery()) {
						while(rs2.next()) {
							expositions.put(rs2.getInt("collection"),rs2.getString("name"));
						}
					}
				}
				System.out.println("LoginServlet.js: " + s.getMaxInactiveInterval() + " " + s.isNew() + " " + s.getLastAccessedTime());
				s.setAttribute("Logged", id);
				s.setAttribute("account", activation);
				s.setAttribute("isAdmin", rs.getBoolean("isAdmin"));
				s.setAttribute("Name", name);
				s.setAttribute("SurName", surname);
				s.setAttribute("Expositions", expositions);
				response.sendRedirect("");
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
