package uwm.dca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by SamGHazaleh on 9/1/16.
 */
public class Suggestion extends Activity {


  DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_layout);
        dbHelper =new DBHelper(this);
        System.out.println("Hello....");
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
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();

    }
    @Override
    protected void onStop(){
        super.onStop();
        //dbHelper.close();
    }
    @Override
    protected void onRestart(){
        super.onRestart();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dbHelper.close();

    }

}
