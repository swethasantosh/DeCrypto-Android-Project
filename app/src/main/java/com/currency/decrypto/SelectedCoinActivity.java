package com.currency.decrypto;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectedCoinActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    String selectedcoin ;
   // private Mypager_adapter madapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_coin);



       // madapter = new Mypager_adapter(getSupportFragmentManager());

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        Intent i = getIntent();
        selectedcoin = i.getStringExtra( "name");
        getSupportActionBar().setTitle(selectedcoin);


        //viewPager.setAdapter(madapter);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Selectedcoin_details_fragment(), "Details");
        adapter.addFragment(new SelectedcoinChart(), "Chart");
        //adapter.addFragment(new Selectedcoin_Alert_fragment(), "Alert");
        viewPager.setAdapter(adapter);
    }


  /*  public  static class Selectedcoin_fragment extends  Fragment
    {
        public  Selectedcoin_fragment()
        {

        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState)
        {
            TextView text = new TextView(getActivity());
            text.setText("selected coin fragment");
            text.setGravity(Gravity.CENTER);
            return text;




        }
    }*/
    public  static class Selectedcoin_Alert_fragment extends  Fragment
    {
        public  Selectedcoin_Alert_fragment()
        {

        }
        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState)
        {
            TextView text = new TextView(getActivity());
            text.setText("selected coin alert fragment");
            text.setGravity(Gravity.CENTER);
            return text;
        }
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}


/*
class  Mypager_adapter extends FragmentStatePagerAdapter
{

    public Mypager_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position)
    {
        SelectedCoinActivity.Selectedcoin_fragment fr = new SelectedCoinActivity.Selectedcoin_fragment();
        return fr;
    }

    @Override
    public int getCount() {
        return 2;
    }
}*/
