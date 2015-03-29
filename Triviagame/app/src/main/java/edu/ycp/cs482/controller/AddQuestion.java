package edu.ycp.cs482.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import edu.ycp.cs482.JSON.JSON;
import edu.ycp.cs482.Model.Question;

public class AddQuestion {
    public boolean postQuestion(String question, String answer1, String answer2, String answer3, String answer4, String finalanswer) throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
        return makePostRequest(question, answer1, answer2, answer3, answer4, finalanswer);
    }

    public boolean makePostRequest(String question, String answer1, String answer2, String answer3, String answer4, String finalanswer) throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();

        // Construct URI
        URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/question", null, null);

        // Construct request
        HttpPost request = new HttpPost(uri);

        if(question != null && answer1 != null && answer2 != null && answer3 != null && answer4 != null && finalanswer != null){
            Question questions = new Question(question, answer1, answer2, answer3, answer4, finalanswer);
            StringWriter sw = new StringWriter();
            JSON.getObjectMapper().writeValue(sw, questions);

            // Add JSON object to request
            StringEntity reqEntity = new StringEntity(sw.toString());
            reqEntity.setContentType("application/json");
            request.setEntity(reqEntity);

            // Execute request
            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }
}
