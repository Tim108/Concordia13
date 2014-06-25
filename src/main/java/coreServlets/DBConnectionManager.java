package coreServlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
 
public class DBConnectionManager extends HttpServlet{
 
    private Connection connection;
     
    public DBConnectionManager() throws ClassNotFoundException, SQLException{
    	ServletContext ctx = this.getServletContext();
		 String URL = ctx.getInitParameter("dbURL");
		 String pass = ctx.getInitParameter("dbPassword");
		 String user = ctx.getInitParameter("dbUser");
		 System.out.println("URL =? " + URL);
		 Class.forName("org.postgresql.Driver");
	     this.connection = DriverManager.getConnection(URL, user, pass);
    }
    
    public DBConnectionManager(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException{
    	Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(dbURL, user, pwd);
    }
     
    public Connection getConnection(){
        return this.connection;
    }
}
