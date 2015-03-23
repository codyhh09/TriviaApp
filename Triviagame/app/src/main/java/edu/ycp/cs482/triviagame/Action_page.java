package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by choward8 on 3/21/2015.
 */
public class Action_page extends Activity {
    private Button Signout, setting, startgame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        startgame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GamePage.class);
                startActivity(i);
            }
        });
    }

}
