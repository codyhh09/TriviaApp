package edu.ycp.cs482.controller;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;

import edu.ycp.cs482.JSON.JSON;
import edu.ycp.cs482.Model.User;

public class ChangePassword extends AsyncTask<String, Void, User> {
    public User changePass(String oldUser, String newPass)throws URISyntaxException, IOException {
        HttpClient client = new DefaultHttpClient();
        User user = new User();
        // Construct request
        HttpPut request = new HttpPut("http://10.0.2.2:8081/user/"+oldUser+"/pass="+newPass);

        // Create JSON object from user
        user.setPassword(newPass);

        StringWriter sw = new StringWriter();
        JSON.getObjectMapper().writeValue(sw, user);

        // Add JSON object to request
        StringEntity reqEntity = new StringEntity(sw.toString());
        reqEntity.setContentType("application/json");
        request.setEntity(reqEntity);
        // Execute request
        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            return user;
        else
            return user;
    }
    @Override
    protected User doInBackground(String... params) {
        try{
            return changePass(params[0], params[1]);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
