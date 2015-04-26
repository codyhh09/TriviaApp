package edu.ycp.cs482.triviagame;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.MenuInflater;
import android.support.v7.app.ActionBarActivity;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

//This page is where the questions will be displayed and answered.  There is a timer to countdown time.
public class MenuPage extends ActionBarActivity {
    private Button startgame, stat;
    private TextView Again;
    private Bundle extras;
    private int streak = 1;
    private Intent i;
    private String username;
    private boolean lose = false;
    private CountDownTimer timer;

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
        stat = (Button) findViewById(R.id.btnStats);
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

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), StatPage.class);
                i.putExtra("name", username);
                i.putExtra("lose", lose);
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
                i.putExtra("lose", lose);
                startActivity(i);
                return true;
            case R.id.addQ:
                i = new Intent(getApplicationContext(), MakeQuestion.class);
                i.putExtra("name", username);
                i.putExtra("lose", lose);
                startActivity(i);
                return true;
            case R.id.log_out:
                //METHOD THAT EXECUTES LOG-OUT SEQUENCE GOES HERE!
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Playagain() {
        Long alert = new GregorianCalendar().getTimeInMillis()+5*1000;
        Long millisUntilFinished = alert - System.currentTimeMillis();
        i = new Intent(this, GameAlertReciever.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alert, PendingIntent.getBroadcast(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT));
        timer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Again.setText(""+String.format("%d min : %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
