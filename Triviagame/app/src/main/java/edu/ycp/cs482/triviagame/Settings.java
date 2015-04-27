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

import edu.ycp.cs482.controller.AddQuestion;
import edu.ycp.cs482.controller.ChangePassword;
import edu.ycp.cs482.controller.ChangeUser;
import edu.ycp.cs482.controller.DeleteUser;

public class Settings extends ActionBarActivity {
    private Button delete, change, chgPss, back;
    private TextView name, pass;
    private Bundle extras;
    private String username;
    private boolean lose;
    private DeleteUser dltuser = new DeleteUser();
    private ChangeUser changeuser = new ChangeUser();
    private ChangePassword changePassword = new ChangePassword();
    private Intent i;


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
                i = new Intent(getApplicationContext(), MakeQuestion.class);
                i.putExtra("name", username);
                i.putExtra("lose", lose);
                startActivity(i);
                return true;
            case R.id.main:
                i = new Intent(getApplicationContext(), MenuPage.class);
                i.putExtra("name", username);
                i.putExtra("lose", lose);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
            lose = extras.getBoolean("lose");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
            lose = (boolean) savedInstanceState.getSerializable("lose");
        }
        setContentView(R.layout.settings);
        name = (TextView) findViewById(R.id.chgUser);
        pass = (TextView) findViewById(R.id.chgPass);
        change = (Button) findViewById(R.id.changeBtn);
        delete = (Button) findViewById(R.id.deleteBtn);
        chgPss = (Button) findViewById(R.id.chgpassBtn);
        back = (Button) findViewById(R.id.menubtn);

        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    changeuser.execute(username, name.getText().toString());
                    username = name.getText().toString();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        chgPss.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    changePassword.execute(username, pass.getText().toString());
                }catch(Exception e){
                    e.printStackTrace();
                }
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dltuser = new DeleteUser();
                try{
                    dltuser.execute(username);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), MenuPage.class);
                i.putExtra("name", username);
                i.putExtra("lose", lose);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        i = new Intent(getApplicationContext(), MenuPage.class);
        i.putExtra("name", username);
        i.putExtra("lose", lose);
        startActivity(i);
    }
}


