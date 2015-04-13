package edu.ycp.cs482.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuInflater;
import android.support.v7.app.ActionBarActivity;

import java.util.concurrent.TimeUnit;

//This page is where the questions will be displayed and answered.  There is a timer to countdown time.
public class MenuPage extends ActionBarActivity {
    private Button startgame;
    private TextView Again;
    private Bundle extras;
    private int streak = 1;
    private Intent i;
    private String username;
    private boolean lose = false;
    final int TIMER = 3600000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
            lose = extras.getBoolean("lose");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
            lose = (boolean) savedInstanceState.getSerializable("lose");
        }
        setContentView(R.layout.activity_main);
        startgame = (Button) findViewById(R.id.btnStart);
        Again = (TextView) findViewById(R.id.Resumetxt);


        if(lose){
            Again.setVisibility(View.VISIBLE);
            startgame.setEnabled(false);
            Playagain();
        }else{
            Again.setVisibility(View.INVISIBLE);
            startgame.setEnabled(true);
        }

        startgame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), GamePage.class);
                i.putExtra("name", username);
                i.putExtra("streak", streak);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        getSupportActionBar().setIcon(R.drawable.ic_action_help);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.settings:
                i = new Intent(getApplicationContext(), Settings.class);
                i.putExtra("name", username);
                startActivity(i);
                return true;
            case R.id.addQ:
                i = new Intent(getApplicationContext(), MakeQuestion.class);
                i.putExtra("name", username);
                startActivity(i);
                return true;
            case R.id.main:
                i = new Intent(getApplicationContext(), MenuPage.class);
                i.putExtra("name", username);
                startActivity(i);
                return true;
            case R.id.log_out:
                //METHOD THAT EXECUTES LOG-OUT SEQUENCE GOES HERE!
                i = new Intent(getApplicationContext(), MainActivity.class);
                SharedPreferences sharedpreferences = getSharedPreferences
                (MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                moveTaskToBack(true);
                MenuPage.this.finish();
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Playagain() {
        new CountDownTimer(TIMER, 1000) {
            public void onTick(long millisUntilFinished) {
                Again.setText(Again.getText().toString().substring(0, 14) + " " + String.format("%d min : %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                startgame.setEnabled(true);
                Again.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
