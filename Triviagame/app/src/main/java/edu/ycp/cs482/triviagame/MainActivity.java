package edu.ycp.cs482.triviagame;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import edu.ycp.cs482.controller.LoginUser;

public class MainActivity extends ActionBarActivity{
    private String name, password;
    private int counter = 3;
    private EditText name1, password1;
    private TextView attemps;
    private Button Signup,Signin;
    private Intent i;
    private boolean lose = false;
    public static final String Attempts = "Attempts left: ";
    private boolean truth;
    private CountDownTimer timer;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            counter = savedInstanceState.getInt("Count");
        }
        setContentView(R.layout.login_page);
        name1 = (EditText) findViewById(R.id.txtUser);
        password1 = (EditText) findViewById(R.id.txtPass);
        Signup = (Button) findViewById(R.id.btnSignUp);
        Signin = (Button) findViewById(R.id.signin);
        attemps = (TextView) findViewById(R.id.attempt);

        attemps.setText(Attempts + Integer.toString(counter));

        Signin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                name = name1.getText().toString();
                password = password1.getText().toString();
                LoginUser controller = new LoginUser();
                try{
                    truth = controller.execute(name,password).get();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(truth) {
                    if(name.equals("Master")){
                        i = new Intent(getApplicationContext(), ModPage.class);
                        startActivity(i);
                    }else {
                        i = new Intent(getApplicationContext(), MenuPage.class);
                        i.putExtra("name", name);
                        i.putExtra("lose", lose);
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",
                            Toast.LENGTH_SHORT).show();
                    attemps.setBackgroundColor(Color.RED);
                    counter--;
                    attemps.setText(Attempts + Integer.toString(counter));
                    if(counter==0){
                        SignbackIn();
                    }

                }
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });
    }

    private void SignbackIn(){

        Signin.setEnabled(false);
        Signup.setEnabled(false);

        Long alert = new GregorianCalendar().getTimeInMillis()+5*1000;
        Long millisUntilFinished = alert - System.currentTimeMillis();
        i = new Intent(this, AlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alert, PendingIntent.getBroadcast(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT));

        timer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                attemps.setText(""+String.format("%d min : %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                Signin.setEnabled(true);
                Signup.setEnabled(true);
                counter+=2;
                attemps.setText(Attempts + Integer.toString(counter));
            }
        }.start();

        /*countDownTimer = new CountDownTimer(5000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                attemps.setText(""+String.format("%d min : %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                moveTaskToBack(true);
            }

            public void onFinish()
            {
                Signin.setEnabled(true);
                Signup.setEnabled(true);
                attemps.setBackgroundColor(Color.WHITE);
                counter = 2;
                attemps.setText(Attempts + Integer.toString(counter));
                moveTaskToBack(false);
                notification();
            }
        }.start();*/
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("Count", counter);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("Count");
    }

    @Override
         public void onBackPressed() {
        //super.onBackPressed();
    }

}
