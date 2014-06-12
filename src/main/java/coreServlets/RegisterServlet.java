package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String name = (String)request.getParameter("name");
		String surname = (String)request.getParameter("surname");
		String address = (String)request.getParameter("address");
		String hnum = (String)request.getParameter("housenumber");
		String city = (String)request.getParameter("city");
		String postal = (String)request.getParameter("postal");
		String email = (String)request.getParameter("email");
		String pass = (String)request.getParameter("pass");
		String newsl = (String)request.getParameter("newsletter");
		String phone = (String)request.getParameter("phone");
		
		if(!ValidateInput(request, response, name, surname, address, hnum, city, postal, email, pass, phone)) return;
		boolean succes = CreateAccount(request, response, name,surname,address,hnum,city,postal,email,pass,newsl,phone);
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML/><HEAD><TITLE>Stuff</TITLE></HEAD>\n<BODY>");
		out.println("New Account: " + name + " " + surname + " " + address + " " + city + " " + postal + " " + email + " " + pass + " " + succes);
		out.println("</BODY></HTML>");
	}
	
	public boolean ValidateInput(HttpServletRequest request, HttpServletResponse response, String name, String surname, String address, String hnum, String city, String postal, String email, String pass, String phone)
	{
		boolean error = false;
		if(!name.matches("[A-Z][a-zA-Z]*")) {
			request.setAttribute("NameError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Uw voornaam mag alleen letters bevatten.");
			request.removeAttribute("name");
			error = true;
		}
		if(name.length() == 0) {
			request.setAttribute("NameError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een voornaam invullen.");
			request.removeAttribute("name");
			error = true;
		}
		if(!surname.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
			request.setAttribute("SurnameError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Uw achternaam mag alleen letters bevatten.");
			request.removeAttribute("surname");
			error = true;
		}
		if(surname.length() == 0) {
			request.setAttribute("SurnameError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een achternaam invullen.");
			request.removeAttribute("surname");
			error = true;
		}
		if(address.length() == 0) {
			request.setAttribute("AddressError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een adres invullen.");
			request.removeAttribute("address");
			error = true;
		}
		if(!address.matches("[A-Z][a-zA-Z]*")) {
			request.setAttribute("AddressError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Uw adres mag alleen letters bevatten.");
			request.removeAttribute("address");
			error = true;
		}
		if(!hnum.matches("\\d+")) {
			request.setAttribute("HouseNumberError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Uw huisnummer mag alleen cijfers bevatten.");
			request.removeAttribute("hnum");
			error = true;
		}
		if(city.length() == 0) {
			request.setAttribute("CityError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een woonplaats invullen.");
			request.removeAttribute("city");
			error = true;
		}
		if(!city.matches("[A-Z][a-zA-Z]*")) {
			request.setAttribute("CityError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Uw woonplaats mag alleen letters bevatten.");
			request.removeAttribute("city");
			error = true;
		}
		if(postal.length() == 0) {
			request.setAttribute("PostalError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een postcode invullen.");
			request.removeAttribute("postal");
			error = true;
		}
		if(!postal.matches(".*\\d.*")) {
			request.setAttribute("PostalError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Uw postcode moet cijfers bevatten.");
			request.removeAttribute("postal");
			error = true;
		}
		if(email.length() == 0) {
			request.setAttribute("EmailError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een e-mailadres invullen.");
			request.removeAttribute("email");
			error = true;
		}
		if(!email.contains("@")) {
			request.setAttribute("EmailError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een werkend e-mailadres invullen.");
			request.removeAttribute("email");
			error = true;
		}
		if(!email.contains(".")) {
			request.setAttribute("EmailError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een werkend e-mailadres invullen.");
			request.removeAttribute("email");
			error = true;
		}
		if(pass.length() < 6) {
			request.setAttribute("PassError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> Uw wachtwoord moet minstens 6 tekens bevatten.");
			request.removeAttribute("pass");
			error = true;
		}
		if(!phone.matches(".*\\d.*")) {
			request.setAttribute("PhoneError", "<img src=\"res/redCross.png\" height=\"14px;\" alt=\"ERROR:\"> U moet een werkend telefoonnummer invullen.");
			request.removeAttribute("phone");
			error = true;
		}
		if(error) {
			try {
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			} catch (ServletException | IOException e) {  }
		}
		return !error;
	}
	
	public boolean CreateAccount(HttpServletRequest request, HttpServletResponse response, String name, String surname, String address, String hnum, String city, String postal, String email, String pass, String newsl, String phone)
	{
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
			try (PreparedStatement ps2 =
					conn.prepareStatement("SELECT name FROM Customer WHERE email=?;")){
				ps2.setString(1, email);
				try (ResultSet rs = ps2.executeQuery()) {
			        while ( rs.next() ) {
			            int numColumns = rs.getMetaData().getColumnCount();
			            if(numColumns > 0) {
			            	request.setAttribute("EmailError", "<img src=\"http://www.clker.com/cliparts/D/0/R/b/X/W/red-cross-md.png\" height=\"14px;\" alt=\"ERROR:\"> Dit e-mailadres is al in gebruik.");
			            	request.getRequestDispatcher("/register.jsp").forward(request, response);
			            	return false;
			            }
			        } 
			    } catch (ServletException | IOException e) { e.printStackTrace(); } 
			}
			
			try (PreparedStatement ps =
				    conn.prepareStatement("INSERT INTO Customer (pass,name,surname,address,city,postal,email,phone,newsletter,activation)"
				    		+ " VALUES(?,?,?,?,?,?,?,?,?,?);")
				){
					String hashpass = hashThis(pass);
				    ps.setString(1, hashpass);
				    ps.setString(2, name);
				    ps.setString(3, surname);
				    ps.setString(4, address + " " + hnum);
				    ps.setString(5, city);
				    ps.setString(6, postal);
				    ps.setString(7, email);
				    ps.setString(8, phone);
				    
				    RandomGenerator rg = new RandomGenerator();
				    String activation = rg.createActivition();
				    ps.setString(10, activation);
				    SendEmailServlet sm = new SendEmailServlet();
				    String fullname = name + " " + surname;
				    sm.sendMail(email, "register", fullname, pass, activation);
				    
				    if(newsl == null) ps.setBoolean(9, false); 
				    else ps.setBoolean(9, true);
				    try {
				    	ps.execute();
				    }
				    catch (SQLException e2) { 
				    	e2.printStackTrace();
				    }
				    response.sendRedirect("");
				    return true;
				}
		}
		catch (SQLException | IOException e) { 
			e.printStackTrace(); 
			return false;
		}
	}
	
	public static String hashThis(String str){
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes("UTF-8"));
			byte[] digest = md.digest();
			str = new String(digest, "UTF-8");
			str = str.replace("\0", ""); 
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
}
