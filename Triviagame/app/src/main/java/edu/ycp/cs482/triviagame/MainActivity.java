package edu.ycp.cs482.triviagame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDefaultView(){
        setContentView(R.layout.login_page);

        Button Signin = (Button) findViewById(R.id.signin);
        Button Signup = (Button) findViewById(R.id.sign_up);
        Button Settings = (Button) findViewById(R.id.btnSettings);

        Signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // setting a new account to the Database.
               setMainPage();



            }
        });
    }

    public void setMainPage(){
        setContentView(R.layout.activity_main);

        Button back = (Button) findViewById(R.id.button);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                setDefaultView();
            }
        });
    }
}