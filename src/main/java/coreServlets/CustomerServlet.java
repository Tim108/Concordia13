package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String letter = (String)request.getParameter("letter");
		
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
		List<String> name = new ArrayList<String>();
		List<String> surname = new ArrayList<String>();
		List<String> address = new ArrayList<String>();
		List<String> city = new ArrayList<String>();
		List<String> postal = new ArrayList<String>();
		List<String> tel = new ArrayList<String>();
		List<Double> credit = new ArrayList<Double>();
		List<Boolean> newsl = new ArrayList<Boolean>(); 
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customer c WHERE c.surname LIKE '" + letter + "%' ORDER BY c.surname")) {
				try(ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						name.add(rs.getString("name"));
						surname.add(rs.getString("surname"));
						address.add(rs.getString("address"));
						city.add(rs.getString("city"));
						postal.add(rs.getString("postal"));
						tel.add(rs.getString("phone"));
						credit.add(rs.getDouble("credit"));
						newsl.add(rs.getBoolean("newsletter"));
					}
				}
			}
			request.setAttribute("name", name);
			request.setAttribute("surname", surname);
			request.setAttribute("address", address);
			request.setAttribute("city", city);
			request.setAttribute("postal", postal);
			request.setAttribute("tel", tel);
			request.setAttribute("credit", credit);
			request.setAttribute("newsl", newsl);
			conn.close();
			request.getRequestDispatcher("klanten.jsp").forward(request, response);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
