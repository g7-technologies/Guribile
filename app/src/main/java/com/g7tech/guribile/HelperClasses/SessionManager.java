package com.g7tech.guribile.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Session";

    private static final String Id = "id";
    private static final String Email="email";
    private static final String Name="name";
    private static final String Phone = "phone";
    private static final String Service = "service";
    private static final String Worker = "worker";
    private static final String Work = "work";
    private static final String Lat = "lat";
    private static final String Language = "language";
    private static final String Longt = "longt";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setId(String id) {

        editor.putString(Id, id);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setPhone(String phone) {

        editor.putString(Phone, phone);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setService(String service) {

        editor.putString(Service, service);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setWorker(String worker) {

        editor.putString(Worker, worker);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setWork(String work) {

        editor.putString(Work, work);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setLat(String lat) {

        editor.putString(Lat, lat);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    public void setLongt(String longt) {

        editor.putString(Longt, longt);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setEmail(String email) {

        editor.putString(Email, email);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public void setName(String name) {

        editor.putString(Name, name);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setLanguage(String language) {

        editor.putString(Language, language);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public String getId() {
        return pref.getString(Id, "");    }


    public String getEmail() {
        return pref.getString(Email, "");    }

    public String getName() {
        return pref.getString(Name, "");    }

    public String getWork() {
        return pref.getString(Work, "");    }

    public String getPhone() {
        return pref.getString(Phone, "");    }

    public String getService() {
        return pref.getString(Service, "");    }

    public String getWorker() {
        return pref.getString(Worker, "");    }

    public String getLat(){
        return pref.getString(Lat,"");  }

    public String getLongt(){
        return pref.getString(Longt,"");  }

    public String getLanguage() {
        return pref.getString(Language, "");    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
