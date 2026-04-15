package com.yeasinrabbee.zafariqbal.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SaveData {

    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    public SaveData(Context context) {

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        edit = preferences.edit();

    }

    public void  save(String key,String data){

        edit.putString(key, data);
        edit.commit();//storing
    }
    public void  save(String key,boolean boo){

        edit.putBoolean(key, boo);
        edit.commit();//storing
    }

    public void  save(String key,int boo){

        edit.putInt(key, boo);
        edit.commit();//storing
    }

    public String  getData(String key){

        String data = preferences.getString(key,"");
        return data;

    }
    public boolean  getBoolean(String key){

        boolean data = preferences.getBoolean(key,false);
        return data;

    }

    public int  getInt(String key){

        int data = preferences.getInt(key,0);
        return data;

    }

}
