package edu.ycp.cs482.Trivia.pesist;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs482.Model.*;
import edu.ycp.cs482.Trivia.pesist.IDatabase;

public class FakeDatabase implements IDatabase{
	private List<User> users;
	private List<Question> questions;
	
	public FakeDatabase() {
		users = new ArrayList<User>();
		User user1 = new User("c","c");
		user1.setId(1);
		users.add(user1);
		
		questions = new ArrayList<Question>();
		Question q = new Question("c","c","c","c","c","c","c",QuestionType.ETC,QuestionApproved.ACCEPTED);
		q.setId(1);
		questions.add(q);
	}
	
	public List<User> getAllUser(){
		return new ArrayList<User>(users);
	}
	
	public void addUser(User user) {
		//add user to the list
		users.add(user);
	}
	
	public User getUser(String username){
		for(User user : users){
			if(user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
	}
	
	public User chgUser(String oldUser, String newUser){
		User user = getUser(oldUser);
		user.setUsername(newUser);
		return user;
	}
	
	public User chgPass(String oldUser, String newPass){
		User user = getUser(oldUser);
		user.setPassword(newPass);
		return user;
	}
	
	public boolean login(String User, String Pass){
		for(User user : users){
			if(user.getUsername().equals(user)&&user.getPassword().equals(Pass)){
				return true;
			}
		}
		return false;
	}

	public void updateStreak(String username, int Streak){
		User user = getUser(username);
		if(user.getStreak() < Streak){
			user.setStreak(Streak);
		}
	}
	// deleting a user
	public boolean deleteUser(String user) {
		for(User temp : users){
			if(temp.getUsername().equals(user)){
				return users.remove(getUser(user));
			}
		}
		return false;
	}
	
	public List<Question> getAllQuestion(){
		return new ArrayList<Question>(questions);
	}
	
	public List<Question> getAllQuestionPending(){
		List<Question> qu = new ArrayList<Question>();
		for(Question q : questions){
			if(q.getRight() == QuestionApproved.PENDING){
				qu.add(q);
			}
		}
		return qu;
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	public Question getQuestion(int id){
		for(Question q : questions){
			if(id == q.getId()){
				return q;
			}
		}
		return null;
	}

	// deleting a user
	public boolean deleteQuestion(int id) {
		for(Question q : questions){
			if(id == q.getId()){
				return questions.remove(getQuestion(id));
			}
		}
		return false;
	}

	@Override
	public Question randomQuestion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Question changeQuestion(int id, Question newq){
		for(Question q : questions){
			if(id == q.getId()){
				newq.setId(id);
				return newq;
			}
		}
		return null;
	}
}
