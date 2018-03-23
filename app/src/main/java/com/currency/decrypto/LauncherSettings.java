package com.currency.decrypto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by swetha on 2/4/18.
 */

public class LauncherSettings extends AppCompatActivity
{
    SharedPreferences pref = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("com.currency.decrypto",MODE_PRIVATE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (pref.getBoolean("firstrun", true))
        {

            pref.edit().putBoolean("firstrun", false).commit();
            startActivity(new Intent(this , Settings_Screen.class));
            finish();
        }
        else {
            startActivity(new Intent(this , MainActivity.class));
            finish();
        }
    }

}
