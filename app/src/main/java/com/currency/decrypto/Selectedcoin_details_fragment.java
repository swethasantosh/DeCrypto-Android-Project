package com.currency.decrypto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.currency.decrypto.Keys.Keys.Currency_keys.KEY_NAME;
import static com.currency.decrypto.Keys.Keys.Currency_keys.KEY_PRICE_BTC;
import static com.currency.decrypto.Keys.Keys.Currency_keys.KEY_PRICE_USD;
import static com.currency.decrypto.Keys.Keys.Currency_keys.KEY_SYMBOL;

/**
 * Created by swetha on 1/3/18.
 */

public class Selectedcoin_details_fragment extends Fragment
{

   /* public static final String url = "https://api.coinmarketcap.com/v1/ticker/";
    Context context;
    RelativeLayout layoutContainer;

    static TextView name_texxtview,symbol_tv,usd_price_tv,btc_price_tv;*/
   View v;


    public  Selectedcoin_details_fragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.v = inflater.inflate(R.layout.coinlistdetailfragment,container,false);

         TextView name,symbol,ranktv,usd_price,btc_price,marketcap,totalsupply,maxxsupply,avail_supply,perchange1,perchange24,percnhane7d;

        name = (TextView)v.findViewById(R.id.textView7);
        symbol = (TextView)v.findViewById(R.id.textView12);
        ranktv = (TextView)v.findViewById(R.id.rank);
        usd_price = (TextView)v.findViewById(R.id.textView14);
        btc_price = (TextView)v.findViewById(R.id.textView16);
        marketcap = (TextView)v.findViewById(R.id.textView18);
        totalsupply = (TextView)v.findViewById(R.id.textView22);
        maxxsupply = (TextView)v.findViewById(R.id.textView24);
        avail_supply = (TextView)v.findViewById(R.id.textView20);
        perchange1 = (TextView)v.findViewById(R.id.textView26);
        perchange24 = (TextView)v.findViewById(R.id.textView28);
        percnhane7d= (TextView)v.findViewById(R.id.textView30);




        Intent intent = getActivity().getIntent();
        String name_coin = intent.getStringExtra("name");
        String name_symbol = intent.getStringExtra("symbol");
        String rank = intent.getStringExtra("rank");
        String name_usd = intent.getStringExtra("usd");
        String name_btc = intent.getStringExtra("btc");


        String market_cap_usd = intent.getStringExtra("market_cap_usd");
        String available_supply = intent.getStringExtra("available_supply");
        String total_supply = intent.getStringExtra("total_supply");
        String max_supply = intent.getStringExtra("max_supply");
        String percent_change_1h = intent.getStringExtra("percent_change_1h");
        String percent_change_24h = intent.getStringExtra("percent_change_24h");
        String percent_change_7d = intent.getStringExtra("percent_change_7d");




        name.setText(name_coin);
        symbol.setText(name_symbol);
        ranktv.setText(rank);
        usd_price.setText(name_usd);
        btc_price.setText(name_btc);
        marketcap.setText(market_cap_usd);
        totalsupply.setText(total_supply);
        maxxsupply.setText(max_supply);
        avail_supply.setText(available_supply);
        perchange1.setText(percent_change_1h+"%");
        perchange24.setText(percent_change_24h+"%");
        percnhane7d.setText(percent_change_7d+"%");

        if(percent_change_1h.contains("-"))
        {
            perchange1.setTextColor(Color.parseColor("#f44336"));
        }
        else
        {
            perchange1.setTextColor(Color.parseColor("#00c853"));

        }
        if(percent_change_24h.contains("-"))
        {
            perchange24.setTextColor(Color.parseColor("#f44336"));
        }
        else
        {
            perchange24.setTextColor(Color.parseColor("#00c853"));

        }
        if(percent_change_7d.contains("-"))
        {
            percnhane7d.setTextColor(Color.parseColor("#f44336"));
        }
        else
        {
            percnhane7d.setTextColor(Color.parseColor("#00c853"));

        }


            /*TextView text = new TextView(getActivity());
            text.setText("selected coin fragment");
            text.setGravity(Gravity.CENTER);
            return text;*/
        return this.v;

    }
   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(this.rootview);
        retrieveData(this.rootview.getContext());

    }
    private void init(View v)
    {

        layoutContainer = (RelativeLayout) v.findViewById(R.id.relativecontainer);

        name_texxtview = (TextView)v.findViewById(R.id.textView7);
        symbol_tv = (TextView)v.findViewById(R.id.textView12);
        usd_price_tv = (TextView)v.findViewById(R.id.textView14);
        btc_price_tv = (TextView)v.findViewById(R.id.textView16);
    }
    private void retrieveData(Context context)
    {
            try {
                getCoinMarketCapData(context);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

    }

    public static void getCoinMarketCapData(final Context context) throws JSONException
    {
        String currency = Crypto_Fragment.getCurrencyPreference(context);
        Toast.makeText(context,currency,Toast.LENGTH_LONG).show();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url+"?convert="+currency, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                int i =0;
                while (i<response.length())
                {
                    try {


                    JSONObject jsonObject = response.getJSONObject(i);
                    String name = jsonObject.getString(KEY_NAME);
                    String symbol = jsonObject.getString(KEY_SYMBOL);
                    String price_usd = jsonObject.getString(KEY_PRICE_USD);
                    String price_btc = jsonObject.getString(KEY_PRICE_BTC);
                        if(Crypto_Fragment.selectedcoinname.equals(name))
                        {
                           name_texxtview.setText(name);
                            symbol_tv.setText(symbol);
                            usd_price_tv.setText(price_usd);
                            btc_price_tv.setText(price_btc);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



    }*/


}
