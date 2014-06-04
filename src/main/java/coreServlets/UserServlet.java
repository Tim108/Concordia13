package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserServlet extends HttpServlet {
	List<String> allIDs = new ArrayList<String>();
	List<Date> allBeginDates = new ArrayList<Date>();
	List<Date> allEndDates = new ArrayList<Date>();
	List<Boolean> allPremiums = new ArrayList<Boolean>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession s = request.getSession();
		int id = (Integer) s.getAttribute("Logged");
		response.setContentType("text/html");
		Properties prop = new Properties();
		String path = "res/dbprops.txt";
		try {
			prop.load(new FileInputStream(getServletContext().getRealPath(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String user = prop.getProperty("username");
		String pass1 = prop.getProperty("pass");
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String dbname = prop.getProperty("dbname");
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			String name = getInfo("name", request, response, conn, id);
			request.setAttribute("Naam", name);

			String surname = getInfo("surname", request, response, conn, id);
			request.setAttribute("Achternaam", surname);

			String address = getInfo("address", request, response, conn, id);
			request.setAttribute("Adres", address);

			String city = getInfo("city", request, response, conn, id);
			request.setAttribute("Woonplaats", city);

			String postal = getInfo("postal", request, response, conn, id);
			request.setAttribute("Postcode", postal);

			String email = getInfo("email", request, response, conn, id);
			request.setAttribute("Email", email);

			String phone = getInfo("phone", request, response, conn, id);
			request.setAttribute("Telefoon", phone);

			String credit = getInfo("credit", request, response, conn, id);
			request.setAttribute("Tegoed", credit);

			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM subscription s, pays_a p "
									+ "WHERE p.customer= '"
									+ id
									+ "'"
									+ " AND s.id = p.subscription;")) {
				while (rs.next()) {
					allIDs.add(rs.getString("id"));
					allBeginDates.add(rs.getDate("startingdata"));
					allEndDates.add(rs.getDate("endingdate"));
					allPremiums.add(rs.getBoolean("premium"));
				}
				request.setAttribute("ids", allIDs);
				request.setAttribute("starts", allBeginDates);
				request.setAttribute("einds", allEndDates);
				request.setAttribute("premiums", allPremiums);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.getRequestDispatcher("/userinfo.jsp").forward(request, response);
	}

	public String getInfo(String info, HttpServletRequest request,
			HttpServletResponse response, Connection conn, int id) {
		String foundInfo = "Niet gevonden!";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT " + info
						+ " FROM Customer WHERE id='" + id + "';")) {
			if (!rs.next()) {
				return foundInfo;
			}
			foundInfo = rs.getString(info);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundInfo;
	}
}
