package edu.ycp.cs482.triviagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ShopPage extends ActionBarActivity{
    private Intent i;
    private String username;
    private boolean lose;
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
}
