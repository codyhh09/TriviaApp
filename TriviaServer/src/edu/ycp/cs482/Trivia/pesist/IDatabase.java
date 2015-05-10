package edu.ycp.cs482.Trivia.pesist;

import java.util.List;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Model.User;

public interface IDatabase {
	// method names going to the servlet
	public void addUser(User user);
	
	public User getUser(String username);
	
	public boolean login(String User, String Pass);
	
	public User chgUser(String oldUser, String newUser);
	
	public User chgPass(String oldUser, String newPass);
	
	public void updateStreak(String username, int Streak);
	
	public void updateRetry(String username, int Retry);
	
	public void updateCoins(String username, int coins);
	
	public List<User> getAllUser();
	
	public boolean deleteUser(String user);
	
	public List<Question> getAllQuestion();
	
	public List<Question> getAllQuestionPending();
	
	public void addQuestion(Question question);
	
	public Question getQuestion(int id);
	
	public Question randomQuestion();
	
	public boolean deleteQuestion(int id);
	
	public void changeStatus(int id);
	
	public void changeQuestion(int id, String question);
	
	public void changeQuestionpt1(int id, String answer1);
	
	public void changeQuestionpt2(int id, String answer2);
	
	public void changeQuestionpt3(int id, String answer3);
	
	public void changeQuestionpt4(int id, String answer4);
	
	public void changeQuestionpt5(int id, String answerfinal);
}
