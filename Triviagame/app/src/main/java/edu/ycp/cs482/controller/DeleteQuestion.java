package edu.ycp.cs482.controller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DeleteQuestion {
    public boolean deleteQuestion(int id) throws ClientProtocolException, URISyntaxException, IOException {
        return makeDeleteRequest(id);
    }

    private boolean makeDeleteRequest(int id) throws URISyntaxException, ClientProtocolException, IOException {
        // Implement method to issue delete inventory request
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();

        // Construct URI
        URI uri;
        uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/question/" + id, null, null);

        // Construct request
        HttpDelete request = new HttpDelete(uri);

        // Execute request
        HttpResponse response = client.execute(request);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            return true;
        else
            return false;
    }
}
