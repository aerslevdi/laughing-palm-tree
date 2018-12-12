package Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.phimy.controller.ControllerMovieDB;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wpenia on 08/12/18.
 */

public class Preferences {

    private static Preferences instance;

    public static Preferences getInstance(){
        if (instance==null){
            instance= new Preferences();
        }
        return instance;
    }


    public Integer getColumnasPreferences(Context context, String preferenceManager) {
        String key= "pref_pref4";
        SharedPreferences prefs = context.getSharedPreferences(preferenceManager, MODE_PRIVATE);
        int pepe = 0;
        
        String restoredText = prefs.getString("text", null);
        prefs.getInt(key , pepe );


        /*if (restoredText != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            int idName = prefs.getInt("idName", 0); //0 is the default value.
        }*/
        return pepe;
    }


}
