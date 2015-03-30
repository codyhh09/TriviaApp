package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ycp.cs482.controller.AddUser;

public class SignUp extends Activity {
    // Progress Dialog
    private EditText inputName, inputPass;
    private String name, password;

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
                //AddUser controller = new AddUser();
                try {
                    if (/*controller.execute(name, password) != null*/true) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(SignUp.this, "Welcome new User", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}