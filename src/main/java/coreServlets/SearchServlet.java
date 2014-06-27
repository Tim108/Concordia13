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

		Connection conn = (Connection) getServletContext().getAttribute(
				"DBConnection");

		try {
			createLists(conn, request);
		} catch (Exception e) {
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

		String p0 = request.getParameter("minPrice");
		String p1 = request.getParameter("maxPrice");
		String s0 = request.getParameter("minBred");
		String s1 = request.getParameter("maxBred");
		String s2 = request.getParameter("minHoog");
		String s3 = request.getParameter("maxHoog");
		String r0 = request.getParameter("minRat");
		String r1 = request.getParameter("maxRat");

		if (p0 == null || p0.equals(""))
			p0 = "0";
		if (p1 == null || p1.equals(""))
			p1 = "0";
		if (s0 == null || s0.equals(""))
			s0 = "0";
		if (s1 == null || s1.equals(""))
			s1 = "0";
		if (s2 == null || s2.equals(""))
			s2 = "0";
		if (s3 == null || s3.equals(""))
			s3 = "0";
		if (r0 == null || r0.equals(""))
			r0 = "0";
		if (r1 == null || r1.equals(""))
			r1 = "0";
		
		//get the rating
		String ratingString = request.getParameter("rating");
		System.out.println("ratingString = " + ratingString);
		String[] strList= ratingString.split("&");
		int rating = Integer.parseInt(strList[0]);
		long ratedId = Integer.parseInt(strList[1]);
		System.out.println("rating = " + rating);
		System.out.println("ratedId = " + ratedId);
		double oldrating = 0;
		int rates = 0;
		double newRating = 0;
		

		// sql inloggen
		Connection conn = (Connection) getServletContext().getAttribute(
			"DBConnection");
		// --
		
		try (PreparedStatement ps = conn
				.prepareStatement("SELECT ap.rating, ap.rates FROM artpiece ap WHERE ap.id=" + ratedId + ";")) {
			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()){
				oldrating = rs.getDouble("rating");
				rates = rs.getInt("rates");
				System.out.println("oldrating & rates = " + oldrating + " & " + rates);
			}}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try{
			double rates1 = rates;
			newRating = (((1/(rates1+1)*rates1)*oldrating) + (1/(rates1+1)*rating));
			newRating = Math.round(newRating * 10) / (double)10;
			rates++;
			System.out.println("newrating = " + newRating);
		}catch (NullPointerException e){
			System.out.println("Calculation error! Rate not processed!");
		}
		
		try (PreparedStatement ps = conn
				.prepareStatement("UPDATE artpiece SET rating=" + newRating + ", rates=" + rates + "WHERE id=" + ratedId)) {
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			// prices
			prices[0] = Double.parseDouble(p0);
			prices[1] = Double.parseDouble(p1);
			// sizes
			sizes[0] = Double.parseDouble(s0);
			sizes[1] = Double.parseDouble(s1);
			sizes[2] = Double.parseDouble(s2);
			sizes[3] = Double.parseDouble(s3);
			// ratings
			ratings[0] = Double.parseDouble(r0);
			ratings[1] = Double.parseDouble(r1);

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
		} catch (NumberFormatException e) {
			createLists(conn, request);
			request.setAttribute("Error", "Niks gevonden!");
			request.getRequestDispatcher("/collectie.jsp").forward(request, response);
			return;
		}
		List<String> attributes = new ArrayList<String>();

	
		try {
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

			// generate statement for price
			String priceStatement = "";
			if (prices[0] != 0)
				priceStatement += "AND ap.price>=" + prices[0] + " ";
			if (prices[1] != 0)
				priceStatement += "AND ap.price<=" + prices[1] + " ";

			// generate statement for size
			String sizeStatement = "";
			if (sizes[0] != 0)
				sizeStatement += "AND ap.width>=" + sizes[0] + " ";
			if (sizes[1] != 0)
				sizeStatement += "AND ap.width<=" + sizes[1] + " ";
			if (sizes[2] != 0)
				sizeStatement += "AND ap.height>=" + sizes[2] + " ";
			if (sizes[3] != 0)
				sizeStatement += "AND ap.height<=" + sizes[3] + " ";

			// generate statement for rating
			String rateStatement = "";
			if (ratings[0] != 0)
				sizeStatement += "AND ap.rating>=" + ratings[0] + " ";
			if (ratings[1] != 0)
				sizeStatement += "AND ap.rating<=" + ratings[1] + " ";

			if (!srchterms[0].equals("") && srchterms.length == 1) {
				for (int i = 0; i < srchterms.length; i++) {
					try (PreparedStatement ps2 = conn
							.prepareStatement("SELECT a.name FROM art a,artpiece ap WHERE a.id=ap.id "

									+ artistStatement

									+ styleStatement

									+ techStatement

									+ orientStatement

									+ priceStatement

									+ sizeStatement

									+ rateStatement

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
									+ srchterms[i] + "%'" + ");")) {
						try (ResultSet rs = ps2.executeQuery()) {
							while (rs.next()) {
								attributes.add(rs.getString("name"));
							}
						}
					}
				}
			}

			else {
				try (PreparedStatement ps2 = conn
						.prepareStatement("SELECT a.name FROM art a,artpiece ap WHERE a.id=ap.id "

								+ artistStatement

								+ styleStatement

								+ techStatement

								+ orientStatement

								+ priceStatement

								+ sizeStatement

								+ rateStatement

								+ ";")) {
					try (ResultSet rs = ps2.executeQuery()) {
						while (rs.next()) {
							attributes.add(rs.getString("name"));
						}
					}
				}
			}

			
			if (attributes.isEmpty()) {
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
