package com.currency.decrypto;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;

/**
 * Created by swetha on 12/26/17.
 */

public class Settings_Screen extends AppCompatActivity
{
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstancestate)
    {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_settings);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");


        Transition transition = TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(transition);

        Fragment fragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.settingsactivity,fragment,"settings_fragment");
            fragmentTransaction.commit();


    }

    public static class SettingsFragment extends PreferenceFragment
    {


        @Override
        public void onCreate(Bundle savedInstancestate) {
            super.onCreate(savedInstancestate);

            addPreferencesFromResource(R.xml.settings_screen);

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
