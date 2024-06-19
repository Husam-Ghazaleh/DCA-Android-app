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
public class BottomBarFragment extends Fragment {

    ImageButton homeBtn;
    ImageButton activitiesBtn;
    ImageButton queryBtn;
    ImageButton suggestionBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottombar_fragment, container, false);

        homeBtn = (ImageButton) view.findViewById(R.id.Home_Btn);
        activitiesBtn = (ImageButton) view.findViewById(R.id.Activities_Btn);
        queryBtn = (ImageButton) view.findViewById(R.id.Query_btn);
        suggestionBtn = (ImageButton) view.findViewById(R.id.Suggestion_btn);


        return view;
    }


}