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
		
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		try {
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
			System.out.println("id = " + id);
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM subscription s, pays_a p "
									+ "WHERE p.customer= '"
									+ id
									+ "'"
									+ " AND s.id = p.subscription;")) {
				while (rs.next()) {
					allIDs.add(rs.getString("id"));
					allBeginDates.add(rs.getDate("startingdate"));
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
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.getRequestDispatcher("/userinfo.jsp").forward(request, response);
	}

	public String getInfo(String info, HttpServletRequest request,
			HttpServletResponse response, Connection conn, int id) {
		String foundInfo = "Niet gevonden!";
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
