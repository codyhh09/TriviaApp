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
		Question q = new Question("c","c","c","c","c","c");
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
	
	public boolean login(String User, String Pass){
		for(User user : users){
			if(user.getUsername().equals(user)&&user.getPassword().equals(Pass)){
				return true;
			}
		}
		return false;
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

}
