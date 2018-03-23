package com.currency.decrypto.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.currency.decrypto.R;


/**
 * Created by swetha on 12/31/17.
 */

public class CoinListDetailFragment extends Fragment
{
    public CoinListDetailFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.coinlistdetailfragment,container,false);

        TextView name,symbol,usd_price,btc_price;
        String name_coin = null;
        String name_symbol= null;
        String name_usd =null;
                String name_btc=null;

        name = (TextView)v.findViewById(R.id.textView7);
        symbol = (TextView)v.findViewById(R.id.textView12);
        usd_price = (TextView)v.findViewById(R.id.textView14);
        btc_price = (TextView)v.findViewById(R.id.textView16);

        if(getArguments()!=null)
        {
             name_coin = getArguments().getString("name");
            name_symbol = getArguments().getString("symbol");
             name_usd = getArguments().getString("usd");
             name_btc = getArguments().getString("btc");
        }

        name.setText(name_coin);
        symbol.setText(name_symbol);
        usd_price.setText(name_usd);
        btc_price.setText(name_btc);


       /* TextView name,symbol,usd_price,btc_price;

        name = (TextView)v.findViewById(R.id.textView7);
        symbol = (TextView)v.findViewById(R.id.textView12);
        usd_price = (TextView)v.findViewById(R.id.textView14);
        btc_price = (TextView)v.findViewById(R.id.textView16);

        Intent intent = getIntent();
        String name_coin = intent.getStringExtra("name");
        String name_symbol = intent.getStringExtra("symbol");
        String name_usd = intent.getStringExtra("usd");
        String name_btc = intent.getStringExtra("btc");

        name.setText(name_coin);
        symbol.setText(name_symbol);
        usd_price.setText(name_usd);
        btc_price.setText(name_btc);*/

        //return super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }
}
