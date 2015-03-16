package edu.ycp.cs482.triviagame;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.widget.ActionMenuView;


public class MainActivity extends Activity {

    private int streak = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.btnSettings:
                setContentView(R.layout.settings);
                return true;
            case R.id.btnSignOut:
                setContentView(R.layout.login_page);
                return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDefaultView()
    {
        setContentView(R.layout.login_page);

        Button Signin = (Button) findViewById(R.id.signin);
        Button Signup = (Button) findViewById(R.id.btnSignUp);
        Button Settings = (Button) findViewById(R.id.btnSettings);

        Signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // setting a new account to the Database.
               setSignUpPage();
            }
        });

        Signin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // setting a new account to the Database.
                setMainPage();
            }
        });
    }

    //This page is where the questions will be displayed and answered.  There is a timer to countdown time.
    public void setActionPage()
    {
        setContentView(R.layout.action_page);

        final int TIMER = 21000;

        Button AnswerA = (Button) findViewById(R.id.btnAnsA);
        Button AnswerB = (Button) findViewById(R.id.btnAnsB);
        Button AnswerC = (Button) findViewById(R.id.btnAnsC);
        Button AnswerD = (Button) findViewById(R.id.btnAnsD);

        final CountDownTimer timer = new CountDownTimer(TIMER, 1000)
        {
            TextView time = (TextView)findViewById(R.id.lblTimer);
            public void onTick(long millisUntilFinished)
            {
                time.setText("Time Remaining: " + (int) millisUntilFinished/ 1000);
            }

            public void onFinish()
            {
                //Time-out Pop-Up
                Toast.makeText(MainActivity.this, "Time Up!", Toast.LENGTH_SHORT).show();
                setMainPage();
            }
        }.start();

        AnswerA.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                //Temporary CORRECT Answer
                streak++;
                Toast.makeText(MainActivity.this, "Correct! Streak: " + streak, Toast.LENGTH_SHORT).show();
                timer.cancel();
                //wait(3000); WHAT d0 here? We want to wait 3 seconds before loading the next question.
                setActionPage();
            }
        });

        AnswerB.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                streak = 0;
                Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                timer.cancel();
                setMainPage();
            }
        });

        AnswerC.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                streak = 0;
                Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                timer.cancel();
                setMainPage();
            }
        });

        AnswerD.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                streak = 0;
                Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                timer.cancel();
                setMainPage();
            }
        });
    }

    public void setMainPage()
    {
        setContentView(R.layout.activity_main);

        Button back = (Button) findViewById(R.id.btnBack);
        Button start = (Button) findViewById(R.id.btnStart);
        Button settings = (Button) findViewById(R.id.btnSettings);
        Button signOut = (Button) findViewById(R.id.btnSignOut);

//        final ImageButton popup = (ImageButton) findViewById(R.id.popup);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setDefaultView();
            }
        });
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setActionPage();
            }
        });
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setSettingsPage();
            }
        });
        signOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setDefaultView();
            }
        });

//        popup.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public  void onClick(View v){
//                popup.getMenuInflater().inflate(R.id.popup, popup.getMenu());
//            }
//        });
    }

    public void setSettingsPage()
    {
        setContentView(R.layout.settings);

        Button back = (Button) findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setMainPage();
            }
        });
    }

    public void setSignUpPage()
    {
        setContentView(R.layout.sign_up);

        Button enter = (Button) findViewById(R.id.btnEnter);
        Button back = (Button) findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setDefaultView();
            }
        });

        enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                EditText username = (EditText) findViewById(R.id.txtUser);

                String user = username.getText().toString();

                setMainPage();
                Toast.makeText(MainActivity.this, "Welcome! " + user, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
