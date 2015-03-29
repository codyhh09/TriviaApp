package edu.ycp.cs482.controller;

/**
 * Created by choward8 on 3/15/2015.
 */

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;


public class DeleteUser extends AsyncTask<String, Void, Boolean>{
    @Override
    protected Boolean doInBackground(String... params) {
        try{
            return deleteUser(params[0]);
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    private Boolean deleteUser(String userName) throws URISyntaxException, ClientProtocolException, IOException {
        // Implement method to issue delete inventory request
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();

        // Construct request
        HttpDelete request = new HttpDelete("http://10.0.2.2:8081/user/"+userName);

        // Execute request
        HttpResponse response = client.execute(request);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            return true;
        else
            return false;
    }
}
