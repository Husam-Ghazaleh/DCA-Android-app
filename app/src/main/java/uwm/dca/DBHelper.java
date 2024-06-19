package uwm.dca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SamGHazaleh on 9/5/16.
 */
 class DBHelper extends SQLiteOpenHelper {

    Context context;
    DBHelper dbHelper;

    private static final int Database_Version = 1;
    private static final String Database_Name = "DCA_Database";
    private static final String Password = "1234";
    public static final String USR_ID_foreign = "usr_id_foreign";

    //BGL Variables
    public static final String TABLE_BGL = "bgl_value";
    public static final String BGL_ID = "bgl_id";
    public static final String BGL_DESC = "bgl_desc";
    public static final String BGL_DATETIME = "bgl_datetime";
    public static final String BGL_VALUE = "bgl_value";


    //Diet Variables
    public static final String TABLE_DIET = "diets";
    public static final String DIET_ID = "diet_id";
    public static final String DIET_DESC = "diet_desc";
    public static final String DIET_AMOUNT = "diet_amount";
    public static final String DIET_DATETIME = "diet_datetime";

    //Exercise
    public static final String TABLE_EXERCISE = "exercise";
    public static final String EXER_ID = "exer_id";
    public static final String EXER_DESC = "exer_desc";
    public static final String EXER_DATETIME = "exer_datetime";
    public static final String EXER_DURATION = "exer_duration";

    //Medication Variables
    public static final String TABLE_MED = "medications";
    public static final String MED_ID = "med_id";
    public static final String MED_DESC = "med_desc";
    public static final String MED_AMOUNT = "med_amount";
    public static final String MED_DATETIME = "med_datetime";

    //Prescription Variables
    public static final String TABLE_PRESCRIPTION = "prescriptions";
    public static final String PRE_ID = "pre_id";
    public static final String PRE_DESC = "pre_desc";
    public static final String PRE_REPEAT = "pre_repeat";

    //Users Variables
    public static final String TABLE_USERS = "users";
    public static final String USR_ID = "usr_id";
    public static final String USR_NAME = "usr_name";
    public static final String USR_Password = "usr_password";
    public static final String USR_Hint = "usr_hint"   ;
    public static final String USR_Email = "usr_email"   ;

    public DBHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String query;
        dbHelper = new DBHelper(context);
        if (database == null || !database.isOpen()) {
            SQLiteDatabase.loadLibs(context);
            database = dbHelper.getWritableDatabase(Password);

        }
        //Users
        query = "CREATE TABLE " + TABLE_USERS + " (" +
                USR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USR_NAME + " TEXT," +
                USR_Password + " TEXT," +
                USR_Hint + " TEXT," +
                USR_Email + ")";
        database.execSQL(query);


        //Medication
        query = "CREATE TABLE " + TABLE_MED + " (" +
                MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MED_DESC + " TEXT," +
                MED_DATETIME + " TEXT," +
                MED_AMOUNT + " INTEGER," +
                USR_ID_foreign + " TEXT," +
                "FOREIGN KEY(" + USR_ID_foreign +") REFERENCES "+TABLE_USERS+ "("+ USR_ID +"));";
        database.execSQL(query);

        System.out.println("test_1");

        //Diet
        query = "CREATE TABLE " + TABLE_DIET + " (" +
                DIET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DIET_DESC + " TEXT," +
                DIET_DATETIME + " TEXT," +
                DIET_AMOUNT + " INTEGER," +
                USR_ID_foreign + " TEXT," +
                "FOREIGN KEY(" + USR_ID_foreign +") REFERENCES "+TABLE_USERS+ "("+ USR_ID +"));";

        database.execSQL(query);
        System.out.println("test_2");

        //Exercise
        query = "CREATE TABLE " + TABLE_EXERCISE + " (" +
                EXER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EXER_DESC + " TEXT," +
                EXER_DATETIME + " TEXT," +
                EXER_DURATION + " INTEGER," +
                USR_ID_foreign + " TEXT," +
                "FOREIGN KEY(" + USR_ID_foreign +") REFERENCES "+TABLE_USERS+ "("+ USR_ID +"));";
        database.execSQL(query);
        System.out.println("test_3");


        //Table BGL
        query = "CREATE TABLE " + TABLE_BGL + " (" +
                BGL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BGL_DESC + " TEXT," +
                BGL_DATETIME + " TEXT," +
                BGL_VALUE + " INTEGER," +
                USR_ID_foreign + " TEXT," +
                "FOREIGN KEY(" + USR_ID_foreign +") REFERENCES "+TABLE_USERS+ "("+ USR_ID +"));";
        database.execSQL(query);

    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.delete(Database_Name,null,null);
        dbHelper.onCreate(database);

    }

    public void insertBGL(String desc, String value, String date) {

        String id = retrieveUserID(getLoggedInUser());
        if(id.isEmpty())
            throw new RuntimeException("No user Found");

        ContentValues values = new ContentValues();
        values.put(BGL_DESC,desc);
        values.put(BGL_VALUE, value);
        values.put(BGL_DATETIME, date);
        values.put(USR_ID_foreign, id);
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        mdb.insert(TABLE_BGL, null, values);
    }

    public void insertDiet(String desc, String amount, String date) {

        String id = retrieveUserID(getLoggedInUser());
        if(id.isEmpty())
            throw new RuntimeException("No user Found");
        ContentValues values = new ContentValues();
        values.put(DIET_DESC, desc);
        values.put(DIET_AMOUNT, amount);
        values.put(DIET_DATETIME, date);
        values.put(USR_ID_foreign, id);
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        mdb.insert(TABLE_DIET, null, values);


    }

    public void insertExercise(String desc, String duration, String date) {

        String id = retrieveUserID(getLoggedInUser());
        if(id.isEmpty())
            throw new RuntimeException("No user Found");

        ContentValues values = new ContentValues();
        values.put(EXER_DESC, desc);
        values.put(EXER_DATETIME, date);
        values.put(EXER_DURATION, duration);
        values.put(USR_ID_foreign, id);
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        mdb.insert(TABLE_EXERCISE, null, values);
    }

    public void insertMedication(String desc, String amount, String date) {


        String id = retrieveUserID(getLoggedInUser());
        if(id.isEmpty())
            throw new RuntimeException("No user Found");
        ContentValues values = new ContentValues();
        values.put(MED_DESC, desc);
        values.put(MED_DATETIME, date);
        values.put(MED_AMOUNT, amount);
        values.put(USR_ID_foreign, id);
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        mdb.insert(TABLE_MED, null, values);
    }

    public void insertAccount(String userName,String password,String hint, String email)
    {

        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        ContentValues values = new ContentValues();
        values.put(USR_NAME, userName);
        values.put(USR_Password,password);
        values.put(USR_Hint,hint);
        values.put(USR_Email,email);
        mdb.insert(TABLE_USERS, null, values);
        Toast.makeText(context, "Account Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public List<Double> GetAllBGL() {

        List<Double> bgls_values = new ArrayList<Double>();
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;
        double bgl =0;
        String id = retrieveUserID(getLoggedInUser());
        mCursor = mdb.query(TABLE_BGL, new String[]{
                "rowid _id", BGL_VALUE},"usr_id_foreign=?", new String[]{id}, null, null, null);

        if (mCursor.getCount() >=1) {

            while (mCursor.moveToNext()) {
                    bgl = Double.valueOf(mCursor.getString(mCursor.getColumnIndex(BGL_VALUE)));
                    bgls_values.add(bgl);
            }
        }
        mCursor.close();
        return bgls_values;
    }

    public double avg_bgl() {

        List<Double> copy_bgl_values = new ArrayList<Double>(GetAllBGL());
        int size = copy_bgl_values.size();
        double sum_sofar = 0;
        for (int i = 0; i < size; i++){
            sum_sofar = sum_sofar + copy_bgl_values.get(i);
        }
        return sum_sofar/(size * 1.0) ;
    }

    public Cursor fetchAllDiet(String from, String to) {

        String id = retrieveUserID(getLoggedInUser());
        System.out.println("diet " + id);
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;

        if (from == null || from.length() == 0) {

            mCursor = mdb.query(TABLE_DIET, new String[]{
                    "rowid _id", DIET_DESC, DIET_DATETIME, DIET_AMOUNT},"usr_id_foreign=?", new String[]{id}, null, null, DIET_DATETIME  + " DESC");
        } else {

            mCursor=mdb.query(TABLE_DIET, new String[]{
                            "rowid _id", DIET_DESC, DIET_DATETIME, DIET_AMOUNT}, "usr_id_foreign=?" +" AND " +DIET_DATETIME + " BETWEEN ? AND ? ", new String[]{id,from,to}, null, null, null);
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllExercise(String from, String to) {

        String id = retrieveUserID(getLoggedInUser());
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;
        if (from == null || from.length() == 0) {

            mCursor = mdb.query(TABLE_EXERCISE, new String[]{
                    "rowid _id", EXER_DESC, EXER_DATETIME, EXER_DURATION}, "usr_id_foreign=?", new String[]{id}, null, null, EXER_DATETIME+ " DESC");
        } else {


            mCursor=mdb.query(TABLE_EXERCISE, new String[]{
                    "rowid _id", EXER_DESC, EXER_DATETIME, EXER_DURATION}, "usr_id_foreign=?" +" AND " + EXER_DATETIME + " BETWEEN ? AND ?", new String[]{id,from,to}, null, null, null);
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public Cursor fetchAllBgl(String from, String to) {

        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;
        String id = retrieveUserID(getLoggedInUser());
        System.out.println(" id in bgl" + id);
        if (from == null || from.length() == 0) {

            mCursor = mdb.query(TABLE_BGL, new String[]{
                    "rowid _id",BGL_DESC ,BGL_DATETIME, BGL_VALUE}, "usr_id_foreign=?", new String[]{id}, null, null, BGL_DATETIME+ " DESC");
        } else {


            mCursor=mdb.query(TABLE_BGL, new String[]{
                    "rowid _id", BGL_DESC,BGL_DATETIME, BGL_VALUE}, "usr_id_foreign=?" +" AND " + BGL_DATETIME + " BETWEEN ? AND ?", new String[]{id,from,to}, null, null, null);
        }

        if (mCursor != null) {
            System.out.println(" id in bgl not empty" );
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllMedication(String from, String to) {

        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;
        String id = retrieveUserID(getLoggedInUser());
        if (from == null || from.length() == 0) {

            mCursor = mdb.query(TABLE_MED, new String[]{
                    "rowid _id", MED_DESC, MED_DATETIME, MED_AMOUNT}, "usr_id_foreign=?", new String[]{id}, null, null, MED_DATETIME + " DESC");
        } else {


            mCursor=mdb.query(TABLE_MED, new String[]{
                    "rowid _id", MED_DESC, MED_DATETIME, MED_AMOUNT}, "usr_id_foreign=?" +" AND " + MED_DATETIME + " BETWEEN ? AND ?", new String[]{id,from,to}, null, null, null );
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public Cursor searchKeywordinDiet(String keyword, String columnName){

        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;
        String id = retrieveUserID(getLoggedInUser());
        mCursor= mdb.query(TABLE_DIET, new String[] {  "rowid _id", DIET_DESC, DIET_DATETIME, DIET_AMOUNT }, "usr_id_foreign=?" +" AND " + DIET_DESC + " LIKE  ?",
                new String[] {id, "%"+ keyword+ "%" }, null, null, columnName + " DESC");

        return mCursor;
    }

    public Cursor searchKeywordinExercise(String keyword,String columnName){

        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;
        String id = retrieveUserID(getLoggedInUser());
        mCursor = mdb.query( TABLE_EXERCISE, new String[] {  "rowid _id", EXER_DESC, EXER_DATETIME, EXER_DURATION }, "usr_id_foreign=?" +" AND " + EXER_DESC + " LIKE ?",
                new String[] {id, "%"+ keyword+ "%" }, null, null, columnName + " DESC");

        return mCursor;
    }

    public Cursor searchKeywordinMedication(String keyword, String columnName){

        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        Cursor mCursor = null;
        String id = retrieveUserID(getLoggedInUser());
        mCursor= mdb.query( TABLE_MED, new String[] {  "rowid _id", MED_DESC, MED_DATETIME, MED_AMOUNT },"usr_id_foreign=?" +" AND " + MED_DESC + " LIKE ?",
                new String[] {id,"%"+ keyword+ "%" }, null, null, columnName + " DESC");

        return mCursor;
    }

    public String getSinlgeEntry(String userName) {

        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getReadableDatabase(Password);
        Cursor cursor=mdb.query(TABLE_USERS, null, " usr_name=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex(USR_Password));
        cursor.close();
        return password;
    }

    public String getLoggedInUser() {
        SessionManager sessionManager = new SessionManager(context);
        HashMap<String, String> user = sessionManager.getUserDetails();

        // name
        String name = user.get(SessionManager.keyName);

        return name;
    }

    public String retrieveUserID(String userName){

        String id ="-1";
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getReadableDatabase(Password);
        if (mdb == null)
            System.out.println("ggg");
        Cursor cursor=mdb.query(TABLE_USERS, null, " usr_name=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return id;
        }
        cursor.moveToFirst();
        id= cursor.getString(cursor.getColumnIndex(USR_ID));
        cursor.close();
        return id;

    }

    public String retiveUserHint(String userName){

        String hint ="-1";
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getReadableDatabase(Password);
        if (mdb == null)
            System.out.println("ggg");
        Cursor cursor=mdb.query(TABLE_USERS, null, " usr_name=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return hint;
        }
        cursor.moveToFirst();
        hint= cursor.getString(cursor.getColumnIndex(USR_Hint));
        cursor.close();
        return hint;

    }

    public String retiveUserName(String email){

        String username ="-1";
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getReadableDatabase(Password);
        if (mdb == null)
            System.out.println("ggg");
          Cursor cursor=mdb.query(TABLE_USERS, null, " usr_email=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return username;
        }
        cursor.moveToFirst();
        username= cursor.getString(cursor.getColumnIndex(USR_NAME));
        cursor.close();
        return username;

    }

    public  ArrayList<ActivityModel> getLastActivites(){

        ArrayList<ActivityModel> lastActivites = new ArrayList<>();
        String nullstr =null;
        Cursor DietCursor = fetchAllDiet(nullstr,nullstr);
        Cursor ExeCursor = fetchAllExercise(nullstr,nullstr);
        Cursor BglDiet = fetchAllBgl(nullstr,nullstr);
        Cursor MedicationDiet = fetchAllMedication(nullstr,nullstr);


        ActivityModel tempActivityDiet = new ActivityModel();
        ActivityModel tempActivityExer = new ActivityModel();
        ActivityModel tempActivityBgl = new ActivityModel();
        ActivityModel tempActivityMed = new ActivityModel();

        if (DietCursor.getCount() >=1) {

            if (DietCursor.moveToFirst()) {


                tempActivityDiet.setDescription(DietCursor.getString(DietCursor.getColumnIndex(DIET_DESC)));
                tempActivityDiet.setDate(DietCursor.getString(DietCursor.getColumnIndex(DIET_DATETIME)));
                tempActivityDiet.setValue(DietCursor.getString(DietCursor.getColumnIndex(DIET_AMOUNT)));
                lastActivites.add(tempActivityDiet);
                System.out.println("we are here in first floor");

            }
        }
        else{

            tempActivityDiet.setDescription("Nothing");
            tempActivityDiet.setDate("Nothing");
            tempActivityDiet.setValue("Nothing" );
            lastActivites.add(tempActivityDiet);
            System.out.println("we are here in second floor");

        }
        DietCursor.close();



        if (ExeCursor.getCount() >=1) {

            if (ExeCursor.moveToFirst()) {

                tempActivityExer.setDescription(ExeCursor.getString(ExeCursor.getColumnIndex(EXER_DESC)));
                tempActivityExer.setDate(ExeCursor.getString(ExeCursor.getColumnIndex(EXER_DATETIME)));
                tempActivityExer.setValue(ExeCursor.getString(ExeCursor.getColumnIndex(EXER_DURATION)));
                lastActivites.add(tempActivityExer);
            }
        }
        else{
            tempActivityExer.setDescription("Nothing");
            tempActivityExer.setDate("Nothing");
            tempActivityExer.setValue("Nothing" );
            lastActivites.add(tempActivityExer);

        }
        ExeCursor.close();




        if (BglDiet.getCount() >=1) {

            if (DietCursor.moveToFirst()) {

                tempActivityBgl.setDescription(BglDiet.getString(BglDiet.getColumnIndex(BGL_DESC)));
                tempActivityBgl.setDate(BglDiet.getString(BglDiet.getColumnIndex(BGL_DATETIME)));
                tempActivityBgl.setValue(BglDiet.getString(BglDiet.getColumnIndex(BGL_VALUE)));
                lastActivites.add(tempActivityBgl);

            }
        }
        else{
            tempActivityBgl.setDescription("Nothing");
            tempActivityBgl.setDate("Nothing");
            tempActivityBgl.setValue("Nothing" );
            lastActivites.add(tempActivityBgl);

        }
        BglDiet.close();




        if (MedicationDiet.getCount() >= 1) {

            if (MedicationDiet.moveToFirst()) {

                tempActivityMed.setDescription(MedicationDiet.getString(MedicationDiet.getColumnIndex(MED_DESC)));
                tempActivityMed.setDate(MedicationDiet.getString(MedicationDiet.getColumnIndex(MED_DATETIME)));
                tempActivityMed.setValue(MedicationDiet.getString(MedicationDiet.getColumnIndex(MED_AMOUNT)));
                lastActivites.add(tempActivityMed);

            }
        }
        else{
            tempActivityMed.setDescription("Nothing");
            tempActivityMed.setDate("Nothing");
            tempActivityMed.setValue("Nothing" );
            lastActivites.add(tempActivityMed);

        }
        MedicationDiet.close();


        return lastActivites;


    }

    public int updatePassword(String name, String password){

        int update =0;
        SQLiteDatabase.loadLibs(context);
        SQLiteDatabase mdb = getWritableDatabase(Password);
        ContentValues newValues = new ContentValues();
        newValues.put(USR_Password, password);
        String[] args = new String[]{name};
        if ( mdb.update(TABLE_USERS, newValues, USR_NAME+"=?", args) ==1) {
            System.out.println("update password successfully");
            update =1;
        }else {
            System.out.println("password hasn't updated");

        }

       return update;
    }
}