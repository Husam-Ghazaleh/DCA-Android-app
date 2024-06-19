package uwm.dca;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
    EditText editTextUserName, editTextPassword, editTextConfirmPassword,editTextEmail ,editTextHint;
    Button btnCreateAccount;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        dbHelper = new DBHelper(this);

        //Get Refferences of Views
        editTextUserName = (EditText) findViewById(R.id.EditText_UserName);
        editTextPassword = (EditText) findViewById(R.id.EditText_Password);
        editTextConfirmPassword = (EditText) findViewById(R.id.EditText_ConfirmPassword);
        editTextEmail =(EditText)findViewById(R.id.EditText_Email);
        editTextHint = (EditText) findViewById(R.id.EditText_Hint);
        btnCreateAccount = (Button) findViewById(R.id.Button_CreateAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                String email =editTextEmail.getText().toString();
                String Hint = editTextHint.getText().toString();

                // check if any of the fields are vaccant
                if (userName.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    // Save the Data in Database
                    dbHelper.insertAccount(userName, password, Hint,email);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Intent intentSignUP=new Intent(getApplicationContext(),Welcome.class);
                    startActivity(intentSignUP);
                    System.out.println("Hello");
                }
            }
        });
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