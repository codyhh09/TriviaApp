package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.controller.GetQuestion;

/**
 * Created by choward8 on 3/23/2015.
 */
public class GamePage extends Activity {
    private int streak = 0;
    private TextView question;
    private Button AnswerA, AnswerB, AnswerC, AnswerD;
    private Question q;
    final int TIMER = 21000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_page);
        GetQuestion controller = new GetQuestion();
        q = new Question();
        question = (TextView) findViewById(R.id.lblQuestion);
        AnswerA = (Button) findViewById(R.id.btnAnsA);
        AnswerB = (Button) findViewById(R.id.btnAnsB);
        AnswerC = (Button) findViewById(R.id.btnAnsC);
        AnswerD = (Button) findViewById(R.id.btnAnsD);



        try {
            q = controller.execute(1).get();
        }catch(Exception e) {
            e.printStackTrace();
        }
        question.setText(q.getQuestion());
        AnswerA.setText(q.getAnswer1());
        AnswerB.setText(q.getAnswer2());
        AnswerC.setText(q.getAnswer3());
        AnswerD.setText(q.getAnswer4());

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
                Intent i = new Intent(getApplicationContext(), Action_page.class);
                startActivity(i);
            }
        }.start();

        AnswerA.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                //Temporary CORRECT Answer
                if(q.getFinalAnswer().equals(AnswerA.getText().toString())){
                    streak++;
                    Toast.makeText(GamePage.this, "Correct! Streak: " + streak, Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    startActivity(i);
                }
            }
        });

        AnswerB.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(q.getFinalAnswer().equals(AnswerB.getText().toString())){
                    streak++;
                    Toast.makeText(GamePage.this, "Correct! Streak: " + streak, Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    startActivity(i);
                }
            }
        });

        AnswerC.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(q.getFinalAnswer().equals(AnswerA.getText().toString())){
                    streak++;
                    Toast.makeText(GamePage.this, "Correct! Streak: " + streak, Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    startActivity(i);
                }
            }
        });

        AnswerD.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(q.getFinalAnswer().equals(AnswerA.getText().toString())){
                    streak++;
                    Toast.makeText(GamePage.this, "Correct! Streak: " + streak, Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    startActivity(i);
                }
            }
        });
    }
}
