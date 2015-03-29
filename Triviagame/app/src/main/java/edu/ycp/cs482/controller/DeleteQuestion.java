package edu.ycp.cs482.controller;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeleteQuestion extends AsyncTask<Integer, Void, Boolean>{
    private boolean deleteQuestion(int id) throws URISyntaxException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();
        // Construct request
        HttpDelete request = new HttpDelete("http://10.0.2.2:8081/question/"+id);

        // Execute request
        HttpResponse response = client.execute(request);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            return true;
        else
            return false;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        try{
            return deleteQuestion(params[0]);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
