package edu.ycp.cs482.Model;

public class Question {
	private int id;
	private String Question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String FinalAnswer;
	private String creator;
	private QuestionType type;
	private QuestionApproved right;

	public Question() {

	}

	public Question(String Question, String answer1, String answer2, String answer3, String answer4, String finalanswer, String creator, QuestionType type, QuestionApproved right) {
		this.Question = Question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.FinalAnswer = finalanswer;
		this.creator = creator;
		this.type = type;
		this.right = right;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = question;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getFinalAnswer() {
		return FinalAnswer;
	}

	public void setFinalAnswer(String finalAnswer) {
		FinalAnswer = finalAnswer;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public QuestionType getType(){
		return type;
	}
	
	public void setType(QuestionType type){
		this.type = type;
	}
	
	public QuestionApproved getRight(){
		return right;
	}
	
	public void setRight(QuestionApproved right){
		this.right = right;
	}
}
