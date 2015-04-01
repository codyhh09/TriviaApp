package edu.ycp.cs482.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.MenuInflater;
import android.support.v7.app.ActionBarActivity;

//This page is where the questions will be displayed and answered.  There is a timer to countdown time.
public class MenuPage extends ActionBarActivity {
    private Button Signout, addQuestion, startgame;
    private Bundle extras;
    private int streak = 1;
    private Intent i;
    private String username;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                i = new Intent(getApplicationContext(), Settings.class);
                i.putExtra("name", username);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
        }

        setContentView(R.layout.activity_main);
        Signout = (Button) findViewById(R.id.btnSignOut);
        startgame = (Button) findViewById(R.id.btnStart);
        addQuestion = (Button) findViewById(R.id.btnAddQues);

        Signout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        startgame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), GamePage.class);
                i.putExtra("name", username);
                i.putExtra("streak", streak);
                startActivity(i);
            }
        });

        addQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), MakeQuestion.class);
                i.putExtra("name", username);
                startActivity(i);
            }
        });
    }
}
