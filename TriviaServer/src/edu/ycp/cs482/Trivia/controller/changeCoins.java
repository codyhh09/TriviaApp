package edu.ycp.cs482.Trivia.controller;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.Trivia.pesist.Database;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class changeCoins {
	public void changeCoins(String oldUser, int coins){
		IDatabase db = Database.getInstance();
		db.updateCoins(oldUser, coins);
	}
}
