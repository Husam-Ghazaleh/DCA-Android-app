package uwm.dca;

/**
 * Created by SamGHazaleh on 1/2/17.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    Editor editor;
    Context context;

    private static final String prefName = "DCA_Preferences";
    private static final String isLogin = "IsLoggedIn";
    public static final String keyName = "name";
    public static final String keyPassword = "password";

    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(prefName, 0);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String password){

        editor.putBoolean(isLogin, true);
        editor.putString(keyName, name);
        editor.putString(keyPassword, password);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent go = new Intent(context, Welcome.class);
//            // Closing all the Activities
//            go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            context.startActivity(go);
            Intent intentHome=new Intent(context,Home.class);
            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            context.startActivity(intentHome);

        }

        else{
                // nothing
        }

    }

    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();
        user.put(keyName, pref.getString(keyName, null));
        user.put(keyPassword, pref.getString(keyPassword, null));
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent out = new Intent(context, Welcome.class);
        // Closing all the Activities
        out.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        out.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(out);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(isLogin, false);
    }
}