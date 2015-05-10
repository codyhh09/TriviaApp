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
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import edu.ycp.cs482.Model.User;
import edu.ycp.cs482.controller.ChangeCoins;
import edu.ycp.cs482.controller.GetUser;
import edu.ycp.cs482.controller.UpdateRetry;

public class ShopPage extends ActionBarActivity {
    private Button sc;
    private Bundle extras;
    private Intent i;
    private String username;
    private User user = new User();
    private boolean lose;
    private int update, coin;
    private GetUser getUser = new GetUser();
    private UpdateRetry updateRetry = new UpdateRetry();
    private ChangeCoins changeCoins = new ChangeCoins();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            username= extras.getString("name");
            lose = extras.getBoolean("lose");
        } else {
            username= (String) savedInstanceState.getSerializable("name");
            lose = (boolean) savedInstanceState.getSerializable("lose");
        }

        setContentView(R.layout.shop_page);
        sc = (Button) findViewById(R.id.btnSC);

        sc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            try {
                user = getUser.execute(username).get();
                if(user.getCoins()>0) {
                    update = user.getRetry() + 1;
                    coin = user.getCoins() - 1;
                    changeCoins.execute(username, Integer.toString(coin));
                    updateRetry.execute(username, Integer.toString(update));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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
                i.putExtra("lose", lose);
                startActivity(i);
                return true;
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
                ShopPage.this.finish();
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        i = new Intent(getApplicationContext(), MenuPage.class);
        i.putExtra("name", username);
        i.putExtra("lose", lose);
        startActivity(i);
    }
}
