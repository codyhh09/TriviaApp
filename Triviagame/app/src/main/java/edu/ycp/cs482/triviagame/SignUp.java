package edu.ycp.cs482.triviagame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ycp.cs482.controller.AddUser;
import edu.ycp.cs482.controller.GetUser;

public class SignUp extends ActionBarActivity {
    // Progress Dialog
    private EditText inputName, inputPass, inputcheckPass;
    private String name, password, checkpass;
    private GetUser getUser = new GetUser();
    private Button btnuser, btnback;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        getSupportActionBar().setIcon(R.drawable.ic_action_help);

        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // Edit Text
        inputName = (EditText) findViewById(R.id.txtUser);
        inputPass = (EditText) findViewById(R.id.txtPass);
        inputcheckPass = (EditText) findViewById(R.id.txtConfirm);

        // Create button
        btnuser = (Button) findViewById(R.id.btnEnter);
        btnback = (Button) findViewById(R.id.btnBack);

        // button click event
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating new product in background thread
                name = inputName.getText().toString();
                password = inputPass.getText().toString();
                checkpass = inputcheckPass.getText().toString();
                try {
                    if(name!=null||password!=null||password==checkpass) {
                        AddUser controller = new AddUser();

                        if (controller.execute(name, password) != null) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignUp.this, "Welcome new User", Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Toast.makeText(SignUp.this, "Need to fill in all fields", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

       btnback.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(i);
           }
       });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}