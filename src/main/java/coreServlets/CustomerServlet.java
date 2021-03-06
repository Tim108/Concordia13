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

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String letter = request.getParameter("letter");
		String words[] = null;
		if(letter == null) {
				letter = request.getParameter("srch-term");
				if(letter == null) letter = "";
				words = letter.split(" ");
		}
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		List<String> name = new ArrayList<String>();
		List<String> surname = new ArrayList<String>();
		List<String> address = new ArrayList<String>();
		List<String> city = new ArrayList<String>();
		List<String> postal = new ArrayList<String>();
		List<String> tel = new ArrayList<String>();
		List<Double> credit = new ArrayList<Double>();
		List<Boolean> newsl = new ArrayList<Boolean>(); 
		List<String> email = new ArrayList<String>();
		List<Integer> id = new ArrayList<Integer>();
		List<Integer> subs = new ArrayList<Integer>();
		try {
			if(words == null) {
				try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customer c WHERE c.surname LIKE '" + letter + "%' ORDER BY c.surname")) {
					try(ResultSet rs = ps.executeQuery()) {
						while(rs.next()) {
							id.add(rs.getInt("id"));
							name.add(rs.getString("name"));
							surname.add(rs.getString("surname"));
							address.add(rs.getString("address"));
							city.add(rs.getString("city"));
							postal.add(rs.getString("postal"));
							tel.add(rs.getString("phone"));
							credit.add(rs.getDouble("credit"));
							newsl.add(rs.getBoolean("newsletter"));
							email.add(rs.getString("email"));
							try (PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM pays_a p WHERE p.customer = " + id.get(id.size()-1))) {
								try(ResultSet rs2 = ps2.executeQuery()) {
									if(rs2.next()) {
										subs.add(rs2.getInt("count"));
									}
								}
							}
						}
					}
				}
			}
			else {
				for(int i=0; i<words.length; i++) {
					try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customer c WHERE c.surname LIKE '" + words[i] + 
																		"%' OR c.name LIKE '" + words[i] + 
																		"%' ORDER BY c.surname")) {
						try(ResultSet rs = ps.executeQuery()) {
							while(rs.next()) {
								if(!id.contains(rs.getInt("id"))) {
									id.add(rs.getInt("id"));
									name.add(rs.getString("name"));
									surname.add(rs.getString("surname"));
									address.add(rs.getString("address"));
									city.add(rs.getString("city"));
									postal.add(rs.getString("postal"));
									tel.add(rs.getString("phone"));
									credit.add(rs.getDouble("credit"));
									newsl.add(rs.getBoolean("newsletter"));
									email.add(rs.getString("email"));
									try (PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM pays_a p WHERE p.customer = " + id.get(id.size()-1))) {
										try(ResultSet rs2 = ps2.executeQuery()) {
											if(rs2.next()) {
												subs.add(rs2.getInt("count"));
											}
										}
									}
								}
							}
						}
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
			request.setAttribute("subs", subs);
			request.setAttribute("email", email);
			request.getRequestDispatcher("klanten.jsp").forward(request, response);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
