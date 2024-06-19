package uwm.dca;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by SamGHazaleh on 11/15/16.
 */
public class  StatsFragment  extends Fragment {

    TextView avgBgl;
    TextView meanBgl;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stats_fragment, container, false);

        avgBgl = (TextView) view.findViewById(R.id.TextView_Avg_Bgl);
        meanBgl = (TextView) view.findViewById(R.id.TextView_Mean_Bgl);

        DBHelper db = new DBHelper(getActivity());
        double avg_bgl =db.avg_bgl();
        avgBgl.setText(Double.toString(avg_bgl));

        return view;
    }



}