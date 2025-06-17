package com.shera.hisabkitab;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemStorage {
    private static final String PREF_NAME = "HisabPrefs";
    private static final String KEY_ITEMS = "item_list";

    public static void saveItems(Context context, List<Item> items) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(items);

        editor.putString(KEY_ITEMS, json);
        editor.apply();
    }

    public static List<Item> loadItems(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>() {}.getType();

        List<Item> allItems = new ArrayList<>();

        for (String key : prefs.getAll().keySet()) {
            String json = prefs.getString(key, null);
            if (json != null) {
                try {
                    List<Item> items = gson.fromJson(json, type);
                    if (items != null) {
                        allItems.addAll(items);
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // catch and log invalid keys
                }
            }
        }

        return allItems;
    }

}
