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
	
	public List<Double> priceL = new ArrayList<Double>();
	public List<String> artistL = new ArrayList<String>();
	public List<Double> widthL = new ArrayList<Double>();
	public List<Double> heightL = new ArrayList<Double>();
	public List<String> styleL = new ArrayList<String>();
	public List<String> techL = new ArrayList<String>();
	public List<String> orientL = new ArrayList<String>();
	public List<Double> ratingL = new ArrayList<Double>();
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) { e1.printStackTrace(); }
		Properties prop = new Properties();
		 String path = "res/dbprops.txt";
		 try{
		 prop.load(new FileInputStream(getServletContext().getRealPath(path)));}catch(Exception e){e.printStackTrace();}
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
		
		request.getRequestDispatcher("/collectie.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		
	
		String srch = (String)request.getParameter("srch-term");
		
		srch = srch.toLowerCase();
		String[] srchterms = srch.split(" ");
		List<String> attributes = new ArrayList<String>();
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) { e1.printStackTrace(); }
		Properties prop = new Properties();
		 String path = "res/dbprops.txt";
		 try{
		 prop.load(new FileInputStream(getServletContext().getRealPath(path)));}catch(Exception e){e.printStackTrace();}
		 String user = prop.getProperty("username");
		 String pass1 = prop.getProperty("pass");
		 String host = prop.getProperty("host");
		 String port = prop.getProperty("port");
		 String dbname = prop.getProperty("dbname");
		 String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
		 
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			createLists(conn, request);
			
			int width = 99999999;
			int heigth = 99999999;
			for(int i=0; i<srchterms.length; i++) {
				if(srchterms[i].equals("by") || srchterms[i].equals("bij") || srchterms[i].equals("x")) {
					if(srchterms.length != i+1 && i-1 > 0) {
						width = Integer.parseInt(srchterms[i-1]);
						heigth = Integer.parseInt(srchterms[i+1]);
					}
				}
				try (PreparedStatement ps2 =
						conn.prepareStatement("SELECT a.name FROM art a,artpiece ap WHERE a.id=ap.id "
								+ "AND (LOWER(a.name) LIKE '%" + srchterms[i] + "%' "
								+ "OR LOWER(ap.artist) LIKE '%" + srchterms[i] + "%' "
								+ "OR LOWER(ap.technique) LIKE '%" + srchterms[i] + "%'"
								+ "OR LOWER(ap.style) LIKE '%" + srchterms[i] + "%'"
								+ "OR ( (ap.width)>'" + width + "'"
								+ "AND  (ap.height)>'" + heigth + "')"
								+ ");")){
					try (ResultSet rs = ps2.executeQuery()) {
						while ( rs.next() ) {
							attributes.add(rs.getString("name"));
						} 
					}
				}
			} 
			if(attributes.isEmpty()) {
				request.setAttribute("Error", "Niks gevonden!");
				request.getRequestDispatcher("/collectie.jsp").forward(request, response);
				return;
			}
			request.setAttribute("Search", attributes);
			request.getRequestDispatcher("/collectie.jsp").forward(request, response);
		} catch (SQLException e2) { e2.printStackTrace(); }
	}
	
	private void createLists(Connection conn, HttpServletRequest request){
		// lists for collectie.jsp
		// first empty all lists
		priceL = new ArrayList<Double>();
		artistL = new ArrayList<String>();
		widthL = new ArrayList<Double>();
		heightL = new ArrayList<Double>();
		styleL = new ArrayList<String>();
		techL = new ArrayList<String>();
		orientL = new ArrayList<String>();
		ratingL = new ArrayList<Double>();
		// fill them up again
		try{
					PreparedStatement prices = conn.prepareStatement("SELECT DISTINCT price From artpiece ORDER BY price");
					PreparedStatement artists = conn.prepareStatement("SELECT DISTINCT artist From artpiece ORDER BY artist");
					PreparedStatement widths = conn.prepareStatement("SELECT DISTINCT width From artpiece ORDER BY width");
					PreparedStatement heights = conn.prepareStatement("SELECT DISTINCT height From artpiece ORDER BY height");
					PreparedStatement styles = conn.prepareStatement("SELECT DISTINCT style From artpiece ORDER BY style");
					PreparedStatement techs = conn.prepareStatement("SELECT DISTINCT technique From artpiece ORDER BY technique");
					PreparedStatement orients = conn.prepareStatement("SELECT DISTINCT orientation From artpiece ORDER BY orientation");
					PreparedStatement ratings = conn.prepareStatement("SELECT DISTINCT rating From artpiece ORDER BY rating");
					
					try(ResultSet rs = prices.executeQuery()) {
						while(rs.next()){
							priceL.add(rs.getDouble("price"));
						}
					}
					try(ResultSet rs = artists.executeQuery()) {
						while(rs.next()){
							artistL.add(rs.getString("artist"));
						}
					}
					try(ResultSet rs = widths.executeQuery()) {
						while(rs.next()){
							widthL.add(rs.getDouble("width"));
						}
					}
					try(ResultSet rs = heights.executeQuery()) {
						while(rs.next()){
							heightL.add(rs.getDouble("height"));
						}
					}
					try(ResultSet rs = styles.executeQuery()) {
						while(rs.next()){
							styleL.add(rs.getString("style"));
						}
					}
					try(ResultSet rs = techs.executeQuery()) {
						while(rs.next()){
							techL.add(rs.getString("technique"));
						}
					}
					try(ResultSet rs = orients.executeQuery()) {
						while(rs.next()){
							orientL.add(rs.getString("orientation"));
						}
					}
					try(ResultSet rs = ratings.executeQuery()) {
						while(rs.next()){
							ratingL.add(rs.getDouble("rating"));
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
					
					
					request.setAttribute("prices", priceL);
					request.setAttribute("artists", artistL);
					request.setAttribute("widths", widthL);
					request.setAttribute("heights", heightL);
					request.setAttribute("styles", styleL);
					request.setAttribute("techs", techL);
					request.setAttribute("orients", orientL);
					request.setAttribute("ratings", ratingL);
		}catch(Exception e){e.printStackTrace();}
	}
	
}
