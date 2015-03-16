package edu.ycp.cs482.Trivia.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
	private static final ObjectMapper theObjectMapper = new ObjectMapper();
	
	public static ObjectMapper getObjectMapper() {
		return theObjectMapper;
	}
}
