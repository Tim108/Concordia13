package coreServlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet implementation class AppContextListener
 */

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		 Connection con = (Connection) arg0.getServletContext().getAttribute("DBConnection");
	        try {
	            con.close();
	            System.out.println("Closing DB");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		 ServletContext ctx = arg0.getServletContext();
		 String URL = ctx.getInitParameter("dbURL");
		 String pass = ctx.getInitParameter("dbPassword");
		 String user = ctx.getInitParameter("dbUser");
		 System.out.println(URL + " - " + user + " - " + pass);
		 
		try{
			DBConnectionManager connectionManager = new DBConnectionManager(URL,user,pass);
			ctx.setAttribute("DBConnection", connectionManager.getConnection());
			if(ctx.getAttribute("DBConnection")!=null){
			System.out.println("Database connectie aangemaakt");}
		}catch(Exception e){e.printStackTrace();}
	}

}
