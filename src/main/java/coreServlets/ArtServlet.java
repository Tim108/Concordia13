package coreServlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class ArtServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String title = getValue(request.getPart("title"));
		String artist = getValue(request.getPart("artist"));
		double width = Double.parseDouble(getValue(request.getPart("width")));
		double heigth = Double.parseDouble(getValue(request.getPart("heigth")));
		String technique = getValue(request.getPart("technique"));
		String style = getValue(request.getPart("style"));
		String orientation = getValue(request.getPart("orientation"));
		double price = Double.parseDouble(getValue(request.getPart("price")));
		
		final Part filePart = request.getPart("file");
	    String fileName = getFileName(filePart);
	    String fileExt = fileName.substring(fileName.indexOf('.'));
		
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
		try (Connection conn = DriverManager.getConnection(url, user, pass1)) {
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Art")) {
				if(rs.next()) {
					int num = rs.getInt("count");
					num++;
					fileName = "kunst" + num + fileExt; 
				}
			}
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Art (name, source) VALUES(?,?);")){
				ps.setString(1, title);
				ps.setString(2, fileName);
				if(ps.execute()) {
				}
			}
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Artpiece (artist, height, width, style, technique, orientation, price, rating, rented) VALUES (?,?,?,?,?,?,?,?,?);")) {
				ps.setString(1, artist);
				ps.setDouble(2, heigth);
				ps.setDouble(3, width);
				ps.setString(4, style);
				ps.setString(5, technique);
				ps.setString(6, orientation);
				ps.setDouble(7, price);
				ps.setInt(8, 0);
				ps.setBoolean(9, false);
				if(ps.execute()) {
					System.out.println("Query Executed!");
				}
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();
	    File f = null;
	    try {
	    	ServletContext servletContext = getServletContext();
	        String path = servletContext.getRealPath("/img/");
	    	f = new File(path + "/" + fileName);
	    	out = new FileOutputStream(f);
	        filecontent = filePart.getInputStream();
	        int read = 0;
	        final byte[] bytes = new byte[1024];
	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        writer.println("New file " + fileName + " created at:<br>" + f.getAbsolutePath());
	    } catch (FileNotFoundException fne) {
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());
	    } finally {
	    	response.sendRedirect("search");
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	}
	
	private String getFileName(final Part part) {
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	private static String getValue(Part part) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
	}
}
