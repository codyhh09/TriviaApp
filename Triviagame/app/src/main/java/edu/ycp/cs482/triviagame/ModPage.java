package edu.ycp.cs482.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.controller.GetAllQuestionPending;
import edu.ycp.cs482.controller.GetQuestion;

public class ModPage extends ActionBarActivity {
    private Question question[];
    private Intent i;
    private int id;
    private GetQuestion getquestion = new GetQuestion();
    private GetAllQuestionPending getAllQuestionPending = new GetAllQuestionPending();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                //METHOD THAT EXECUTES LOG-OUT SEQUENCE GOES HERE!
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod_page);

        ListView lview = (ListView) findViewById(R.id.listView);

        //pull the list of user courses from the database
        List<String> questionname = new ArrayList<>();

        try {
            question = getAllQuestionPending.execute().get();

            for(int i = 0; i< question.length; i++){
                questionname.add(question[i].getQuestion());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(MainActivity.this, "User does not have any courses." , Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> la = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, questionname);
        lview.setAdapter(la);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                id = question[arg2].getId();
                i = new Intent(getApplicationContext(), CorrectAQuestion.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
