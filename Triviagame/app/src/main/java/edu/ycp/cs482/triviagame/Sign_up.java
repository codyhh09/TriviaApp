package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ycp.cs482.controller.AddUser;

/**
 * Created by choward8 on 3/21/2015.
 */
public class Sign_up extends Activity{
    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPass;
    String name, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // Edit Text
        inputName = (EditText) findViewById(R.id.txtUser);
        inputPass = (EditText) findViewById(R.id.txtPass);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnEnter);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating new product in background thread
                name = inputName.getText().toString();
                password = inputPass.getText().toString();
                AddUser controller = new AddUser();
                try{
                    if(controller.execute(name, password)!=null){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(Sign_up.this, "Welcome new User", Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
//    class CreateNewUser extends AsyncTask<String, Integer, User> {
//        String currectText;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected User doInBackground(String... params) {
//           try {
//               HttpClient client = new DefaultHttpClient();
//               HttpPost post = new HttpPost(params[0]);
//               HttpResponse response = client.execute(post);
//
//               int status = response.getStatusLine().getStatusCode();
//               if (status == 200) {
//                   HttpEntity entity = response.getEntity();
//                   String data = EntityUtils.toString(entity);
//
//                   JSONObject jObj = new JSONObject(data);
//                   JSONArray jArray = jObj.getJSONArray("users");
//
//                   for(int i=0; i<jArray.length(); i++){
//                       User user = new User();
//
//                       JSONObject jRealObject = jArray.getJSONObject(i);
//                   }
//
//               }
//           }catch(Exception e){
//               e.printStackTrace();
//           }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(User user) {
//            super.onPostExecute(user);
//        }
//    }
 }
