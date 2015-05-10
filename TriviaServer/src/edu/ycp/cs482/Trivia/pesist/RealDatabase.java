package edu.ycp.cs482.Trivia.pesist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs482.Trivia.pesist.IDatabase;
import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Model.QuestionApproved;
import edu.ycp.cs482.Model.QuestionType;
import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.Trivia.pesist.DBUtil;

public class RealDatabase implements IDatabase{
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load sqlite driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	public void addUser(final User user) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;
				
				try {
					stmt = conn.prepareStatement(
							"insert into users (username, password) values (?, ?)",
							PreparedStatement.RETURN_GENERATED_KEYS
					);
					
					storeUserNoId(user, stmt, 1);

					// Attempt to insert the item
					stmt.executeUpdate();

					// Determine the auto-generated id
					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted Users");
					}
					
					user.setId(generatedKeys.getInt(1));
					System.out.println("New User has id " + user.getId());
					
					return true;
				} finally {
					DBUtil.closeQuietly(generatedKeys);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean deleteUser(final String user) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("Delete from users where users.username = ?");
					stmt.setString(1, user);
					
					int numRowsAffected = stmt.executeUpdate();
					
					return numRowsAffected != 0;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public User getUser(final String username) {
		return executeTransaction(new Transaction<User>() {
			@Override
			public User execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement("select users.* from users where users.username = ?");
					stmt.setString(1, username);
					
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						// No such item
						return null;
					}
					
					User user = new User();
					loadUser(user, resultSet, 1);
					return user;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public boolean login(String User, String Pass) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement("select users.* from users where users.username = ? and users.password = ?");
					stmt.setString(1, User);
					stmt.setString(2, Pass);
					
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						// No such item
						return false;
					}
					
					return true;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public User chgUser(String oldUser, String newUser) {
		return executeTransaction(new Transaction<User>() {
			@Override
			public User execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update users set users.username = ? where users.username = ?");
					
					stmt.setString(1,  newUser);
					stmt.setString(2, oldUser);
					
					stmt.executeUpdate();
					User user = new User();
					System.out.println("changed username");
					return user;
					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public User chgPass(String oldUser, String newPass) {

		return executeTransaction(new Transaction<User>() {
			@Override
			public User execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update users set users.password = ? where users.username = ?");
					
					stmt.setString(1,  newPass);
					stmt.setString(2, oldUser);
					
					stmt.executeUpdate();
					User user = new User();
					System.out.println("changed password");
					return user;
					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void updateStreak(String username, int Streak) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update users set users.streak = ? where users.username = ? and users.streak < ?");
					
					stmt.setInt(1,  Streak);
					stmt.setString(2, username);
					stmt.setInt(3,  Streak);
					
					stmt.executeUpdate();
					
					System.out.println("updated streak");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void updateRetry(String username, int Retry){
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update users set users.retry = ? where users.username = ?");
					
					stmt.setInt(1,  Retry);
					stmt.setString(2, username);
					
					stmt.executeUpdate();
					
					System.out.println("updated Retry");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void updateCoins(String username, int coins){
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update users set users.coins = ? where users.username = ?");
					
					stmt.setInt(1,  coins);
					stmt.setString(2, username);
					
					stmt.executeUpdate();
					
					System.out.println("updated coins");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public List<User> getAllUser() {
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// Note: no 'where' clause, so all items will be returned
					stmt = conn.prepareStatement("select users.* from users");
					
					resultSet = stmt.executeQuery();

					List<User> result = new ArrayList<User>();
					while (resultSet.next()) {
						User user = new User();
						user.setId(resultSet.getInt(1));
						user.setUsername(resultSet.getString(2));
						user.setPassword(resultSet.getString(3));
						user.setStreak(resultSet.getInt(4));
						user.setRetry(resultSet.getInt(5));
						user.setCoins(resultSet.getInt(6));
						result.add(user);
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Question> getAllQuestion() {
		return executeTransaction(new Transaction<List<Question>>() {
			@Override
			public List<Question> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// Note: no 'where' clause, so all items will be returned
					stmt = conn.prepareStatement("select questions.* from questions");
					resultSet = stmt.executeQuery();

					List<Question> result = new ArrayList<Question>();
					while (resultSet.next()) {
						Question question = new Question();
						question.setId(resultSet.getInt(1));
						question.setQuestion(resultSet.getString(2));
						question.setAnswer1(resultSet.getString(3));
						question.setAnswer2(resultSet.getString(4));
						question.setAnswer3(resultSet.getString(5));
						question.setAnswer4(resultSet.getString(6));
						question.setFinalAnswer(resultSet.getString(7));
						question.setCreator(resultSet.getString(8));
						QuestionType[] typeValues = QuestionType.values();
						QuestionType type = typeValues[resultSet.getInt(9)];
						question.setType(type);
						QuestionApproved[] ApprovedValues = QuestionApproved.values();
						QuestionApproved right = ApprovedValues[resultSet.getInt(10)];
						question.setRight(right);
						result.add(question);
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Question> getAllQuestionPending() {
		return executeTransaction(new Transaction<List<Question>>() {
			@Override
			public List<Question> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// Note: no 'where' clause, so all items will be returned
					stmt = conn.prepareStatement("select questions.* from questions where questions.approved=?");
					stmt.setInt(1, QuestionApproved.PENDING.ordinal());
					
					resultSet = stmt.executeQuery();

					List<Question> result = new ArrayList<Question>();
					while (resultSet.next()) {
						Question question = new Question();
						question.setId(resultSet.getInt(1));
						question.setQuestion(resultSet.getString(2));
						question.setAnswer1(resultSet.getString(3));
						question.setAnswer2(resultSet.getString(4));
						question.setAnswer3(resultSet.getString(5));
						question.setAnswer4(resultSet.getString(6));
						question.setFinalAnswer(resultSet.getString(7));
						question.setCreator(resultSet.getString(8));
						QuestionType[] typeValues = QuestionType.values();
						QuestionType type = typeValues[resultSet.getInt(9)];
						question.setType(type);
						QuestionApproved[] ApprovedValues = QuestionApproved.values();
						QuestionApproved right = ApprovedValues[resultSet.getInt(10)];
						question.setRight(right);
						result.add(question);
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public void addQuestion(Question question) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;
				
				try {
					stmt = conn.prepareStatement(
							"insert into questions (question, answer1, answer2, answer3, answer4, finalanswer, creator, type, approved) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
							PreparedStatement.RETURN_GENERATED_KEYS
					);
					
					storeQuestionNoId(question, stmt, 1);

					// Attempt to insert the item
					stmt.executeUpdate();

					// Determine the auto-generated id
					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted Questions");
					}
					
					question.setId(generatedKeys.getInt(1));
					System.out.println("New Question has id " + question.getId());
					
					return true;
				} finally {
					DBUtil.closeQuietly(generatedKeys);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public Question getQuestion(int id) {
		return executeTransaction(new Transaction<Question>() {
			@Override
			public Question execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement("select questions.* from questions where questions.id = ?");
					stmt.setInt(1, id);
					
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						// No such item
						return null;
					}
					
					Question question = new Question();
					loadQuestion(question, resultSet, 1);
					return question;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Question randomQuestion() {
		return executeTransaction(new Transaction<Question>() {
			@Override
			public Question execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement("select * from questions order by random()");
					
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						// No such item
						return null;
					}
					
					Question question = new Question();
					loadQuestion(question, resultSet, 1);
					return question;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	

	@Override
	public void changeQuestion(int id, String question) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update questions set questions.question = ? where questions.id=?");
					
					stmt.setString(1, question);
					stmt.setInt(2,  id);
					
					stmt.executeUpdate();
					
					System.out.println("updated question");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void changeStatus(int id) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update questions set questions.approved = ? where questions.id=?");
					
					stmt.setInt(1, QuestionApproved.ACCEPTED.ordinal());
					stmt.setInt(2,  id);
					
					stmt.executeUpdate();
					
					System.out.println("updated question");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void changeQuestionpt1(int id, String answer1) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update questions set questions.answer1=? where questions.id=?");
					
					stmt.setString(1, answer1);
					stmt.setInt(2, id);
					
					stmt.executeUpdate();
					
					System.out.println("updated question");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void changeQuestionpt2(int id, String answer2) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update questions set questions.answer2=? where questions.id=?");
					
					stmt.setString(1, answer2);
					stmt.setInt(2,  id);
					
					stmt.executeUpdate();
					
					System.out.println("updated question");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void changeQuestionpt3(int id, String answer3) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update questions set questions.answer3=? where questions.id=?");
					
					stmt.setString(1, answer3);
					stmt.setInt(2,  id);
					
					stmt.executeUpdate();
					
					System.out.println("updated question");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void changeQuestionpt4(int id, String answer4) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update questions set questions.answer4=? where questions.id=?");
					
					stmt.setString(1, answer4);
					stmt.setInt(2,  id);
					
					stmt.executeUpdate();
					
					System.out.println("updated question");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}
	
	@Override
	public void changeQuestionpt5(int id, String answerfinal) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet keys = null;

				try {					
					stmt = conn.prepareStatement("update questions set questions.finalanswer=? where questions.id=?");
					
					stmt.setString(1, answerfinal);
					stmt.setInt(2, id);
					
					stmt.executeUpdate();
					
					System.out.println("updated question");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(keys);
				}
			}
		});
	}

	@Override
	public boolean deleteQuestion(int id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("Delete from questions where questions.id = ?");
					stmt.setInt(1, id);
					
					int numRowsAffected = stmt.executeUpdate();
					
					return numRowsAffected != 0;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public void createUserTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					// Note that the 'id' column is an autoincrement primary key,
					stmt = conn.prepareStatement(
							"create table users (" +
							"  id integer primary key not null generated always as identity," +
							"  username varchar(30)," +
							"  password varchar(30)," +
							"  streak integer default 0," +
							"  retry integer default 0," +
							"  coins integer default 0" +
							")"
					);
					stmt.executeUpdate();			
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}	
	
	public void createQuestionTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;	
				try {
					// Note that the 'id' column is an autoincrement primary key,
					stmt = conn.prepareStatement(
							"create table questions (" +
							"  id integer primary key not null generated always as identity (start with 1, increment by 1)," +
							"  question varchar(100)," +
							"  answer1 varchar(100)," +
							"  answer2 varchar(100)," +
							"  answer3 varchar(100)," +
							"  answer4 varchar(100)," +
							"  finalanswer varchar(100)," +
							"  creator varchar(100)," +
							"  type integer not null default 0," +
							"  approved integer not null default 0" +
							")"
					);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}	
	
	protected void storeUserNoId(User user, PreparedStatement stmt, int index) throws SQLException {
		stmt.setString(index++, user.getUsername());
		stmt.setString(index++, user.getPassword());
	}
	
	protected void storeQuestionNoId(Question question, PreparedStatement stmt, int index) throws SQLException {
		stmt.setString(index++, question.getQuestion());
		stmt.setString(index++, question.getAnswer1());
		stmt.setString(index++, question.getAnswer2());
		stmt.setString(index++, question.getAnswer3());
		stmt.setString(index++, question.getAnswer4());
		stmt.setString(index++, question.getFinalAnswer());
		stmt.setString(index++, question.getCreator());
		stmt.setInt(index++, question.getType().ordinal());
		stmt.setInt(index++, question.getRight().ordinal());
	}
	
	protected void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		user.setId(resultSet.getInt(index++));
		user.setUsername(resultSet.getString(index++));
		user.setPassword(resultSet.getString(index++));
		user.setStreak(resultSet.getInt(index++));
		user.setRetry(resultSet.getInt(index++));
		user.setCoins(resultSet.getInt(index++));
	}
	
	protected void loadQuestion(Question question, ResultSet resultSet, int index) throws SQLException {
		question.setId(resultSet.getInt(index++));
		question.setQuestion(resultSet.getString(index++));
		question.setAnswer1(resultSet.getString(index++));
		question.setAnswer2(resultSet.getString(index++));
		question.setAnswer3(resultSet.getString(index++));
		question.setAnswer4(resultSet.getString(index++));
		question.setFinalAnswer(resultSet.getString(index++));
		question.setCreator(resultSet.getString(index++));
		QuestionType[] typeValues = QuestionType.values();
		QuestionType type = typeValues[resultSet.getInt(index++)];
		question.setType(type);
		QuestionApproved[] ApprovedValues = QuestionApproved.values();
		QuestionApproved right = ApprovedValues[resultSet.getInt(index++)];
		question.setRight(right);
	}
	
	public void loadInitialUserData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("insert into users (username, password) values (?, ?)");
					storeUserNoId(new User("Master","Master"), stmt, 1);
					stmt.addBatch();
					storeUserNoId(new User("Cody","Cody"), stmt, 1);
					stmt.addBatch();
					storeUserNoId(new User("Jason","Jason"), stmt, 1);
					stmt.addBatch();
					storeUserNoId(new User("Babcock","Babcock"), stmt, 1);
					stmt.addBatch();
					
					stmt.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void loadInitialQuestionData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("insert into questions (question, answer1, answer2, answer3, answer4, finalanswer, creator, type, approved) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
					storeQuestionNoId(new Question("What is 2 + 2?","2","4","6","Moscola","4", "Cody", QuestionType.SCIENCE, QuestionApproved.PENDING), stmt, 1);
					stmt.addBatch();
					storeQuestionNoId(new Question("Who won Super Bowl I?","Packers","Seahawks","Brown","Giants","Packers", "Jason", QuestionType.SPORTS, QuestionApproved.ACCEPTED), stmt, 1);
					stmt.addBatch();
					storeQuestionNoId(new Question("What color is George Washington's white horse?","Pink","Brown","George Washington didn't have a horse", "White", "White","Jason", QuestionType.ETC, QuestionApproved.ACCEPTED), stmt, 1);
					stmt.addBatch();
					
					stmt.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public static void main(String[] args) {
		RealDatabase db = new RealDatabase();
		System.out.println("Creating tables...");
		db.createUserTables();
		db.createQuestionTables();
		System.out.println("Loading initial data...");
		db.loadInitialUserData();
		db.loadInitialQuestionData();
		System.out.println("Done!");
	}
}
