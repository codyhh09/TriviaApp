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

/**
 * Created by choward8 on 3/23/2015.
 */
public class LoginUser extends AsyncTask<String, Void, Boolean> {
    private Boolean Loguser(String Username, String Pass) throws URISyntaxException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();
        // Construct request
        HttpGet request = new HttpGet("http://10.0.2.2:8081/user/"+Username+"+"+Pass);

        // Execute request
        HttpResponse response = client.execute(request);
        // Parse response
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // Add JSON object to request
            HttpEntity entity = response.getEntity();
            /* Parse JSON */
            return JSON.getObjectMapper().readValue(entity.getContent(), Boolean.class);
        }
        // Return null if invalid response
        return null;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            return Loguser(params[0], params[1]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
