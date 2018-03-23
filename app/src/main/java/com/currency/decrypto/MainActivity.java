package com.currency.decrypto;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.currency.decrypto.Fragments.News_fragment;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity
{
    Context context;

    private static final String TAG = "Mainactivity";
    String networkinfo;

    public  boolean haveAnInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(1);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(0);
        if (wifi.isAvailable() && wifi.isConnected()) {
            return true;
        }
        if (mobile.isAvailable() && mobile.isConnected()) {
            return true;
        }
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_include);
        setSupportActionBar(toolbar);


        if(haveAnInternetConnection(this))
        {
            Crypto_Fragment crypto_fragment = new Crypto_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout,crypto_fragment,"Fragmentname");
            fragmentTransaction.replace(R.id.main_layout,crypto_fragment,"Fragmentname");

            fragmentTransaction.commit();

        }
        else
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();


        /*ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = connectivityManager.getActiveNetworkInfo();
        if(activenetwork!= null)
        {
            if(activenetwork.getType() == ConnectivityManager.TYPE_WIFI)
                networkinfo = "you are connected to wifi";
            if(activenetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                networkinfo = "you are connected to mobile data";
        }
        else
            networkinfo = "Check your internet connection and then try";
        Toast.makeText(getApplicationContext(), networkinfo, Toast.LENGTH_LONG).show();*/








        //bottom naviagtion view
        final BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    //case R.id.action_add:
                    //from menu-bottomnavigationmain mxl
                    case R.id.action_coins:

                        Crypto_Fragment crypto_fragment = new Crypto_Fragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        //fragmentTransaction.replace(R.id.framelayout,crypto_fragment,"Fragmentname");
                        fragmentTransaction.replace(R.id.main_layout,crypto_fragment,"Fragmentname");

                        fragmentTransaction.commit();

                        break;
                    //case R.id.action_alert:
                    case R.id.action_news:

                        News_fragment news_fragment = new News_fragment();
                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        //fragmentTransaction2.replace(R.id.framelayout,news_fragment,"Fragmentname");
                        fragmentTransaction2.replace(R.id.main_layout,news_fragment,"Fragmentname");

                        fragmentTransaction2.commit();
                        break;
                   // case R.id.action_notification:
                    case R.id.action_settings:

                        startActivity(new Intent(MainActivity.this,Settings_Screen.class));
                        SharedPreferences  preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String setting = preferences.getString("value_tobe_displayed","10");

                       // Toast.makeText(getApplicationContext(),setting,Toast.LENGTH_LONG).show();
                        break;

                  /*  case R.id.action_favorites:

                        FavoriteListFragment favorites_fragment = new FavoriteListFragment();
                        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        //fragmentTransaction.replace(R.id.framelayout,crypto_fragment,"Fragmentname");
                        fragmentTransaction3.replace(R.id.main_layout,favorites_fragment,"Fragmentname");

                        fragmentTransaction3.commit();

                        break;*/
                }
                return true;
            }
        });

    }

}
