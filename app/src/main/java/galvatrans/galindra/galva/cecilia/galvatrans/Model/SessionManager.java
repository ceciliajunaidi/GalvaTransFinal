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
    public static final String KEY_NAME = "name";
    public static final String KEY_AREA = "area";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String username, String name, String area){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_AREA, area);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_AREA, pref.getString(KEY_AREA, null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
