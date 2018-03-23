package com.currency.decrypto;

import android.app.Application;
import android.content.Context;

/**
 * Created by swetha on 12/17/17.
 */


//Custom Application class
public class MyApplication  extends Application
{
    //hard code api key
    public static final String API_KEY_CRYPTO_CURRENCY = "https://api.cryptonator.com/api/full/btc-usd";



    private static MyApplication sInstance;

    //called before anything runs in the app

    @Override
    public void onCreate()
    {
        sInstance = this;
        super.onCreate();
    }
    //return instance
    public static  MyApplication getsInstance()
    {

        return sInstance;
    }

    //return appcontext

    public static Context getAppContext()
    {
        return sInstance.getApplicationContext();
    }
}
