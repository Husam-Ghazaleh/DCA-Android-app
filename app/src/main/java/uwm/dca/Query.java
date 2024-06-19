package uwm.dca;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SamGHazaleh on 9/1/16.
 */
public class Query extends Activity {

    private Context context;
    Fragment fragment = null;
    DBHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    CheckBox diet_Checkbox , exercise_Checkbox, bgl_Checkbox, medication_Checkbox;
    String from_str, to_str;
    Boolean search_diet =false,
            search_exercize =false,
            search_bgl =false,
            search_medication =false,
            only_from =false,
            _to =false;
         static Boolean withkeyword = false;
         static String keyword;

    boolean has_initialized = false;
    ListView listView;

    @Override
    protected void onCreate (Bundle SavedInstantState) {
        super.onCreate(SavedInstantState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.query_layout);
        withkeyword = false;
    }
    public void checkBoxListener (View view) {


            switch (view.getId()) {
                case R.id.CheckBox_Diet:
                    diet_Checkbox = (CheckBox)findViewById(R.id.CheckBox_Diet);
                    if (diet_Checkbox.isChecked())
                        search_diet = true;
                    break;
                case R.id.CheckBox_Exercise:
                    exercise_Checkbox = (CheckBox)findViewById(R.id.CheckBox_Exercise);
                    if (exercise_Checkbox.isChecked())
                        search_exercize = true;
                    break;
                case R.id.CheckBox_BGL:
                    bgl_Checkbox = (CheckBox)findViewById(R.id.CheckBox_BGL);
                    if (bgl_Checkbox.isChecked())
                        search_bgl = true;
                    break;
                case R.id.CheckBox_Medication:
                    medication_Checkbox = (CheckBox)findViewById(R.id.CheckBox_Medication);
                    if (medication_Checkbox.isChecked())
                        search_medication = true;
                    break;

            }
    }
    public void show_List(View view){

        fragment = new ListFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
    public void show_Graph(View view){


    }
    public void show_Stats(View view){

        fragment = new StatsFragment();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
        if(has_initialized == true) {
            listView.setVisibility(View.INVISIBLE);
        }

    }
    public void showDay(View view){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) ;
        int day = c.get(Calendar.DAY_OF_MONTH);
        final EditText myEditText = (EditText) view;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String strDate;

                            myEditText.setText(year + "-" + (monthOfYear +1 ) + "-" + dayOfMonth);
                            strDate = myEditText.getText().toString();
                            String strTime ="00:00";
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date convertedDate = new Date();

                             try {
                                 convertedDate = dateFormat.parse(strDate + " " + strTime);
                             } catch(ParseException pe){
                                 System.out.println("dateFormat exception - Query class");
                          }

                             from_str = dateFormat.format(convertedDate);
                             only_from = true;

                    }
                },year, month, day);
        datePickerDialog.show();
    }
    public void showDay_to(View view){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) ;
        int day = c.get(Calendar.DAY_OF_MONTH);
        final EditText myEditText = (EditText) view;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String strDate;
                        myEditText.setText(year + "-" + (monthOfYear +1 ) + "-" + dayOfMonth);
                        strDate= myEditText.getText().toString();
                        String strTime ="23:59";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date convertedDate = new Date();


                        try {
                            convertedDate = dateFormat.parse(strDate + " " + strTime);
                        } catch(ParseException pe){
                            System.out.println("dateFormat exception - Query class");
                        }
                         to_str = dateFormat.format(convertedDate);
                        _to=true;
                    }
                },year, month, day);
        datePickerDialog.show();
    }
    public class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            keyword = editText.getText().toString();
            withkeyword = true;
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    public void search (View view){

        Cursor cursor = null;
        String[] columns_Diet = new String[]{
                dbHelper.DIET_DESC,
                dbHelper.DIET_DATETIME,
                dbHelper.DIET_AMOUNT,
        };
        String[] columns_Excercise = new String[]{
                dbHelper.EXER_DESC,
                dbHelper.EXER_DATETIME,
                dbHelper.EXER_DURATION,
        };
        String[] columns_Bgl = new String[]{
                dbHelper.BGL_DESC,
                dbHelper.BGL_DATETIME,
                dbHelper.BGL_VALUE,
        };
        String[] columns_Med = new String[]{
                dbHelper.MED_DESC,
                dbHelper.MED_DATETIME,
                dbHelper.MED_AMOUNT,
        };

        int[] to = new int[]{
                R.id.description,
                R.id.datetime,
                R.id.value,
        };


        if(search_diet) {

            if(withkeyword) {

                //cursor= dbHelper.searchKeywordinDiet(keyword);

                if (!(cursor = dbHelper.searchKeywordinDiet(keyword,dbHelper.DIET_DATETIME)).moveToFirst()) {
                    Toast toast = Toast.makeText(this, keyword + " Not Found!", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
             else {
                    if (only_from && _to)

                        cursor = dbHelper.fetchAllDiet(from_str, to_str);
                    else if (!_to && !only_from)
                        cursor = dbHelper.fetchAllDiet(from_str, to_str);
                    else {
                        Toast toast = Toast.makeText(this, "Please Enter \"From date\" and \"To date\"Or Leave Them Blank", Toast.LENGTH_LONG);
                        toast.show();
                    }
            }

            dataAdapter = new SimpleCursorAdapter(this, R.layout.row_activity, cursor, columns_Diet, to, 0);
            listView = (ListView) findViewById(R.id.activity_list);
            has_initialized =true;
            listView.setAdapter(dataAdapter);
            listView.setVisibility(View.VISIBLE);
        }

        if(search_exercize) {

            if(withkeyword) {

                if (!(cursor = dbHelper.searchKeywordinExercise(keyword,dbHelper.EXER_DATETIME)).moveToFirst()) {
                    Toast toast = Toast.makeText(this, keyword + " Not Found!", Toast.LENGTH_LONG);
                    toast.show();

                }

            }else {
                if (only_from && _to)

                    cursor = dbHelper.fetchAllExercise(from_str, to_str);

                else if (!_to && !only_from)  // covers without from or with

                    cursor = dbHelper.fetchAllExercise(from_str, to_str);
                else {

                    Toast toast = Toast.makeText(this, "Please Enter \"From date\" and \"To date\"Or Leave Them Blank", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            dataAdapter = new SimpleCursorAdapter(this, R.layout.row_activity, cursor, columns_Excercise, to, 0);
            listView = (ListView) findViewById(R.id.activity_list);
            has_initialized =true;
            listView.setAdapter(dataAdapter);
            listView.setVisibility(View.VISIBLE);
        }

        if(search_bgl) {

            if(withkeyword) {

                Toast toast = Toast.makeText(this, "BGL Measurment Comes with No Description!", Toast.LENGTH_LONG);
                toast.show();

            }else {
                    if (only_from && _to)

                        cursor = dbHelper.fetchAllBgl(from_str,to_str);

                    else if(!_to && !only_from)  // covers without from or with

                         cursor = dbHelper.fetchAllBgl(from_str,to_str);
                     else {

                        Toast toast = Toast.makeText(this, "Please Enter \"From date\" and \"To date\"Or Leave Them Blank", Toast.LENGTH_LONG);
                        toast.show();
                    }
            }

            dataAdapter = new SimpleCursorAdapter(this, R.layout.row_activity, cursor, columns_Bgl, to, 0);
            listView = (ListView) findViewById(R.id.activity_list);
            has_initialized =true;
            listView.setAdapter(dataAdapter);
            listView.setVisibility(View.VISIBLE);
        }

        if(search_medication) {

            if(withkeyword) {

                if (!(cursor = dbHelper.searchKeywordinMedication(keyword,dbHelper.MED_DATETIME)).moveToFirst()) {
                    Toast toast = Toast.makeText(this, keyword + " Not Found!", Toast.LENGTH_LONG);
                    toast.show();

                }

            }else {
                if (only_from && _to)

                    cursor = dbHelper.fetchAllMedication(from_str, to_str);

                else if (!_to && !only_from)  // covers without from or with

                    cursor = dbHelper.fetchAllMedication(from_str, to_str);
                else {

                    Toast toast = Toast.makeText(this, "Please Enter \"From date\" and \"To date\"Or Leave Them Blank", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            dataAdapter = new SimpleCursorAdapter(this, R.layout.row_activity, cursor, columns_Med, to, 0);
            listView = (ListView) findViewById(R.id.activity_list);
            has_initialized =true;
            listView.setAdapter(dataAdapter);
            listView.setVisibility(View.VISIBLE);
        }


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

}
