package edu.ycp.cs482.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.MenuInflater;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;

import edu.ycp.cs482.controller.AddQuestion;

public class ShopPage extends ActionBarActivity {
    private Button sc;
    private Bundle extras;
    private int streak;
    private Intent i;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
        }

        setContentView(R.layout.shop_page);
        sc = (Button) findViewById(R.id.btnSC);

        sc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: gain continues
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        getSupportActionBar().setIcon(R.drawable.ic_action_help);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.settings:
                i = new Intent(getApplicationContext(), Settings.class);
                i.putExtra("name", username);
                startActivity(i);
                return true;
            case R.id.addQ:
                i = new Intent(getApplicationContext(), MakeQuestion.class);
                i.putExtra("name", username);
                startActivity(i);
                return true;
            case R.id.main:
                i = new Intent(getApplicationContext(), MenuPage.class);
                i.putExtra("name", username);
                startActivity(i);
                return true;
            case R.id.log_out:
                //METHOD THAT EXECUTES LOG-OUT SEQUENCE GOES HERE!
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
