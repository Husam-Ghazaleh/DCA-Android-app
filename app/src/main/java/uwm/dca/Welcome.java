package uwm.dca;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SamGHazaleh on 12/28/16.
 */

    public class Welcome extends Activity
    {
        Button btnSignIn,btnSignUp;
        TextView ForgotPassword;
        DBHelper dbHelper;
        Context context;
        SessionManager sessionManager;


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.welcome);
            context = getApplicationContext();
            dbHelper=new DBHelper(this);
            sessionManager= new SessionManager(context);

            sessionManager.checkLogin();

            btnSignIn=(Button)findViewById(R.id.Button_SignIN);
            btnSignUp=(Button)findViewById(R.id.Button_SignUP);
            ForgotPassword = (TextView) findViewById(R.id.TextView_ForgotPassword);
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    /// Create Intent for SignUpActivity  abd Start The Activity
                    Intent intentSignUP=new Intent(getApplicationContext(),SignUp.class);
                    startActivity(intentSignUP);
                }
            });

            ForgotPassword.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    /// Create Intent for SignUpActivity  abd Start The Activity
                    Intent intentSignUP=new Intent(getApplicationContext(),ForgotPasswordPage.class);
                    startActivity(intentSignUP);
                }
            });
        }
        // Methos to handleClick Event of Sign In Button
        public void signIn(View V)
        {
            final Dialog dialog = new Dialog(Welcome.this,R.style.AppTheme_PopupOverlay);
            dialog.setContentView(R.layout.login);
            dialog.setTitle("Login");

            // get the Refferences of views
            final EditText editTextUserName=(EditText) dialog.findViewById(R.id.EditText_UserNameToLogin);
            final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.EditText_PasswordToLogin);
            Button btnSignIn=(Button)dialog.findViewById(R.id.Button_SignIn);

            // Set On ClickListener
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   // get The User name and Password
                    String userName=editTextUserName.getText().toString();
                    String password=editTextPassword.getText().toString();
                    // fetch the Password form database for respective user name
                    String storedPassword=dbHelper.getSinlgeEntry(userName);

                    // check if the Stored password matches with  Password entered by user
                    if(password.equals(storedPassword)) {
                        sessionManager.createLoginSession(userName, storedPassword);
                        Toast.makeText(Welcome.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();

                        Intent intentHome=new Intent(getApplicationContext(),Home.class);
                        startActivity(intentHome);
                        //dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(Welcome.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                    }
                }
            });

            dialog.show();
        }

        protected void onStart(){
            super.onStart();
        }
        @Override
        protected void onResume(){
            super.onResume();
        }
        @Override
        protected void onPause(){
            super.onPause();

        }
        @Override
        protected void onStop(){
            super.onStop();
            dbHelper.close();
        }
        @Override
        protected void onRestart(){
            super.onRestart();

        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            dbHelper.close();

        }
    }

