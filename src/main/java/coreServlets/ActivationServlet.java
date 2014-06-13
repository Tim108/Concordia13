package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActivationServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession s = request.getSession();
		String dbactivation = "";
		String activation = request.getParameter("link");
		Integer account = (Integer) s.getAttribute("Logged");
		response.setContentType("text/html");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties prop = new Properties();
		String path = "res/dbprops.txt";
		try {
			prop.load(new FileInputStream(getServletContext().getRealPath(path)));
		} catch (Exception inputerror) {
			inputerror.printStackTrace();
		}
		String user = prop.getProperty("username");
		String pass1 = prop.getProperty("pass");
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String dbname = prop.getProperty("dbname");
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			if(account==null) {
				account= Integer.parseInt(request.getParameter("id"));
			}
			try (PreparedStatement ps2 = conn
					.prepareStatement("SELECT activation FROM Customer WHERE id=?;")) {
				ps2.setInt(1, account);
				try (ResultSet rs = ps2.executeQuery()) {
					while (rs.next()) {
						dbactivation = rs.getString("activation");
					}
				}
			}
			if (dbactivation.equals("activated")) {
				s.setAttribute("activated", "alreadyactivated");
			}
			else if (activation.equals(dbactivation)) {
				try (PreparedStatement ps = conn
						.prepareStatement("UPDATE customer SET activation='activated' WHERE id =?")) {
					ps.setInt(1, account);
					try {
						ps.execute();
						s.setAttribute("activated", "activated");
						System.out.println("update done");
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					response.sendRedirect("/concordia/activation.jsp");
				} catch (SQLException | IOException e) {
					e.printStackTrace();
					s.setAttribute("activated", "notactivated");
					response.sendRedirect("/concordia/activation.jsp");
				}
			} else {
				s.setAttribute("activated", "wrongCode");
				response.sendRedirect("/concordia/activation.jsp");
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("activation key: " + activation + " || dbactivation key:  "+ dbactivation);
	}

}