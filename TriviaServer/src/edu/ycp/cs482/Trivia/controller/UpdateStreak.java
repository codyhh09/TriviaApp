package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class UpdateStreak {
	public void updateStreak(String username, int Streak){
		IDatabase db = Database.getInstance();
		db.updateStreak(username, Streak);;
	}
}
