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

public class SearchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<String> artistL = new ArrayList<String>();
	public List<String> styleL = new ArrayList<String>();
	public List<String> techL = new ArrayList<String>();
	public List<String> orientL = new ArrayList<String>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
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
			createLists(conn, request);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("/collectie.jsp").forward(request,
				response);
	}

	private void printList(List list) {
		System.out.println("list");
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Element " + i + ": " + list.get(i));
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String srch = request.getParameter("srch-term");
		System.out.println(srch);
		if (srch == null)
			srch = "";
		srch = srch.toLowerCase();

		String[] srchterms = srch.split(" ");
		double[] prices = new double[2];
		List<String> artists = new ArrayList<String>();
		double[] sizes = new double[4];
		List<String> styles = new ArrayList<String>();
		List<String> techs = new ArrayList<String>();
		List<String> orients = new ArrayList<String>();
		double[] ratings = new double[2];

		// prices
		prices[0] = Double.parseDouble(request.getParameter("minPrice"));
		prices[1] = Double.parseDouble(request.getParameter("maxPrice"));
		// sizes
		sizes[0] = Double.parseDouble(request.getParameter("minBred"));
		sizes[1] = Double.parseDouble(request.getParameter("maxBred"));
		sizes[2] = Double.parseDouble(request.getParameter("minHoog"));
		sizes[3] = Double.parseDouble(request.getParameter("maxHoog"));
		// ratings
		ratings[0] = Double.parseDouble(request.getParameter("minRat"));
		ratings[1] = Double.parseDouble(request.getParameter("maxRat"));
		// artists

		for (int i = 0; i < artistL.size(); i++) {
			String artist = artistL.get(i);
			String param = request.getParameter(artist);
			boolean isChecked = (param != null) && param.equals("on");
			if (isChecked) {
				artists.add(artist);
			}
		}

		// styles
		for (int i = 0; i < styleL.size(); i++) {
			String style = styleL.get(i);
			String param = request.getParameter(style);
			boolean isChecked = (param != null) && param.equals("on");
			if (isChecked)
				styles.add(style);
		}

		// techs
		for (int i = 0; i < techL.size(); i++) {
			String tech = techL.get(i);
			String param = request.getParameter(tech);
			boolean isChecked = (param != null) && param.equals("on");
			if (isChecked)
				techs.add(tech);
		}

		// orients
		for (int i = 0; i < orientL.size(); i++) {
			String orient = orientL.get(i);
			String param = request.getParameter(orient);
			boolean isChecked = (param != null) && param.equals("on");
			if (isChecked)
				orients.add(orient);
		}

		List<String> attributes = new ArrayList<String>();

		// sql inloggen
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
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
		// --

		System.out.println("1");
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			createLists(conn, request);

			// generate statement for artist
			String artistStatement = generateStatement(artists, "ap.artist");

			// generate statement for style
			String styleStatement = generateStatement(styles, "ap.style");

			// generate statement for tech
			String techStatement = generateStatement(techs, "ap.technique");

			// generate statement for orient
			String orientStatement = generateStatement(orients,
					"ap.orientation");

			System.out.println("2");
			if(!srchterms[0].equals("") && srchterms.length == 1){
			for (int i = 0; i < srchterms.length; i++) {
				try (PreparedStatement ps2 = conn
						.prepareStatement("SELECT a.name FROM art a,artpiece ap WHERE a.id=ap.id "

								+ artistStatement

								+ styleStatement

								+ techStatement

								+ orientStatement

								+ "AND (LOWER(a.name) LIKE '%"
								+ srchterms[i]
								+ "%' "
								+ "OR LOWER(ap.artist) LIKE '%"
								+ srchterms[i]
								+ "%' "
								+ "OR LOWER(ap.technique) LIKE '%"
								+ srchterms[i]
								+ "%'"
								+ "OR LOWER(ap.style) LIKE '%"
								+ srchterms[i]
								+ "%'" + ");")) {
					try (ResultSet rs = ps2.executeQuery()) {
						System.out.println(ps2);
						while (rs.next()) {
							attributes.add(rs.getString("name"));
						}
					}
				}
			}}

			else{
				try (PreparedStatement ps2 = conn
						.prepareStatement("SELECT a.name FROM art a,artpiece ap WHERE a.id=ap.id "

								+ artistStatement

								+ styleStatement

								+ techStatement

								+ orientStatement 
								
								+ ";")) {
					try (ResultSet rs = ps2.executeQuery()) {
						System.out.println("--" + ps2);
						while (rs.next()) {
							attributes.add(rs.getString("name"));
						}
					}
				}
			}

			System.out.println("3");
			if (attributes.isEmpty()) {
				System.out.println("4");
				request.setAttribute("Error", "Niks gevonden!");
				request.getRequestDispatcher("/collectie.jsp").forward(request,
						response);
				return;
			}
			request.setAttribute("Search", attributes);
			request.getRequestDispatcher("/collectie.jsp").forward(request,
					response);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		System.out.println("5");
	}

	private String generateStatement(List list, String element) {
		String statement = "";
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0)
					statement += "AND ( " + element + "='" + list.get(i) + "' ";
				else
					statement += "OR " + element + "='" + list.get(i) + "' ";
			}
			statement += ") ";
		}

		return statement;
	}

	private void createLists(Connection conn, HttpServletRequest request) {
		// lists for collectie.jsp
		// first empty all lists
		artistL = new ArrayList<String>();
		styleL = new ArrayList<String>();
		techL = new ArrayList<String>();
		orientL = new ArrayList<String>();
		// fill them up again
		try {
			PreparedStatement prices = conn
					.prepareStatement("SELECT DISTINCT price From artpiece ORDER BY price");
			PreparedStatement artists = conn
					.prepareStatement("SELECT DISTINCT artist From artpiece ORDER BY artist");
			PreparedStatement widths = conn
					.prepareStatement("SELECT DISTINCT width From artpiece ORDER BY width");
			PreparedStatement heights = conn
					.prepareStatement("SELECT DISTINCT height From artpiece ORDER BY height");
			PreparedStatement styles = conn
					.prepareStatement("SELECT DISTINCT style From artpiece ORDER BY style");
			PreparedStatement techs = conn
					.prepareStatement("SELECT DISTINCT technique From artpiece ORDER BY technique");
			PreparedStatement orients = conn
					.prepareStatement("SELECT DISTINCT orientation From artpiece ORDER BY orientation");
			PreparedStatement ratings = conn
					.prepareStatement("SELECT DISTINCT rating From artpiece ORDER BY rating");

			try (ResultSet rs = artists.executeQuery()) {
				while (rs.next()) {
					artistL.add(rs.getString("artist"));
				}
			}
			try (ResultSet rs = styles.executeQuery()) {
				while (rs.next()) {
					styleL.add(rs.getString("style"));
				}
			}
			try (ResultSet rs = techs.executeQuery()) {
				while (rs.next()) {
					techL.add(rs.getString("technique"));
				}
			}
			try (ResultSet rs = orients.executeQuery()) {
				while (rs.next()) {
					orientL.add(rs.getString("orientation"));
				}
			}

			request.removeAttribute("prices");
			request.removeAttribute("artists");
			request.removeAttribute("widths");
			request.removeAttribute("heights");
			request.removeAttribute("styles");
			request.removeAttribute("techs");
			request.removeAttribute("orients");
			request.removeAttribute("ratings");

			request.setAttribute("artists", artistL);
			request.setAttribute("styles", styleL);
			request.setAttribute("techs", techL);
			request.setAttribute("orients", orientL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
