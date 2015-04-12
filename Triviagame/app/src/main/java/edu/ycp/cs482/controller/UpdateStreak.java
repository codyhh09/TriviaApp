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

public class UpdateStreak extends AsyncTask<String,Void,Void>{
    public void updateStreak(String oldUser, int Streak)throws URISyntaxException, IOException {
        HttpClient client = new DefaultHttpClient();
        // Construct request
        HttpPut request = new HttpPut("http://10.0.2.2:8081/user/"+oldUser+"/streak="+Streak);

        StringWriter sw = new StringWriter();
        JSON.getObjectMapper().writeValue(sw, Streak);

        // Add JSON object to request
        StringEntity reqEntity = new StringEntity(sw.toString());
        reqEntity.setContentType("application/json");
        request.setEntity(reqEntity);
        // Execute request
        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

        }

    }

    protected Void doInBackground(String... params) {
        try{
            updateStreak(params[0], Integer.parseInt(params[1]));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
