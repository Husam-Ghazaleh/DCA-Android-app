package uwm.dca;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SamGHazaleh on 9/1/16.
 */
public class AddEvent extends Activity {

    ArrayList<ActivityModel> amList;
    RecyclerView rvAddEvent;
    ActivityModelAdapter adapter;
    DBHelper dbHelper = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent_layout);
        rvAddEvent = (RecyclerView) findViewById(R.id.rv_addactivities);
        amList = new ArrayList<>();
        adapter = new ActivityModelAdapter(this, amList);
        rvAddEvent.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        rvAddEvent.setLayoutManager(layoutManager);
        rvAddEvent.setAdapter(adapter);

        rvAddEvent.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int totalWidth = parent.getWidth();
                int layoutWidth = getResources().getDimensionPixelOffset(R.dimen.logevent_width);
                int layoutVerticalPad = getResources().getDimensionPixelOffset(R.dimen.logevent_padding);

                int sidePadding = (totalWidth - layoutWidth) / 2;
                int abovePadding = layoutVerticalPad / 2;

                sidePadding = Math.max(0, sidePadding);
                abovePadding = Math.max(0, abovePadding);

                outRect.set(sidePadding, abovePadding, sidePadding, abovePadding);
            }
        });

    }
    public void addDiet (View view){
        int type = 1;
        //rvAddEvent.scrollToPosition(adapter.getItemCount()-1);

        amList.add(new ActivityModel(type));
        ActivityModel.addModeltoList(type);
        adapter.notifyDataSetChanged();
        rvAddEvent.scrollToPosition(adapter.getItemCount() -1);

    }
    public void addExercise (View view){
        int type = 2;
        amList.add(new ActivityModel(type));
        ActivityModel.addModeltoList(type);
        adapter.notifyDataSetChanged();
        rvAddEvent.scrollToPosition(adapter.getItemCount() -1);
    }
    public void addMedication (View view) {
        int type = 3;
        amList.add(new ActivityModel(type));
        ActivityModel.addModeltoList(type);
        adapter.notifyDataSetChanged();
        rvAddEvent.scrollToPosition(adapter.getItemCount() -1);
    }
    public void addBgl(View view){

        int type = 4;
        amList.add(new ActivityModel(type));
        ActivityModel.addModeltoList(type);
        adapter.notifyDataSetChanged();
        rvAddEvent.scrollToPosition(adapter.getItemCount() -1);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int count = adapter.getItemCount();
        int counter =0;
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.Save_item:
                System.out.println("hi");
                while (counter < count){
                    //parentView = rvAddEvent.getLayoutManager().findViewByPosition(counter);
                    //int type = adapter.getItemViewType(counter);
                    if(amList.get(counter).getType() == 1) {
                        String strDescription = ActivityModel.getList().get(counter).getDescription();
                        String strValue = ActivityModel.getList().get(counter).getValue();
                        String  strDate  =  ActivityModel.getList().get(counter).getDate();
                        String  strTime  =  ActivityModel.getList().get(counter).getTime();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date convertedDate = new Date();

                        try {
                            convertedDate = dateFormat.parse(strDate + " " + strTime);

                        } catch(ParseException pe){
                            System.out.println("dateFormat exception - AddEvent class");
                        }

                          String dateTime = dateFormat.format(convertedDate);
                          dbHelper.insertDiet(strDescription,strValue,dateTime);
                          Toast toast = Toast.makeText(this, "added", Toast.LENGTH_LONG);
                           toast.show();

                    }
                    else if(amList.get(counter).getType() == 2) {
                        String strDescription = ActivityModel.getList().get(counter).getDescription();
                        String strValue = ActivityModel.getList().get(counter).getValue();
                        String strDate = ActivityModel.getList().get(counter).getDate();
                        String strTime = ActivityModel.getList().get(counter).getTime();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date convertedDate = new Date();

                        try {
                            convertedDate = dateFormat.parse(strDate + " " + strTime);
                        } catch(ParseException pe){
                            System.out.println("dateFormat exception - AddEvent class");
                        }

                        String dateTime = dateFormat.format(convertedDate);
                        dbHelper.insertExercise (strDescription, strValue,dateTime);
                        Toast toast = Toast.makeText(this, "added", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    else if(amList.get(counter).getType() == 3) {
                        String strDescription = ActivityModel.getList().get(counter).getDescription();
                        String strValue = ActivityModel.getList().get(counter).getValue();
                        String strDate = ActivityModel.getList().get(counter).getDate();
                        String strTime = ActivityModel.getList().get(counter).getTime();


                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date convertedDate = new Date();
                        try {
                            convertedDate = dateFormat.parse(strDate + " " + strTime);
                        } catch(ParseException pe){
                            System.out.println("dateFormat exception - AddEvent class");
                        }

                        String dateTime = dateFormat.format(convertedDate);

                        dbHelper.insertMedication(strDescription, strValue, dateTime);
                        Toast toast = Toast.makeText (this, "added", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    else if (amList.get(counter).getType() == 4) {

                        String strValue = ActivityModel.getList().get(counter).getValue();
                        String strDate = ActivityModel.getList().get(counter).getDate();
                        String strTime = ActivityModel.getList().get(counter).getTime();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date convertedDate = new Date();


                        try {
                            convertedDate = dateFormat.parse(strDate + " " + strTime);
                        } catch(ParseException pe){
                            System.out.println("dateFormat exception - AddEvent class");
                        }

                        String dateTime = dateFormat.format(convertedDate);
                        if (strValue == null)
                             strValue ="70";

                        dbHelper.insertBGL("BGL",strValue, dateTime);
                        Toast toast = Toast.makeText(this, "added", Toast.LENGTH_LONG);
                        toast.show();

                    }

                    counter++;
                }
                amList.clear();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.SignOut:

               SessionManager sessionManager = new SessionManager(getApplicationContext());
               sessionManager.logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
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
        dbHelper.close();
    }
    @Override
    protected void onRestart(){
        super.onRestart();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();

    }
}