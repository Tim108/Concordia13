package rest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import com.sun.jersey.api.view.Viewable;
@Path("/generateArt")
public class GenerateArt {
	Map<Integer, Art> artList = new HashMap<Integer, Art>();
	ArrayList<Art> randomArt = new ArrayList<Art>();

	public GenerateArt() {
		try {
			URL resource = getClass().getResource("/");
			String path = resource.getPath();
			path = path.replace("WEB-INF/classes/", "");
			path = path + "res/dbprops.txt"; 
			Properties prop = new Properties();
			prop.load(new FileInputStream(path));
			String user = prop.getProperty("username");
			String pass1 = prop.getProperty("pass");
			String host = prop.getProperty("host");
			String port = prop.getProperty("port");
			String dbname = prop.getProperty("dbname");
			String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
			 System.out.println("URL: " + url + ", USER: " + user + ", PASS: " + pass1);
			try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
				try (PreparedStatement ps = conn
						.prepareStatement("SELECT * FROM art, artpiece WHERE art.id = artpiece.id;")) {
					try (ResultSet rs = ps.executeQuery()) {
						while (rs.next()) {
							Art addArt = new Art(rs.getInt("id"),
									rs.getString("name"),
									rs.getString("source"),
									rs.getString("artist"),
									rs.getDouble("height"),
									rs.getDouble("width"),
									rs.getString("style"),
									rs.getString("technique"),
									rs.getString("orientation"),
									rs.getDouble("price"),
									rs.getDouble("rating"),
									rs.getBoolean("rented"));
							artList.put(rs.getInt("id"), addArt);
						}
					}
				} conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void randomArt(int amount) {
		randomArt.clear();
		ArrayList<Integer> allPaintings = new ArrayList<Integer>();
		ArrayList<Integer> inRandom = new ArrayList<Integer>();
		allPaintings.addAll(artList.keySet());
		int first = allPaintings.get(0);
		int last = allPaintings.get(allPaintings.size() - 1);
		if (amount > allPaintings.size()) amount = allPaintings.size();	
		for (int i = 0; i < amount; i++) {
			int random = first + (int) (Math.random() * ((last - first) + 1));
			while (inRandom.contains(random) || !allPaintings.contains(random)) {
				random = first + (int) (Math.random() * ((last - first) + 1));
			}
				inRandom.add(random);
				randomArt.add(artList.get(random));
		}
		inRandom.clear();
	}
	
	@GET
	@Path("/frontpage")
	public Viewable index(@Context HttpServletRequest request, @Context HttpServletResponse response) {
	List<String> frontArtSource = new ArrayList<String>();
	randomArt(3);
	for(int i = 0; i<3; i++){
		frontArtSource.add(randomArt.get(i).getSource());
	}
	request.setAttribute("artheaders", frontArtSource);
	return new Viewable("/index.jsp", null);
	}
	
	@Path("/genhtml")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String HTMLcode(@QueryParam("amount") int amount, @QueryParam("align") String align) {
		randomArt(amount); 
		String htmlcode = "";
		if(align.equals("hor")) {
		htmlcode = "<table><tr>";
		for (int i = 0; i < randomArt.size(); i++) {
			htmlcode += "<td><table border=\"1\" bordercolor=\"black\" style=\"width:100px;border-radius:4px;margin:20px;\">"
					+ "<tr><td><img src=../img/" + randomArt.get(i).getSource() +"></td></tr>"
					+ "<tr><td>" + randomArt.get(i).getName() + "</td></tr>"
					+ "<tr><td>" + randomArt.get(i).getArtist() + "</td></tr>"
					+ "</table></td>";
			
		}
		htmlcode += "</tr></table>";
		} else {
			htmlcode = "<table>";
			for (int i = 0; i < randomArt.size(); i++) {
				htmlcode += "<tr><td><table border=\"1\" bordercolor=\"black\" style=\"width:100px;border-radius:4px;margin:20px;\">"
						+ "<tr><td><img src=../img/" + randomArt.get(i).getSource() +"></td></tr>"
						+ "<tr><td>" + randomArt.get(i).getName() + "</td></tr>"
						+ "<tr><td>" + randomArt.get(i).getArtist() + "</td></tr>"
						+ "</table></td></tr>";
				
			}
			htmlcode += "</table>";
		}
		return htmlcode;
	}
	
	@Path("/genxml")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String XMLCode(@QueryParam("amount") int amount, @QueryParam("align") String align) {
		return "<painting>" + randomArt.toString() + "</painting>";
	}
}
