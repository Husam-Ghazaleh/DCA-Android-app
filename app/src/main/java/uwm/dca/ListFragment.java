package uwm.dca;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by SamGHazaleh on 11/15/16.
 */
public class  ListFragment  extends Fragment {

    CheckBox diet_Checkbox , exercise_Checkbox, bgl_Checkbox, medication_Checkbox;
    EditText from_Edittxt, to_Edittxt, keyword_Edittxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        diet_Checkbox = (CheckBox)  view.findViewById(R.id.CheckBox_Diet);
        exercise_Checkbox = (CheckBox)  view.findViewById(R.id.CheckBox_Exercise);
        bgl_Checkbox = (CheckBox)  view.findViewById(R.id.CheckBox_BGL);
        medication_Checkbox = (CheckBox)  view.findViewById(R.id.CheckBox_Medication);
        from_Edittxt = (EditText) view.findViewById(R.id.EditText_From) ;
        to_Edittxt = (EditText) view.findViewById(R.id.EditText_To);
        keyword_Edittxt = (EditText) view.findViewById(R.id.EditText_Keyword);
        Query query = new Query();
        Query.MyTextWatcher mytextwatcher = query.new MyTextWatcher(keyword_Edittxt);
        keyword_Edittxt.addTextChangedListener(mytextwatcher);

        return view;
    }

}