package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExternalArtAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		
		String source = (String)request.getParameter("source");
		String name = (String)request.getParameter("name");
		String website = (String)request.getParameter("website");
		
		HttpSession s = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		int artID = 0;
		int userID = (int)s.getAttribute("Logged");
		try {
			try(PreparedStatement ps = conn.prepareStatement("INSERT INTO Art(name,source) VALUES(?,?) RETURNING id;")) {
				ps.setString(1, name);
				ps.setString(2, source);
				try(ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
						artID = rs.getInt("id");
					}
				}
			}
			try(PreparedStatement ps = conn.prepareStatement("INSERT INTO external_picture(id,website) VALUES(?,?);")) {
				ps.setInt(1, artID);
				ps.setString(2, website);
				if(ps.execute()) {
				}
			}
			try(PreparedStatement ps = conn.prepareStatement("INSERT INTO uploaded(external_picture,customer) VALUES(?,?);")) {
				ps.setInt(1, artID);
				ps.setInt(2, userID);
				if(ps.execute()) {
				}
			}
			request.setAttribute("id", artID);
			request.getRequestDispatcher("/voegToeExpositie").forward(request, response);
			return;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
