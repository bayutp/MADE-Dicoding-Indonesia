package com.bayupamuji.catalogmovie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SharePreferencesCatalogMovie {
    private final SharedPreferences preferences;

    public SharePreferencesCatalogMovie(@NonNull Context context){
        String SHARE_PREF_NAME = "SHARE_PREFERENCES_CATALOG_MOVIE";
        preferences = context.getSharedPreferences(SHARE_PREF_NAME,Context.MODE_PRIVATE);
    }

    public boolean getStatus(String key){
        return preferences.getBoolean(key,false);
    }

    public void setStatus(String key){
        preferences.edit().putBoolean(key, true).apply();
    }

    public void remove(String key){
        preferences.edit().remove(key).apply();
    }
}
