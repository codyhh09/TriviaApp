package edu.ycp.cs482.Trivia.pesist;
/**
 * Allow access to the singleton {@link IDatabase} implementation.
 */
public class Database {
	private static final IDatabase theInstance = new RealDatabase();//new FakeDatabase();
	
	/**
	 * Get the singleton {@link IDatabase} implementation.
	 * 
	 * @return the singleton {@link IDatabase} implementation
	 */
	public static IDatabase getInstance() {
		return theInstance;
	}
}
