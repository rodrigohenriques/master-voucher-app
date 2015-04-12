package br.com.mastervoucher.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Isabel Porto on 12/04/2015.
 */
public class EventDAO {

    SharedPreferences sharedpreferences;

    private static final String EVENT_PREF = "EVENT_PREF";
    private static final String EVENT_ID = "EVENT_ID";

    public EventDAO(Context context) {
        this.sharedpreferences = context.getSharedPreferences(EVENT_PREF, Context.MODE_PRIVATE);
    }

    public void saveEventId(String eventId){
        Editor editor = sharedpreferences.edit();
        editor.putString(EVENT_ID, eventId);
        editor.commit();
   }

    public String getEventId(){
        return this.sharedpreferences.getString(EVENT_ID, null);
    }
}
