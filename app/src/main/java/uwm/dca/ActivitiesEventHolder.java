package uwm.dca;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SeekBar;

/**
 * Created by SamGHazaleh on 9/15/16.
 */
public class ActivitiesEventHolder extends RecyclerView.ViewHolder {


        public AutoCompleteTextView description;
        public EditText value;
        public EditText varDate;
        public EditText varTime;
        public SeekBar sbBgl;

        ActivityModel model;


        public ActivitiesEventHolder(View itemView, int type) {

            super(itemView);

            if( type == 1) {

                description = (AutoCompleteTextView) itemView.findViewById(R.id.EditText_DietDescription);
                value = (EditText) itemView.findViewById(R.id.EditText_DietAmount);
                varDate= (EditText) itemView.findViewById(R.id.EditText_DietDayTime);
                varTime = (EditText) itemView.findViewById(R.id.EditText_DietTime);
            }

            else if (type ==2) {

                description = (AutoCompleteTextView) itemView.findViewById(R.id.EditText_ExerciseDescription);
                value = (EditText) itemView.findViewById(R.id.EditText_ExerciseDuration);
                varDate = (EditText) itemView.findViewById(R.id.EditText_ExerciseDayTime);
                varTime=(EditText) itemView.findViewById(R.id.EditText_ExerciseTime);
            }

            else if (type ==3) {

                description =(AutoCompleteTextView) itemView.findViewById(R.id.EditText_MedicationDescription);
                value = (EditText) itemView.findViewById(R.id.EditText_MedicationAmount);
                varDate = (EditText) itemView.findViewById(R.id.EditText_MedicationDayTime);
                varTime=(EditText) itemView.findViewById(R.id.EditText_MedicationTime);
            }

            else if ( type ==4)
            {
                sbBgl = (SeekBar) itemView.findViewById(R.id.SeekBar_Bgl);
                varDate = (EditText) itemView.findViewById(R.id.EditText_BglDayTime);
                varTime=(EditText) itemView.findViewById(R.id.EditText_BglTime);
            }
        }

    }

