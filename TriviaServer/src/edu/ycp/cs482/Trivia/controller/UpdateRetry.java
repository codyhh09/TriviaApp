package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class UpdateRetry {
	public void updateretry(String username, int Retry){
		IDatabase db = Database.getInstance();
		db.updateRetry(username, Retry);
	}
}
