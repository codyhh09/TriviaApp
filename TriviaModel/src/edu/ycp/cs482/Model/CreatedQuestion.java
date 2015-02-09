package edu.ycp.cs482.Model;

public class CreatedQuestion {
	private int id;
	private int userid;
	private int questionid;

	public CreatedQuestion() {

	}

	public CreatedQuestion(int id, int userid, int questionid) {
		this.id = id;
		this.userid = userid;
		this.questionid = questionid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
}