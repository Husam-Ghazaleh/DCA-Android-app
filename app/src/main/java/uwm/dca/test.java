package uwm.dca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by HusamGhazaleh on 8/6/17.
 */

public class test extends AppCompatActivity {


    @Override
     protected void onCreate (Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.test);

    }


    public void GooglesignIn(View v){

        Intent intent = new Intent(getApplicationContext(),GoogleSignInActivity.class);
        startActivity(intent);
    }

    public void EmailSignIn (View v){

        Intent intent = new Intent( getApplicationContext(),EmailPasswordActivity.class);
        startActivity(intent);

    }

}
