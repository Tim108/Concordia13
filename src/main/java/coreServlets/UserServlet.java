package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String name = getInfo("name", request, response);
		request.setAttribute("Naam", name);
		
		String surname = getInfo("surname", request, response);
		request.setAttribute("Achternaam", surname);
		
		String address = getInfo("address", request, response);
		request.setAttribute("Adres", address);
		
		String city = getInfo("city", request, response);
		request.setAttribute("Woonplaats", city);
		
		String postal = getInfo("postal", request, response);
		request.setAttribute("Postcode", postal);
		
		String email = getInfo("email", request, response);
		request.setAttribute("Email", email);
		
		String phone = getInfo("phone", request, response);
		request.setAttribute("Telefoon", phone);
		
		request.getRequestDispatcher("/userinfo.jsp").forward(request, response);
	}
	
	public String getInfo(String info, HttpServletRequest request, HttpServletResponse response){
		HttpSession s = request.getSession();
		int id = (Integer)s.getAttribute("Logged");
		String foundInfo = "Niet gevonden!";
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
							.executeQuery("SELECT " + info + " FROM Customer WHERE id='"+ id + "';")) {
				if(!rs.next()) {
					return foundInfo;
				}
				 foundInfo = rs.getString(info);
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return foundInfo;
	}
}

