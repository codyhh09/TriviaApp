package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.DeleteUser;

public class Settings extends ActionBarActivity {
    private Button Delete, change;
    private TextView name, pass;
    private Bundle extras;
    private String username;
    private DeleteUser controller;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
        }
        setContentView(R.layout.settings);
        Delete = (Button) findViewById(R.id.deleteBtn);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //controller = new DeleteUser();
                try{
                    //controller.execute(username);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        getSupportActionBar().setIcon(R.drawable.ic_action_help);

        menu.getItem(1).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.addQ:
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
