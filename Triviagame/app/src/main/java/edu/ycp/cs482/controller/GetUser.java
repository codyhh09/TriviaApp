package edu.ycp.cs482.controller;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

import edu.ycp.cs482.JSON.JSON;
import edu.ycp.cs482.Model.User;

public class GetUser extends AsyncTask<String, Void, User> {
    private Context con;

    public GetUser(Context context){
        con = context;
    }

    private User getUser(String Username) throws URISyntaxException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();
        // Construct request
        HttpGet request = new HttpGet("http://10.0.2.2:8081/user/"+Username);

        // Execute request
        HttpResponse response = client.execute(request);
        // Parse response
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // Add JSON object to request
            HttpEntity entity = response.getEntity();
            // Parse JSON
            return JSON.getObjectMapper().readValue(entity.getContent(), User.class);
        }
        // Return null if invalid response
        return null;
    }

    @Override
    protected User doInBackground(String... params) {
        try{
            return getUser(params[0]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
