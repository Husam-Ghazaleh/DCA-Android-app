package uwm.dca;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by SamGHazaleh on 3/2/17.
 */
public class ForgotPasswordFragment extends Fragment {

    CheckBox sendHintCheckBox , forgotPasswordCheckBox;
    Button SendEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

     View view = inflater.inflate(R.layout.forgotpassword_fragment,container,false);
        sendHintCheckBox = (CheckBox) view.findViewById(R.id.CheckBox_SendHint);
        forgotPasswordCheckBox = (CheckBox)view.findViewById(R.id.CheckBox_SendTempPassword);
        SendEmail= (Button) view.findViewById(R.id.reset_btn);

        ForgotPasswordPage forgotpassword = new ForgotPasswordPage();
        ForgotPasswordPage.MyClickListener myclicklistner = forgotpassword.new MyClickListener(getActivity());
        SendEmail.setOnClickListener(myclicklistner);




        return view;
    }
}
