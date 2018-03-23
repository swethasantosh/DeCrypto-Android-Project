package com.currency.decrypto;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.currency.decrypto.Models.Currency;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Santosh on 2/12/18.
 */

public class Sharedpreferences
{
    public static final String PREFS_NAME = "COIN_APP";
    public static final String FAVORITES = "COIN_Favorite";

    public Sharedpreferences() {
        super();
    }


    public void saveFavorites(Context context, List<Currency> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Currency coin) {
        List<Currency> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Currency>();
        favorites.add(coin);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Currency coin) {
        ArrayList<Currency> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(coin);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Currency> getFavorites(Context context) {
        SharedPreferences settings;
        List<Currency> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        Toast.makeText(context,"sharedpref",Toast.LENGTH_LONG).show();

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Currency[] favoriteItems = gson.fromJson(jsonFavorites,Currency[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Currency>(favorites);
        } else
            return null;

        return (ArrayList<Currency>) favorites;
    }

}
