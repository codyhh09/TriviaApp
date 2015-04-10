package edu.ycp.cs482.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.LoginUser;

public class MainActivity extends ActionBarActivity {
    private User user;
    private String name, password;
    private EditText name1, password1;
    private Button Signup,Signin;
    private Intent i;
    private Bundle b;
    private boolean truth;
    public final static String User_Key = "edu.ycp.cs482.Model.User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        name1 = (EditText) findViewById(R.id.txtUser);
        password1 = (EditText) findViewById(R.id.txtPass);
        Signup = (Button) findViewById(R.id.btnSignUp);
        Signin = (Button) findViewById(R.id.signin);
        user = new User();

        Signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //name = name1.getText().toString();
                //password = password1.getText().toString();
                /*LoginUser controller = new LoginUser();
                try{
                    truth = true;//= controller.execute(name,password).get();
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                if(true/*truth*/) {
                    i = new Intent(getApplicationContext(), MenuPage.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this,"Wrong Username and/or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), SignUp.class);
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
}