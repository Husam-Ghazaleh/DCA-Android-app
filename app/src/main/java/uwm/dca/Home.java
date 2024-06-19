package uwm.dca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {

    Toolbar toolBar;
    TextView textView;
    Context context;
    BottomBarFragment bottomBarFragment;
    DBHelper dbHelper;
    ListView list;
    MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        referenceUIcomponents();
        SessionManager sessionManager = new SessionManager(context);

        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.keyName);
        textView.setText(name + "!");

        dbHelper = new DBHelper(context);
        list = (ListView)findViewById(R.id.lastActivityList);

        listLastActivities();
    }


    private void referenceUIcomponents() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        bottomBarFragment = new BottomBarFragment();
        textView = (TextView) findViewById(R.id.LabelView_UserName);
        context = getApplicationContext();
    }


    private void listLastActivities(){


        ArrayList<ActivityModel> temp = dbHelper.getLastActivites();
        adapter = new MyArrayAdapter(temp,context);

        list.setAdapter(adapter);

    }

    public void goto_AddEvent(View view) {
        Intent AddEvent_Intent = new Intent(this, AddEvent.class);
        startActivity(AddEvent_Intent);
    }
    public void goto_Query(View view) {
        Intent Query_Intent = new Intent(this, Query.class);
        startActivity(Query_Intent);
    }
    public void goto_Suggestion(View view) {
        Intent Suggestion_Intent = new Intent(this, Suggestion.class);
        startActivity(Suggestion_Intent);
    }
    public void goto_Home(View view) {
        Intent Home_Intent = new Intent(this, Home.class);
        startActivity(Home_Intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the home_layout/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
