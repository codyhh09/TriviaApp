package edu.ycp.cs482.triviagame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.ChangeUser;
import edu.ycp.cs482.controller.DeleteUser;
import edu.ycp.cs482.controller.GetUser;

public class Settings extends Activity {
    private Button delete, change;
    private TextView name, pass;
    private Bundle extras;
    private String username;
    private GetUser getuser = new GetUser();
    private DeleteUser dltuser = new DeleteUser();
    private ChangeUser changeuser = new ChangeUser();
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
        name = (TextView) findViewById(R.id.chgUser);
        pass = (TextView) findViewById(R.id.chgPass);
        change = (Button) findViewById(R.id.changeBtn);
        delete = (Button) findViewById(R.id.deleteBtn);

        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    changeuser.execute(name.getText().toString(), username);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    dltuser.execute(username);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
