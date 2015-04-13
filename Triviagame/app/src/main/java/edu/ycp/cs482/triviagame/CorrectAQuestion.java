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
import android.widget.TextView;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.controller.DeleteQuestion;
import edu.ycp.cs482.controller.GetQuestion;

public class CorrectAQuestion extends ActionBarActivity{
    private Question q = new Question();
    private Intent i;
    private Bundle extras;
    private int id;
    private EditText question, answer1, answer2, answer3, answer4;
    private Button Good, Bad;
    private GetQuestion getquestion = new GetQuestion();
    private DeleteQuestion deleteQuestion = new DeleteQuestion();

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

        question = (EditText) findViewById(R.id.txtQuestion);
        answer1 = (EditText) findViewById(R.id.txtAnsA);
        answer2 = (EditText) findViewById(R.id.txtAnsB);
        answer3 = (EditText) findViewById(R.id.txtAnsC);
        answer4 = (EditText) findViewById(R.id.txtAnsD);
        Good = (Button) findViewById(R.id.btngood);
        Bad = (Button) findViewById(R.id.btnbad);

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

        Good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
