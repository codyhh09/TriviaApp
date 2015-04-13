package edu.ycp.cs482.Trivia.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Trivia.JSON.JSON;
import edu.ycp.cs482.Trivia.controller.AddQuestion;
import edu.ycp.cs482.Trivia.controller.DeleteQuestion;
import edu.ycp.cs482.Trivia.controller.GetAllQuestion;
import edu.ycp.cs482.Trivia.controller.GetQuestion;
import edu.ycp.cs482.Trivia.controller.getRandomQuestion;

public class QuestionPage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private getRandomQuestion randomQuestion = new getRandomQuestion();
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_OK);
			
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// Return the item in JSON format
			JSON.getObjectMapper().writeValue(resp.getWriter(), randomQuestion.RandomQuestion());
			return ;
		}
		
		// Get the user name
		if (pathInfo.startsWith("/")){
			pathInfo = pathInfo.substring(1);
		}
		int id = Integer.parseInt(pathInfo);
		// Use a GetUsercontroller to find the user in the database
		GetQuestion controller = new GetQuestion();
		Question question = controller.getQuestion(id);
		
		if (question == null) {
			// No such item, so return a NOT FOUND response
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			resp.getWriter().println("No Question " + pathInfo);
			return;
		}
		
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), question);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Question question = JSON.getObjectMapper().readValue(req.getReader(), Question.class);
		// Use a GetUser controller to find the item in the database
		AddQuestion controller = new AddQuestion();
		controller.addQuestion(question);
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// writing the operation out.
		JSON.getObjectMapper().writeValue(resp.getWriter(), question);
	}

	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("text/plain");
			resp.getWriter().println("No qusetion listed");
			return;
		}
		// Get the item name
		if (pathInfo.startsWith("/")){
			pathInfo = pathInfo.substring(1);
		}
		int id = Integer.parseInt(pathInfo);
		DeleteQuestion controller = new DeleteQuestion();
		controller.deleteQuestion(id);

		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), pathInfo);
	}
}
