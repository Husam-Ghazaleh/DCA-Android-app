package uwm.dca;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by SamGHazaleh on 3/2/17.
 */
public class EnterEmailFragment extends Fragment {


    EditText EditTextToSendEmail;
    Button BtnEnterEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.enteremail_fragment, container, false);


        EditTextToSendEmail = (EditText) view.findViewById(R.id.EditText_toSendEmail);
        BtnEnterEmail = (Button) view.findViewById(R.id.Btn_SendEmailToReset);
        return view;
    }

}