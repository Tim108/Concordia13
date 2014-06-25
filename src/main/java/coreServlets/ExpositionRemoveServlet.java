package coreServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rest.User;

public class ExpositionRemoveServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		System.out.println(request.getParameter("id"));
		int collID = Integer.parseInt(request.getParameter("coll"));
		String[] ids = request.getParameter("id").split(" ");
		
		Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
		try {
			String stmt = "DELETE FROM belongs_to WHERE collection = ? AND (";
			for(int i=1; i<ids.length; i++) {
				System.out.println("IDS[" + i + "]: " + ids[i]);
				stmt = stmt + "art = ? OR ";
			}
			stmt = stmt + "art = ?);";
			try (PreparedStatement ps = conn.prepareStatement(stmt)) 
			{
				ps.setInt(1, collID);
				for(int i=1; i<ids.length; i++) {
					ps.setInt(i+1, Integer.parseInt(ids[i]));
				}
				ps.setInt(ids.length+1, Integer.parseInt(ids[1]));
				ps.execute();
			}
			
			try(PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM belongs_to WHERE collection = ?")) {
				ps.setInt(1, collID);
				try(ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
						if(rs.getInt("count") == 0) {
							try(PreparedStatement ps2 = conn.prepareStatement("DELETE FROM collection WHERE id = ?")) {
								ps2.setInt(1, collID);
								ps.execute();
							}
							Map<Integer, String> expositions = (Map<Integer, String>)request.getSession().getAttribute("Expositions");
							expositions.remove(collID);
							collID = 0;
						}
					}
				}
			}
			
			stmt = "DELETE FROM uploaded WHERE ";
			for(int i=1; i<ids.length; i++) {
				stmt = stmt + "external_picture = ? OR ";
			}
			stmt = stmt + "external_picture = ?;";
			try(PreparedStatement ps = conn.prepareStatement(stmt)) {
				for(int i=1; i<ids.length; i++) {
					ps.setInt(i, Integer.parseInt(ids[i]));
				}
				ps.setInt(ids.length, Integer.parseInt(ids[1]));
				ps.execute();
			}
			stmt = "DELETE FROM external_picture WHERE ";
			for(int i=1; i<ids.length; i++) {
				stmt = stmt + "id = ? OR ";
			}
			stmt = stmt + "id = ?;";
			try(PreparedStatement ps = conn.prepareStatement(stmt)) {
				for(int i=1; i<ids.length; i++) {
					ps.setInt(i, Integer.parseInt(ids[i]));
				}
				ps.setInt(ids.length, Integer.parseInt(ids[1]));
				ps.execute();
			}
			response.sendRedirect("/concordia/expositie?id=" + collID);
		} catch (SQLException e1) {
		e1.printStackTrace();
		}
	}
}