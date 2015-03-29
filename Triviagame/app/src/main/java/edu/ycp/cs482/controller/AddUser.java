package edu.ycp.cs482.controller;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.JSON.*;

public class AddUser extends AsyncTask<String, Void, Boolean>{
    public boolean postUser(String Username, String Password) throws URISyntaxException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();

        // Construct request
        HttpPost request = new HttpPost("http://10.0.2.2:8081/user");

        if(Username != null && Password != null){
            User user = new User(Username, Password);
            StringWriter sw = new StringWriter();
            JSON.getObjectMapper().writeValue(sw, user);

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

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            return postUser(params[0], params[1]);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}