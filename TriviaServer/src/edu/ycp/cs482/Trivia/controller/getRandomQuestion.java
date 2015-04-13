package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class getRandomQuestion {
	public Question RandomQuestion(){
		IDatabase db = Database.getInstance();
		return db.randomQuestion();
	}
}
