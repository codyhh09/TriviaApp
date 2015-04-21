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
import edu.ycp.cs482.controller.ChangeAnswer1;
import edu.ycp.cs482.controller.ChangeAnswer2;
import edu.ycp.cs482.controller.ChangeAnswer3;
import edu.ycp.cs482.controller.ChangeAnswer4;
import edu.ycp.cs482.controller.ChangeFinalAnswer;
import edu.ycp.cs482.controller.ChangeQuestion;
import edu.ycp.cs482.controller.ChangeStatus;
import edu.ycp.cs482.controller.DeleteQuestion;
import edu.ycp.cs482.controller.GetQuestion;

public class CorrectAQuestion extends ActionBarActivity{
    private Question q = new Question();
    private Intent i;
    private Bundle extras;
    private int id, choose=1;
    private EditText question, answer1, answer2, answer3, answer4;
    private Button Good, Bad;
    private String answer;
    private RadioGroup rg;
    private GetQuestion getquestion = new GetQuestion();
    private DeleteQuestion deleteQuestion = new DeleteQuestion();
    private ChangeQuestion changeQuestion = new ChangeQuestion();
    private ChangeAnswer1 changeAnswer1 = new ChangeAnswer1();
    private ChangeAnswer2 changeAnswer2 = new ChangeAnswer2();
    private ChangeAnswer3 changeAnswer3 = new ChangeAnswer3();
    private ChangeAnswer4 changeAnswer4 = new ChangeAnswer4();
    private ChangeFinalAnswer changeFinalAnswer = new ChangeFinalAnswer();
    private ChangeStatus changeStatus = new ChangeStatus();

    // make the spinner

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
        setContentView(R.layout.verifingpage);

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            id = extras.getInt("id");
        } else {
            id = (Integer) savedInstanceState.getSerializable("id");
        }

        question = (EditText) findViewById(R.id.txtQuestions);
        answer1 = (EditText) findViewById(R.id.txtAnsAs);
        answer2 = (EditText) findViewById(R.id.txtAnsBs);
        answer3 = (EditText) findViewById(R.id.txtAnsCs);
        answer4 = (EditText) findViewById(R.id.txtAnsDs);
        Good = (Button) findViewById(R.id.btngood);
        Bad = (Button) findViewById(R.id.btnbad);
        rg = (RadioGroup) findViewById(R.id.radioGroup1);

        try{
            q = getquestion.execute(id).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        question.setText(q.getQuestion());
        answer1.setText(q.getAnswer1());
        answer2.setText(q.getAnswer2());
        answer3.setText(q.getAnswer3());
        answer4.setText(q.getAnswer4());

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radAnsA1:
                        choose = 1;
                        break;
                    case R.id.radAnsB1:
                        choose = 2;
                        break;
                    case R.id.radAnsC1:
                        choose = 3;
                        break;
                    case R.id.radAnsD1:
                        choose = 4;
                        break;
                }
            }
        });

        Good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1.getText().toString() != "" && answer2.getText().toString() != "" && answer3.getText().toString() != "" && answer4.getText().toString() != "" && question.getText().toString() != "" && choose != 0){
                    switch (choose) {
                        case 1:
                            answer = answer1.getText().toString();
                            break;
                        case 2:
                            answer = answer2.getText().toString();
                            break;
                        case 3:
                            answer = answer3.getText().toString();
                            break;
                        case 4:
                            answer = answer4.getText().toString();
                            break;
                    }
                    try {
                        if(changeQuestion.execute(Integer.toString(q.getId()), question.getText().toString()).get() &&
                                changeAnswer1.execute(Integer.toString(q.getId()), answer1.getText().toString()).get()&&
                                changeAnswer2.execute(Integer.toString(q.getId()), answer2.getText().toString()).get()&&
                                changeAnswer3.execute(Integer.toString(q.getId()), answer3.getText().toString()).get()&&
                                changeAnswer4.execute(Integer.toString(q.getId()), answer4.getText().toString()).get()&&
                                changeFinalAnswer.execute(Integer.toString(q.getId()), answer).get()&&
                                changeStatus.execute(Integer.toString(q.getId())).get()){
                            i = new Intent(getApplicationContext(), ModPage.class);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(CorrectAQuestion.this, "Fill in all of the entries!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    deleteQuestion.execute(q.getId());
                    i = new Intent(getApplicationContext(), ModPage.class);
                    startActivity(i);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
