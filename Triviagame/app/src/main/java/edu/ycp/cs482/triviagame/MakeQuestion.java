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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.controller.AddQuestion;

public class MakeQuestion extends ActionBarActivity{
    private String username;
    private String answer;
    private Bundle extras;
    private Intent i;
    private EditText question, answerA, answerB, answerC, answerD;
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
        } else {
            username = (String) savedInstanceState.getSerializable("name");
        }

        setContentView(R.layout.add_question);
        question = (EditText) findViewById(R.id.txtQuestion);
        answerA = (EditText) findViewById(R.id.txtAnsA);
        answerB = (EditText) findViewById(R.id.txtAnsB);
        answerC = (EditText) findViewById(R.id.txtAnsC);
        answerD = (EditText) findViewById(R.id.txtAnsD);
        submit = (Button) findViewById(R.id.btnSubmit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                   // answer();
                    addquestion.execute(question.getText().toString(), answerA.getText().toString(),answerB.getText().toString(),
                            answerC.getText().toString(),answerD.getText().toString(), "word");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public void answer(){
        rg = (RadioGroup) findViewById(R.id.radioGroup);

        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                // Check which radio button was clicked
                switch(v.getId()) {
                    case R.id.radAnsA:
                        if (checked)
                            answer = answerA.getText().toString();
                            break;
                    case R.id.radAnsB:
                        if (checked)
                            answer = answerB.getText().toString();
                            break;
                    case R.id.radAnsC:
                        if (checked)
                            answer = answerC.getText().toString();
                            break;
                    case R.id.radAnsD:
                        if (checked)
                            answer = answerD.getText().toString();
                            break;
                }
            }
        });
    }
}
