package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
					PreparedStatement ps = conn.prepareStatement("SELECT id,pass,salt,name,surname,isadmin,activation FROM Customer WHERE email=?");
					ps.setString(1, email);
					ResultSet rs = ps.executeQuery();
				if(!rs.next()) {
					request.setAttribute("EmailError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Email of wachtwoord is verkeerd!");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
				String savedPass = rs.getString("pass");
				
				if(!savedPass.equals(RegisterServlet.hashThis(pass, rs.getBytes("salt")))){
					request.setAttribute("EmailError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Email of wachtwoord is verkeerd!");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String activation = rs.getString("activation");
				Map<Integer, String> expositions = new HashMap<Integer, String>();
				Map<Integer, Date> reservations = new HashMap<Integer, Date>();
				Map<Integer, List<Object>> rents = new HashMap<Integer, List<Object>>();
				
				HttpSession s = request.getSession();
				try(PreparedStatement ps2 = conn.prepareStatement("SELECT h.collection, c.name, r.artpiece, r.startingdate, g.artpiece AS rentpiece, g.startingdate, g.endingdate, g.deliver FROM rent g, reservation r, has h, Collection c WHERE h.customer = ? AND h.customer = r.customer AND h.customer = g.customer AND c.id = h.collection;")) {
					ps2.setInt(1, id);
					try(ResultSet rs2 = ps2.executeQuery()) {
						while(rs2.next()) {
							expositions.put(rs2.getInt("collection"),rs2.getString("name"));
							reservations.put(rs2.getInt("artpiece"),rs2.getDate("startingdate"));
							List<Object> rentinfo = new ArrayList<Object>();
							rentinfo.add(0, rs2.getInt("startingdate"));
							rentinfo.add(1, rs2.getInt("endingdate"));
							rentinfo.add(2, rs2.getInt("deliver"));
							rents.put(rs2.getInt("rentpiece"), rentinfo);
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
				s.setAttribute("Reservations", reservations);
				s.setAttribute("Rents", rents);
				response.sendRedirect("");
				return;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
