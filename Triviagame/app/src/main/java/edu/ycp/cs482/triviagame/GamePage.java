package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.ChangeCoins;
import edu.ycp.cs482.controller.GerRandomQuestion;
import edu.ycp.cs482.controller.GetQuestion;
import edu.ycp.cs482.controller.GetUser;
import edu.ycp.cs482.controller.UpdateRetry;
import edu.ycp.cs482.controller.UpdateStreak;

public class GamePage extends ActionBarActivity {
    private int streak;
    private String username;
    private TextView question, currstreak, creator;
    private boolean lose = false;
    private Button AnswerA, AnswerB, AnswerC, AnswerD;
    private Question q = new Question();
    private Intent i;
    private Bundle extras;
    private User user = new User();
    private GerRandomQuestion controller = new GerRandomQuestion();
    private UpdateStreak updateStreak = new UpdateStreak();
    private GetUser getUser = new GetUser();
    private ChangeCoins changeCoins = new ChangeCoins();
    private UpdateRetry updateRetry = new UpdateRetry();
    final int TIMER = 21000;


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
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
            streak = extras.getInt("streak");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
            streak = (Integer) savedInstanceState.getSerializable("streak");
        }

        setContentView(R.layout.action_page);
        currstreak = (TextView) findViewById(R.id.lblStreak);
        question = (TextView) findViewById(R.id.lblQuestion);
        creator = (TextView) findViewById(R.id.lblOwner);
        AnswerA = (Button) findViewById(R.id.btnAnsA);
        AnswerB = (Button) findViewById(R.id.btnAnsB);
        AnswerC = (Button) findViewById(R.id.btnAnsC);
        AnswerD = (Button) findViewById(R.id.btnAnsD);

        try {
            q = controller.execute().get();
            user = getUser.execute(username).get();
        }catch(Exception e) {
            e.printStackTrace();
        }
        question.setText(q.getQuestion());
        AnswerA.setText(q.getAnswer1());
        AnswerB.setText(q.getAnswer2());
        AnswerC.setText(q.getAnswer3());
        AnswerD.setText(q.getAnswer4());
        creator.setText(creator.getText().toString() + q.getCreator());
        currstreak.setText(currstreak.getText().toString() + streak);
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
                Toast.makeText(GamePage.this, "Time Up!", Toast.LENGTH_SHORT).show();
                if(user.getRetry()>0) {
                    wronganswer();
                }else{
                    lose = true;
                    updateStreak.execute(username, Integer.toString(streak));
                    i = new Intent(getApplicationContext(), MenuPage.class);
                    i.putExtra("name", username);
                    i.putExtra("lose", lose);
                    startActivity(i);
                }
            }
        }.start();

        AnswerA.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                answerQuestion(AnswerA, timer, q);
            }
        });

        AnswerB.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                answerQuestion(AnswerB, timer, q);
            }
        });

        AnswerC.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                answerQuestion(AnswerC, timer, q);
            }
        });

        AnswerD.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){answerQuestion(AnswerD, timer, q);
            }
        });
    }

    public void answerQuestion(Button btn, CountDownTimer timer, Question q){
        if(q.getFinalAnswer().equals(btn.getText().toString())){
            streak++;
            timer.cancel();
            if(streak%5==0){
                changeCoins.execute(username, Integer.toString(user.getCoins()+1));
            }
            Intent i = new Intent(getApplicationContext(), GamePage.class);
            i.putExtra("name", username);
            i.putExtra("streak", streak);
            startActivity(i);
        }else{
            if(user.getRetry()>0) {
                timer.cancel();
                wronganswer();
            }else {
                Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                timer.cancel();
                lose = true;
                updateStreak.execute(username, Integer.toString(streak));
                Intent i = new Intent(getApplicationContext(), MenuPage.class);
                i.putExtra("name", username);
                i.putExtra("lose", lose);
                startActivity(i);
            }
        }
    }

    public void wronganswer(){
        //Put up the Yes/No message box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Would you like to use one of your retries")
                .setMessage("Are you sure?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        updateRetry.execute(username, Integer.toString(user.getRetry()-1));
                        Intent i = new Intent(getApplicationContext(), GamePage.class);
                        i.putExtra("name", username);
                        i.putExtra("streak", streak);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), MenuPage.class);
                        i.putExtra("name", username);
                        i.putExtra("lose", lose);
                        startActivity(i);
                    }
                })
                .show();
    }

}
