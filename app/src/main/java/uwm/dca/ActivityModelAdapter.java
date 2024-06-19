package uwm.dca;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
/**
 * Created by SamGHazaleh on 9/10/16.
 */
public class ActivityModelAdapter extends RecyclerView.Adapter<ActivitiesEventHolder>{

     private List<ActivityModel> amList;
     private Context context;
     LayoutInflater inflater;
     ActivityModel model;
     AutoCompleteTextView editDescription;
     EditText editVal;
     EditText editDay;
     EditText editTime;
     SeekBar seekBarBGL;
     DBHelper dbHelper;

    public ActivityModelAdapter (Context context, List<ActivityModel> amList){

        this.context = context;
        this.amList =amList;
        inflater = LayoutInflater.from(context);
        dbHelper = new DBHelper(context);
    }
    @Override
    public ActivitiesEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =null;
        if( viewType == 1) {

            view = inflater.inflate(R.layout.add_diet, parent, false);
        }

        else if( viewType == 2) {

            view = inflater.inflate(R.layout.add_exercise, parent, false);
        }

        else if (viewType ==3) {

            view = inflater.inflate(R.layout.add_medication, parent, false);

        }

        else if (viewType ==4) {

            view = inflater.inflate(R.layout.add_bgl, parent, false);

        }

        return new ActivitiesEventHolder(view,viewType);

    }
    @Override
    public void onBindViewHolder(ActivitiesEventHolder viewHolder, int position) {

        if ( viewHolder.getItemViewType() == 1 || viewHolder.getItemViewType() == 2 || viewHolder.getItemViewType() == 3) {

            editDescription = viewHolder.description;
            editVal= viewHolder.value;
            editDay = viewHolder.varDate;
            editTime= viewHolder.varTime;
            editDescription.setTag(position);
            editVal.setTag(position);
            editDay.setTag(position);
            editTime.setTag(position);
            editDescription.addTextChangedListener(new MyTextWatcher(editDescription, 1, viewHolder.getItemViewType())); // One for Description
            System.out.println("mmmmaram " + viewHolder.getItemViewType() );
            editVal.addTextChangedListener(new MyTextWatcher(editVal, 2));
            editDay.setOnClickListener(new showDayPickerDialog());
            editTime.setOnClickListener(new showTimePickerDialog());


        }// Two for value

        else if (viewHolder.getItemViewType() == 4) {

            int max = 360;
            int min =70;
            int step =10;
            seekBarBGL= viewHolder.sbBgl;
            editDay = viewHolder.varDate;
            editTime = viewHolder.varTime;
            seekBarBGL.setTag(position);
            seekBarBGL.setMax((max-min)/ step);
            editDay.setTag(position);
            editTime.setTag(position);
            seekBarBGL.setOnSeekBarChangeListener(new BglListener(seekBarBGL));
            editDay.setOnClickListener(new showDayPickerDialog());
            editTime.setOnClickListener(new showTimePickerDialog());

        }

    }

    public class MyTextWatcher implements TextWatcher {

        private AutoCompleteTextView editTextDesc;

        private EditText editTextValue;
        int type;
        int activityType;
        SimpleCursorAdapter adapter;

        final int[] to = new int[]{R.id.text5};
//        View view = inflater.inflate(android.R.layout.simple_list_item_1, null, false);
//        TextView  = (TextView) view.findViewById(android.R.id.text1);
//        textView.s
        public MyTextWatcher(EditText editText, int type) {
            this.editTextValue = editText;
            this.type = type;
        }

        public MyTextWatcher(AutoCompleteTextView editText, int type, int activityType) {
            this.editTextDesc = editText;
            this.type = type;
            this.activityType = activityType;
            System.out.println("ooookina " + this.activityType);
            adapter= new SimpleCursorAdapter(getContext().getApplicationContext(),
                    R.layout.my_custom_dropdown , null, from(this.activityType), to, 0);




        }


        //android.R.layout.simple_dropdown_item_1line

        public String[] from(int type) {

            System.out.println("hhhhh" + type);

            if (type == 1)
                return new String[]{dbHelper.DIET_DESC};
            else if (type == 2)
                return new String[]{dbHelper.EXER_DESC};
            else if (this.activityType == 3)
                return new String[]{dbHelper.MED_DESC};

            else return null;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {


            if(this.type == 1) {

                adapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
                    @Override
                    public CharSequence convertToString(Cursor cursor) {
                         int colIndex =0;

                        if (activityType == 1) {
                             colIndex = cursor.getColumnIndexOrThrow(dbHelper.DIET_DESC);
                        }else if (activityType == 2) {
                             colIndex = cursor.getColumnIndexOrThrow(dbHelper.EXER_DESC);
                        } else if (activityType == 3) {
                             colIndex = cursor.getColumnIndexOrThrow(dbHelper.MED_DESC);
                        }

                        return cursor.getString(colIndex);
                    }
                });



                adapter.setFilterQueryProvider(new FilterQueryProvider() {
                    @Override
                    public Cursor runQuery(CharSequence constraints) {

                        String filter = "";
                        if (constraints == null)
                            filter="";
                        else
                            filter=constraints.toString();

                        if (activityType == 1)
                            return  dbHelper.searchKeywordinDiet(filter, dbHelper.DIET_DESC);

                        else if (activityType == 2)
                            return dbHelper.searchKeywordinExercise(filter,dbHelper.EXER_DESC);

                         else if (activityType == 3)
                            return dbHelper.searchKeywordinMedication(filter,dbHelper.MED_DESC);

                        else // should never happen
                            return null;


                    }
                });

                editTextDesc.setAdapter(adapter);
                editTextDesc.setThreshold(1);
                int position = Integer.parseInt(editTextDesc.getTag().toString());
                model = ActivityModel.getList().get(position);
                ActivityModel.setElementList(position, model);
                model.setDescription(s.toString());

            } else if ( this.type ==2) {

                int position = Integer.parseInt(editTextValue.getTag().toString());
                model = ActivityModel.getList().get(position);
                ActivityModel.setElementList(position, model);
                model.setValue(s.toString());
            }
        }

    }
    public class BglListener implements SeekBar.OnSeekBarChangeListener{

          private SeekBar seekbar;
          public BglListener ( SeekBar seekbar){

              this.seekbar = seekbar;
          }


          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

              int position = Integer.parseInt( seekbar.getTag() .toString());
              model =  ActivityModel.getList().get(position);

              int value = 70;
              value = value + (progress * 10);
              String prog_str = Integer.toString(value);
              model.setDescription("bgl");
              model.setValue(prog_str);
              Toast toast = Toast.makeText(context,prog_str , Toast.LENGTH_SHORT);
              toast.show();
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {

          }


      }

