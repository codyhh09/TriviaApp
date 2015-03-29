package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.DeleteUser;

/**
 * Created by choward8 on 3/23/2015.
 */

public class Settings extends Activity {
    private Button delete, change;
    private TextView name, pass;
    private Bundle extras;
    private String username;
    private DeleteUser controller;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
        }
        setContentView(R.layout.settings);
        delete = (Button) findViewById(R.id.deleteBtn);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = new DeleteUser();
                try{
                    controller.execute(username);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
