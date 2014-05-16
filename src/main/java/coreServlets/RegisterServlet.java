package coreServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

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
		String city = (String)request.getParameter("city");
		String postal = (String)request.getParameter("postal");
		String email = (String)request.getParameter("email");
		String pass = (String)request.getParameter("pass");
		String newsl = (String)request.getParameter("newsletter");
		String phone = (String)request.getParameter("phone");
		
		if(!ValidateInput(request, response, name, surname, address, city, postal, email, pass, phone)) return;
		boolean succes = CreateAccount(request, response, name,surname,address,city,postal,email,pass,newsl,phone);
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML/><HEAD><TITLE>Stuff</TITLE></HEAD>\n<BODY>");
		out.println("New Account: " + name + " " + surname + " " + address + " " + city + " " + postal + " " + email + " " + pass + " " + succes);
		out.println("</BODY></HTML>");
	}
	
	public boolean ValidateInput(HttpServletRequest request, HttpServletResponse response, String name, String surname, String address, String city, String postal, String email, String pass, String phone)
	{
		boolean error = false;
		if(name.matches(".*\\d.*")) {
			request.setAttribute("NameError", "Er mag geen cijfer in uw voornaam zittten.");
			error = true;
		}
		if(name.length() == 0) {
			request.setAttribute("NameError", "U moet een voornaam invullen.");
			error = true;
		}
		if(surname.matches(".*\\d.*")) {
			request.setAttribute("SurnameError", "Er mag geen cijfer in uw achternaam zittten.");
			error = true;
		}
		if(surname.length() == 0) {
			request.setAttribute("SurnameError", "U moet een achternaam invullen.");
			error = true;
		}
		if(address.length() == 0) {
			request.setAttribute("AddressError", "U moet een adres invullen.");
			error = true;
		}
		if(!address.matches(".*\\d.*")) {
			request.setAttribute("AddressError", "U moet een huisnummer invullen.");
			error = true;
		}
		if(city.length() == 0) {
			request.setAttribute("CityError", "U moet een woonplaats invullen.");
			error = true;
		}
		if(postal.length() == 0) {
			request.setAttribute("PostalError", "U moet een postcode invullen.");
			error = true;
		}
		if(!postal.matches(".*\\d.*")) {
			request.setAttribute("PostalError", "Uw moet cijfers bevatten.");
			error = true;
		}
		if(email.length() == 0) {
			request.setAttribute("EmailError", "U moet een e-mailadres invullen.");
			error = true;
		}
		if(!email.contains("@")) {
			request.setAttribute("EmailError", "U moet een werkende e-mailadres invullen.");
			error = true;
		}
		if(!email.contains(".")) {
			request.setAttribute("EmailError", "U moet een werkende e-mailadres invullen.");
			error = true;
		}
		if(pass.length() < 6) {
			request.setAttribute("PassError", "Uw wachtwoord moet minstens 6 tekens lang zijn.");
			error = true;
		}
		if(!phone.matches(".*\\d.*")) {
			request.setAttribute("PhoneError", "Uw moet een werkend telefoonnummer invullen.");
			error = true;
		}
		if(error) {
			try {
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			} catch (ServletException | IOException e) {  }
		}
		return !error;
	}
	
	public boolean CreateAccount(HttpServletRequest request, HttpServletResponse response, String name, String surname, String address, String city, String postal, String email, String pass, String newsl, String phone)
	{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) { e1.printStackTrace(); }
		try (Connection conn = DriverManager.getConnection(
			"jdbc:postgresql://localhost:5432/Kunstuitleen",
			"postgres",
			"dude")) {
			try (PreparedStatement ps2 =
					conn.prepareStatement("SELECT name FROM Customer WHERE email=?;")){
				ps2.setString(1, email);
				try (ResultSet rs = ps2.executeQuery()) {
			        while ( rs.next() ) {
			            int numColumns = rs.getMetaData().getColumnCount();
			            if(numColumns > 0) {
			            	request.setAttribute("EmailError", "Dit e-mailadres is al in gebruik.");
			            	request.getRequestDispatcher("/register.jsp").forward(request, response);
			            	return false;
			            }
			        } 
			    } catch (ServletException | IOException e) { e.printStackTrace(); } 
			}
			
			try (PreparedStatement ps =
				    conn.prepareStatement("INSERT INTO Customer (pass,name,surname,address,city,postal,email,phone,newsletter)"
				    		+ " VALUES(?,?,?,?,?,?,?,?,?);")
				){
					pass = hashThis(pass);
				    ps.setString(1, pass);
				    ps.setString(2, name);
				    ps.setString(3, surname);
				    ps.setString(4, address);
				    ps.setString(5, city);
				    ps.setString(6, postal);
				    ps.setString(7, email);
				    ps.setString(8, phone);
				    if(newsl == null) ps.setBoolean(9, false); 
				    else ps.setBoolean(9, true);
				    try {
				    	ps.executeQuery();
				    }
				    catch (SQLException e2) { 
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