//    public static class  DatePickerFragment extends DialogFragment
//            implements DatePickerDialog.OnDateSetListener {
//
//
//        EditText b;
//
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // GET THE CURRENT DATE
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH) +1 ;
//            int day = c.get(Calendar.DAY_OF_MONTH);
//            //b = (EditText) view;
//
////            // CURRENT DATE IS DEFAULT DATE FOR DATEPICKERDIALOG
////            // CREATE AN INSTANCE OF A DATEPICKERDIALOG AND THEN RETURN IT
//            return new DatePickerDialog(getActivity(), R.style.DialogTheme ,this, year, month, day);
//        }
//
////        @Override
////        public void onDateSet(DatePicker view, int year, int month, int day) {
////            // Do something with the date chosen by the user
////            b.setText(day + "-" + (month + 1) + "-" + year);
////        }
//    }

//    public static class TimePickerFragment extends DialogFragment
//            implements TimePickerDialog.OnTimeSetListener {
//
//        int hour = 10;
//        int minute = 0;
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the current time as the default values for the picker
//
//            final Calendar c = Calendar.getInstance();
//            hour = c.get(Calendar.HOUR_OF_DAY);
//            minute = c.get(Calendar.MINUTE);
//
//            // Create a new instance of TimePickerDialog and return it
//            return new TimePickerDialog(getActivity(), R.style.DialogTheme, this, hour, minute,
//                    DateFormat.is24HourFormat(getActivity()));
//        }
//
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//            // do something with the time chosen by the user
//            editTime.setText(hourOfDay + ":" + minute);
//
//        }
//
//    }

    public class showDayPickerDialog implements View.OnClickListener {

        public void onClick(View view) {


            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)  ;
            int day = c.get(Calendar.DAY_OF_MONTH);
            Activity activity = (Activity) context;
            final EditText myEditText = (EditText) view;
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity,R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                            myEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            int position = Integer.parseInt( myEditText.getTag() .toString());
                            model =  ActivityModel.getList().get(position);
                            model.setDate(editDay.getText().toString());

                        }
                    },year, month, day);
                    datePickerDialog.show();
        }
    }
    public class showTimePickerDialog implements View.OnClickListener {

        public void onClick(View view) {


            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            Activity activity = (Activity) context;

            final EditText myEditText = (EditText) view;

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity,R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            myEditText.setText(hourOfDay + ":" + minute);
                            int position = Integer.parseInt( myEditText.getTag() .toString());
                            model =  ActivityModel.getList().get(position);
                            model.setTime(editTime.getText().toString());
                        }
                    }, hour, minute, false);
                    timePickerDialog.show();
                    //Activity activity = (Activity) context;
                    //timeFragment.show(activity.getFragmentManager(),"timePicker");
                    //timeFragment.setView(varTime);
        }
    }
    @Override
    public int getItemViewType(int position) {

        ActivityModel Model = amList.get(position);
        if(Model != null) {
            return Model.type;
        }
        return super.getItemViewType(position);
    }
    @Override
    public int getItemCount() {

        return amList.size();
    }
    public Context getContext(){

        return this.context;
    }

}
