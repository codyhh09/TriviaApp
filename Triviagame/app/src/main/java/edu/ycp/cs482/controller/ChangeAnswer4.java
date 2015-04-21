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

public class ChangeAnswer4 extends AsyncTask<String, Void, Boolean> {
    public boolean changeanswer4(int id, String answer4)throws URISyntaxException, IOException {
        HttpClient client = new DefaultHttpClient();
        // Construct request
        if(answer4.contains(" "))
            answer4 = answer4.replace(" ", "_");

        HttpPut request = new HttpPut("http://10.0.2.2:8081/question/"+id+"/answer4="+answer4);

        StringWriter sw = new StringWriter();
        JSON.getObjectMapper().writeValue(sw, answer4);

        // Add JSON object to request
        StringEntity reqEntity = new StringEntity(sw.toString());
        reqEntity.setContentType("application/json");
        request.setEntity(reqEntity);
        // Execute request
        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            return true;
        else
            return false;
    }
    @Override
    protected Boolean doInBackground(String... params) {
        try{
            return changeanswer4(Integer.parseInt(params[0]), params[1]);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
