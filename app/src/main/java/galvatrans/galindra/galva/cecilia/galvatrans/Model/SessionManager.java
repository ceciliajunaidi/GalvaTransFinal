package galvatrans.galindra.galva.cecilia.galvatrans.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "session";
    private static final String IS_LOGIN = "isLoggedIn";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_DB_MASTER = "dbMaster";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createDbMaster(String dbMaster){
        editor.putString(KEY_DB_MASTER, dbMaster);
        editor.commit();
    }

    public void createLoginSession(String username){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        return user;
    }

    public HashMap<String, String> getDbMaster(){
        HashMap<String, String> db = new HashMap<>();
        db.put(KEY_DB_MASTER, pref.getString(KEY_DB_MASTER, null));

        return db;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setIsLogin() {

    }
}
