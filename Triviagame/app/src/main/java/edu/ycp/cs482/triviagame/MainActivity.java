package edu.ycp.cs482.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.GetUser;
import edu.ycp.cs482.controller.LoginUser;

public class MainActivity extends Activity {
    private User user = new User();;
    private String name, password;
    private EditText name1, password1;
    private Button Signup,Signin;
    private Intent i;
    private boolean truth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        name1 = (EditText) findViewById(R.id.txtUser);
        password1 = (EditText) findViewById(R.id.txtPass);
        Signup = (Button) findViewById(R.id.signin);
        Signin = (Button) findViewById(R.id.btnSignUp);

        Signin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                name = name1.getText().toString();
                password = password1.getText().toString();
                LoginUser controller = new LoginUser();
                try{
                    truth = controller.execute(name,password).get();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(truth) {
                    i = new Intent(getApplicationContext(), Action_page.class);
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
                i = new Intent(getApplicationContext(), Sign_up.class);
                startActivity(i);
            }
        });
    }
}
