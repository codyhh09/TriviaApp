package edu.ycp.cs482.Trivia.controller;

import java.util.List;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class getAllQusetionPending {
	public List<Question> GetallQuestionPending(){
		IDatabase db = Database.getInstance();
		return db.getAllQuestionPending();
	}
}
