package edu.ycp.cs482.triviagame;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.LoginUser;

public class MainActivity extends ActionBarActivity {
    private String name, password;
    private int counter = 3;
    private EditText name1, password1;
    private CountDownTimer countDownTimer;
    private TextView attemps;
    private Button Signup,Signin;
    private Intent i;
    private Bundle b;
    private boolean lose = false;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userkey = "nameKey";
    public static final String Attempts = "Attempts left: ";
    SharedPreferences sharedpreferences;
    private boolean truth;
    final int TIMER = 3600000;
    private NotificationManager nm;

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
    protected void onResume() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedpreferences.contains(userkey)){
            i = new Intent(getApplicationContext(), MenuPage.class);
            i.putExtra("name", name);
            startActivity(i);
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        i = new Intent(getApplicationContext(), MenuPage.class);
                        editor.putString("name", name);
                        editor.commit();
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
                        Signin.setEnabled(false);
                        Signup.setEnabled(false);
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
        countDownTimer = new CountDownTimer(TIMER, 1000)
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
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
