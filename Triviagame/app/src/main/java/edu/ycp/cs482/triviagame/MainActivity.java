package edu.ycp.cs482.triviagame;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;


public class MainActivity extends ActionBarActivity {

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDefaultView(){
        setContentView(R.layout.login_page);

        Button Signin = (Button) findViewById(R.id.signin);
        Button Signup = (Button) findViewById(R.id.button2);
        Button Settings = (Button) findViewById(R.id.btnSettings);

        Signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // setting a new account to the Database.
               setMainPage();



            }
        });
    }

    /*This page is where the questions will be displayed and answered.  There is a timer to countdown time.*/
    public void setActionPage()
    {
        setContentView(R.layout.action_page);

        final int TIMER = 20000;

        Button AnswerA = (Button) findViewById(R.id.btnAnsA);
        Button AnswerB = (Button) findViewById(R.id.btnAnsB);
        Button AnswerC = (Button) findViewById(R.id.btnAnsC);
        Button AnswerD = (Button) findViewById(R.id.btnAnsD);

        final TextView time = (TextView)findViewById(R.id.lblTimer);

        final CountDownTimer timer = new CountDownTimer(TIMER, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                time.setText((int) millisUntilFinished);
            }

            public void onFinish()
            {
                //Time-out Pop-Up
                Toast.makeText(MainActivity.this, "Time Up!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void setMainPage(){
        setContentView(R.layout.activity_main);

        Button back = (Button) findViewById(R.id.button);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setDefaultView();
            }
        });
    }
}
