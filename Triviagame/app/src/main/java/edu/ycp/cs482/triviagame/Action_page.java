package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ycp.cs482.Model.User;

/**
 * Created by choward8 on 3/21/2015.
 */
public class Action_page extends Activity {
    private Button Signout, setting, startgame;
    private Bundle extras;
    private Intent i;
    private String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
        }

        Toast.makeText(Action_page.this,username,Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        Signout = (Button) findViewById(R.id.btnSignOut);
        setting = (Button) findViewById(R.id.btnSettings);
        startgame = (Button) findViewById(R.id.btnStart);

        Signout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), Settings.class);
                i.putExtra("name", username);
                startActivity(i);
            }
        });

        startgame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), GamePage.class);
                startActivity(i);
            }
        });
    }

}
