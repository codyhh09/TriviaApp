package edu.ycp.cs482.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.controller.AddQuestion;

public class MakeQuestion extends ActionBarActivity{
    private String username;
    private String answer = null;
    private Bundle extras;
    private boolean lose = false;
    private int choose = 0;
    private Intent i;
    private EditText question, answerA, answerB, answerC, answerD;
    private String qe, a1, a2, a3, a4;
    private Button submit;
    private Question q = new Question();
    private AddQuestion addquestion = new AddQuestion();
    private RadioGroup rg;

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
                i.putExtra("lose", lose);
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
            username = extras.getString("name");
            lose = extras.getBoolean("lose");
        } else {
            username = (String) savedInstanceState.getSerializable("name");
            lose = (Boolean) savedInstanceState.getSerializable("lose");
        }

        setContentView(R.layout.add_question);
        question = (EditText) findViewById(R.id.txtQuestions);
        answerA = (EditText) findViewById(R.id.txtAnsA);
        answerB = (EditText) findViewById(R.id.txtAnsB);
        answerC = (EditText) findViewById(R.id.txtAnsC);
        answerD = (EditText) findViewById(R.id.txtAnsD);
        submit = (Button) findViewById(R.id.btngood);
        rg = (RadioGroup) findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button was clicked
                switch(checkedId) {
                    case R.id.radAnsA:
                        choose = 1;
                        break;
                    case R.id.radAnsB:
                        choose = 2;
                        break;
                    case R.id.radAnsC:
                        choose = 3;
                        break;
                    case R.id.radAnsD:
                        choose = 4;
                        break;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            qe = question.getText().toString();
            a1 = answerA.getText().toString();
            a2 = answerB.getText().toString();
            a3 = answerC.getText().toString();
            a4 = answerD.getText().toString();
            if (qe != "" && a1 != "" && a2 != "" && a3 != "" && a4 != "" && choose != 0){
                try {
                    switch(choose){
                        case 1:
                            answer =  answerA.getText().toString();
                            break;
                        case 2:
                            answer = answerB.getText().toString();
                            break;
                        case 3:
                            answer = answerC.getText().toString();
                            break;
                        case 4:
                            answer = answerD.getText().toString();
                            break;
                    }
                    addquestion.execute(question.getText().toString(), answerA.getText().toString(), answerB.getText().toString(), answerC.getText().toString(), answerD.getText().toString(), answer, username);
                    i = new Intent(getApplicationContext(), CorrectAQuestion.class);
                    i.putExtra("name", username);
                    i.putExtra("lose", lose);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(MakeQuestion.this, "Fill in all of the entries!!", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
