
package uwm.dca;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by SamGHazaleh on 9/1/16.
 */
public class AddactivitiesFragment  extends Fragment {

    ImageButton dietBtn;
    ImageButton exerciseBtn;
    ImageButton bglBtn;
    ImageButton medicationBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.addactivitesbar_fragment, container, false);

        dietBtn = (ImageButton) view.findViewById(R.id.diet_btn);
        exerciseBtn = (ImageButton) view.findViewById(R.id.exercise_btn);
        bglBtn = (ImageButton) view.findViewById(R.id.bgl_btn);
        medicationBtn = (ImageButton) view.findViewById(R.id.medication_btn);
        return view;
    }



}