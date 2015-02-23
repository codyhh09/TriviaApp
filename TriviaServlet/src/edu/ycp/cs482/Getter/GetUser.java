package edu.ycp.cs482.Getter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/GetUser")
public class GetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String SQL = "select * from USERS";
	DataSource ds = null;
	public void init(ServletConfig config) throws ServletException{
		try  {
			Context context = (Context) new InitialContext().lookup("java:comp/env");
			ds = (DataSource) context.lookup("jdbc/embedded_datasource");
			if(ds == null) throw new ServletException("'datasource' is null");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pre = null;
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("<html><head><title>JDBC in Web App(w/ datasource) </title></head>");
		pw.println("<body><table width=30%>");
		try{
			conn = ds.getConnection();
			pre = conn.prepareStatement(SQL);
			ResultSet rs = pre.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			pw.println("<tr>");
			for(int x = 1; x<=columnCount; x++){
				pw.println("<td bgcolor=lightblue>" + rsmd.getColumnName(x)+"</td>");
			}
			pw.println("</tr>");
			while(rs.next()){
				pw.println("<tr>");
				for (int x = 1; x<=columnCount; x++) pw.println("<td bgcolor=lightgrey>" +rs.getString(x)+"</td>");
				pw.println("</tr>");
			}
		}catch(SQLException e){
				
			}finally{			
				try {
					if(pre != null) pre.close();
					if(conn!= null) conn.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
				pw.println("</table></body></html>");
	
				
			}
	}
}