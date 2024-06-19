package uwm.dca;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;

/**
 * Created by SamGHazaleh on 2/13/17.
 */
public class ForgotPasswordPage extends Activity{

    Fragment fragment;
    DBHelper dbHelper;
    static String body ="", email="";
    CheckBox sendHintCheckBox , forgotPasswordCheckBox;
    EditText emailEdittext;
    static int flag =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgotpasswordpage);
        dbHelper = new DBHelper(this);
        fragment = new EnterEmailFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container2, fragment);
        transaction.commit();


    }

    public class MyClickListener implements View.OnClickListener {
        Activity activity;

        public MyClickListener(Activity actvitiy ){
            this.activity = actvitiy;
        }

        Mail mailsender  = mailsender= new Mail("diabetescareuwm@gmail.com", "Uwm2016Mi");
        public void onClick(View view) {

            mailsender.setFrom("diabetescareuwm@gmail.com");
            mailsender.setSubject("Test Email from DiabetesCare Center-UWM");
            mailsender.setBody(body);
            String to_[]={email};
            mailsender.setTo(to_);

            new AsyncTask<Void, Void, Void>() {



                @Override
                protected void onPreExecute() {
                    // Log.d(TAG, "onPreExecute()");
                }
                @Override
                protected Void doInBackground(Void... params) {

                    try {

                        if(mailsender.send()) {
                            flag =1;
                            System.out.println(" Email was sent successfuly ");
                        } else {
                             flag =2;
                             System.out.println(" Email was'nt sent successfuly");
                        }

                    } catch(Exception e) {
                          Log.e("MailApp", "Could not send email", e);
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Void res) {
                    if (flag == 1)
                        Toast.makeText(activity, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                    else if( flag == 2)
                        Toast.makeText(activity, "Email was not sent successfully.", Toast.LENGTH_LONG).show();
                    else if (flag ==3)
                        Toast.makeText(activity," Your password has been updated",Toast.LENGTH_SHORT).show();
                    else if ( flag ==4)
                        Toast.makeText(activity," Password Can't be updated",Toast.LENGTH_SHORT).show();
                }
            }.execute();


        }
    }

    public void checkBoxListener (View view) {

        switch (view.getId()) {
            case R.id.CheckBox_SendHint:
                sendHintCheckBox = (CheckBox) view.findViewById(R.id.CheckBox_SendHint);
                if (sendHintCheckBox.isChecked())
                    sendHint();
                break;
            case R.id.CheckBox_SendTempPassword:
                forgotPasswordCheckBox = (CheckBox)view.findViewById(R.id.CheckBox_SendTempPassword);
                if (forgotPasswordCheckBox.isChecked())
                    sendPassword();
                break;


        }

    }

    public void sendEmailTo(View view){
        emailEdittext = (EditText) findViewById(R.id.EditText_toSendEmail);
        email = emailEdittext.getText().toString();
        System.out.println("March + " + email);
        fragment = new ForgotPasswordFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container2, fragment);
        transaction.commit();

    }

    public void sendHint(){

     String name = dbHelper.retiveUserName(email);
     String hint = dbHelper.retiveUserHint(name);
     body = hint;

    }

    public void sendPassword(){

        String generatedPassword = generateRandomPassword();
        String name = dbHelper.retiveUserName(email);
        if(dbHelper.updatePassword(name,  generatedPassword) ==1) {
            body = generatedPassword;
            flag =3;
        }
        else
            flag=4;

    }

    private String generateRandomPassword(){
         final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
         SecureRandom rnd = new SecureRandom();

            StringBuilder sb = new StringBuilder( 8 );
            for( int i = 0; i < 8; i++ )
                sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();

    }

}


