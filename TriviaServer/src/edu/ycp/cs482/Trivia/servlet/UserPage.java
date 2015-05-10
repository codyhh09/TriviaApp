package edu.ycp.cs482.Trivia.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.Trivia.JSON.JSON;
import edu.ycp.cs482.Trivia.controller.AddUser;
import edu.ycp.cs482.Trivia.controller.ChangePass;
import edu.ycp.cs482.Trivia.controller.ChangeUser;
import edu.ycp.cs482.Trivia.controller.DeleteUser;
import edu.ycp.cs482.Trivia.controller.GetAllUsers;
import edu.ycp.cs482.Trivia.controller.GetUser;
import edu.ycp.cs482.Trivia.controller.LoginUser;
import edu.ycp.cs482.Trivia.controller.UpdateRetry;
import edu.ycp.cs482.Trivia.controller.UpdateStreak;
import edu.ycp.cs482.Trivia.controller.changeCoins;


public class UserPage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String user, pass, user1;
	private int streak, retry, coins;
	private User users;
	private GetUser getuser = new GetUser();
	private GetAllUsers getalluser = new GetAllUsers();
	private LoginUser loginuser = new LoginUser();
	private DeleteUser deleteUser = new DeleteUser();
	private ChangeUser changeuser = new ChangeUser();
	private ChangePass changepassword = new ChangePass();
	private UpdateStreak updateStreak = new UpdateStreak();
	private UpdateRetry updateretry = new UpdateRetry(); 
	private changeCoins changeCoins = new changeCoins();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();		
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {		
			getalluser = new GetAllUsers();
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// Return the item in JSON format
			JSON.getObjectMapper().writeValue(resp.getWriter(), getalluser.getallUser());
			return;
		}
		
		// Get the user name
		if (pathInfo.startsWith("/")){
			pathInfo = pathInfo.substring(1);
		}
		
		if(pathInfo.contains("+")){
			user = pathInfo.substring(0, pathInfo.indexOf('+'));
			pass = pathInfo.substring(pathInfo.indexOf('+')+1,pathInfo.length());
			
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// Return the item in JSON format
			JSON.getObjectMapper().writeValue(resp.getWriter(), loginuser.login(user, pass));
			
			return;
		}
		// Use a GetUsercontroller to find the user in the database
		User user = getuser.getUser(pathInfo);
		
		if (user == null) {
			// No such item, so return a NOT FOUND response
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			resp.getWriter().println("No such user: " + pathInfo);
			return;
		}
		
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), user);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User users = JSON.getObjectMapper().readValue(req.getReader(), User.class);

		if(getuser.getUser(users.getUsername()) == null){
			AddUser controller = new AddUser();
			controller.addUser(users);
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// writing the operation out.
			JSON.getObjectMapper().writeValue(resp.getWriter(), users);
		}
	}

	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("text/plain");
			resp.getWriter().println("No user listed");
			return;
		}
		// Get the item name
		if (pathInfo.startsWith("/")){
			pathInfo = pathInfo.substring(1);
		}
		deleteUser.deleteUser(pathInfo);

		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), pathInfo);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, JsonGenerationException, JsonMappingException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.setContentType("application/json");
			return;
		}else{
			// Get the item name
			if (pathInfo.startsWith("/")){
				pathInfo = pathInfo.substring(1);
			}	

			if (pathInfo.contains("/user=")){
				user = pathInfo.substring(0, pathInfo.indexOf('/'));
				user1 = pathInfo.substring(pathInfo.indexOf('=')+1,pathInfo.length());
				users = changeuser.changeUser(user, user1);
				
				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), users);
				return;
			}	
			
			if (pathInfo.contains("/pass=")){
				user = pathInfo.substring(0, pathInfo.indexOf('/'));
				pass = pathInfo.substring(pathInfo.indexOf('=')+1,pathInfo.length());
				users = changepassword.changePass(user, pass);
				
				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), users);
				return;
			}	
			
			if (pathInfo.contains("/streak=")){
				user = pathInfo.substring(0, pathInfo.indexOf('/'));
				streak = Integer.parseInt(pathInfo.substring(pathInfo.indexOf('=')+1,pathInfo.length()));
				
				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				updateStreak.updateStreak(user, streak);
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), streak);
				return;
			}
			
			if (pathInfo.contains("/retry=")){
				user = pathInfo.substring(0, pathInfo.indexOf('/'));
				retry = Integer.parseInt(pathInfo.substring(pathInfo.indexOf('=')+1,pathInfo.length()));
				
				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				updateretry.updateretry(user, retry);
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), retry);
				return;
			}
			
			if (pathInfo.contains("/coins=")){
				user = pathInfo.substring(0, pathInfo.indexOf('/'));
				coins = Integer.parseInt(pathInfo.substring(pathInfo.indexOf('=')+1,pathInfo.length()));
				
				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				changeCoins.changeCoins(user, coins);
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), coins);
				return;
			}
		}
	}
}
