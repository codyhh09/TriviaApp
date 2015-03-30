package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private int streak;
    private String username;
    private TextView question, currstreak;
    private Button AnswerA, AnswerB, AnswerC, AnswerD;
    private Question q;
    private Intent i;
    private Bundle extras;
    final int TIMER = 21000;

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
        GetQuestion controller = new GetQuestion();
        q = new Question();
        currstreak = (TextView) findViewById(R.id.lblStreak);
        question = (TextView) findViewById(R.id.lblQuestion);
        AnswerA = (Button) findViewById(R.id.btnAnsA);
        AnswerB = (Button) findViewById(R.id.btnAnsB);
        AnswerC = (Button) findViewById(R.id.btnAnsC);
        AnswerD = (Button) findViewById(R.id.btnAnsD);

        try {
            q = controller.execute().get();
        }catch(Exception e) {
            e.printStackTrace();
        }
        question.setText(q.getQuestion());
        AnswerA.setText(q.getAnswer1());
        AnswerB.setText(q.getAnswer2());
        AnswerC.setText(q.getAnswer3());
        AnswerD.setText(q.getAnswer4());
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
                i = new Intent(getApplicationContext(), Action_page.class);
                i.putExtra("name", username);
                startActivity(i);
            }
        }.start();

        AnswerA.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(q.getFinalAnswer().equals(AnswerA.getText().toString())){
                    streak++;
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    i.putExtra("name", username);
                    i.putExtra("streak", streak);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    i.putExtra("name", username);
                    startActivity(i);
                }
            }
        });

        AnswerB.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(q.getFinalAnswer().equals(AnswerB.getText().toString())){
                    streak++;
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    i.putExtra("name", username);
                    i.putExtra("streak", streak);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    i.putExtra("name", username);
                    startActivity(i);
                }
            }
        });

        AnswerC.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(q.getFinalAnswer().equals(AnswerC.getText().toString())){
                    streak++;
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    i.putExtra("name", username);
                    i.putExtra("streak", streak);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    i.putExtra("name", username);
                    startActivity(i);
                }
            }
        });

        AnswerD.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(q.getFinalAnswer().equals(AnswerD.getText().toString())){
                    streak++;
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), GamePage.class);
                    i.putExtra("name", username);
                    i.putExtra("streak", streak);
                    startActivity(i);
                }else{
                    Toast.makeText(GamePage.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    timer.cancel();
                    Intent i = new Intent(getApplicationContext(), Action_page.class);
                    i.putExtra("name", username);
                    startActivity(i);
                }
            }
        });
    }
}
