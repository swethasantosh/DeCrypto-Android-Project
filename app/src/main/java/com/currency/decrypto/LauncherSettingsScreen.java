package com.currency.decrypto;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by swetha on 3/23/18.
 */

public class LauncherSettingsScreen extends AppCompatActivity
{
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstancestate)
    {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.launcher_settings);


       /* Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_include);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");*/


        Transition transition = TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(transition);

        Fragment fragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragmentlaout,fragment,"settings_fragment");
        fragmentTransaction.commit();

        Button later_btn,ok_btn;
        later_btn = (Button)findViewById(R.id.laterbtn);
        ok_btn=(Button)findViewById(R.id.okbutton);
        later_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));


            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));


            }
        });

    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {


        @Override
        public void onCreate(Bundle savedInstancestate) {
            super.onCreate(savedInstancestate);

            addPreferencesFromResource(R.xml.settings_screen);

            SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
            EditTextPreference editTextPreference = (EditTextPreference)findPreference("value_tobe_displayed");
            editTextPreference.setSummary(sp.getString("value_tobe_displayed","100"));

        }
        public void onResume()
        {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }
        public void onPause()
        {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key)
        {
            Preference pref = findPreference(key);
            if(pref instanceof  EditTextPreference)
            {
                EditTextPreference etp = (EditTextPreference) pref;
                pref.setSummary(etp.getText());
            }
        }
    }

   /* @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }*/
}

