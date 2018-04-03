package com.currency.decrypto;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.currency.decrypto.Adapters.Currency_Adapter;
import com.currency.decrypto.Keys.Keys;
import com.currency.decrypto.Models.Currency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import  static com.currency.decrypto.Keys.Keys.Currency_keys.*;


public class Crypto_Fragment extends Fragment implements  Currency_Adapter.Clicklistener,SearchView.OnQueryTextListener

{
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    //Arraylist to store list of  markets
    private ArrayList<Currency> list_markets = new ArrayList<>();
    private RecyclerView currency_list;
    //adapter for customized list
    private Currency_Adapter currency_adapter;
    Context context;
    public static String selectedcurrencymode;
    String price_common;


   static  String selectedcoinname="null";

    //url
    //public static final String url = "https://api.cryptonator.com/api/full/btc-usd";
   // public static final String url = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
    //public static final String url = "https://api.coinmarketcap.com/v1/ticker/";
    public static final String url = "https://api.coinmarketcap.com/v1/ticker/";



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Crypto_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Crypto_Fragment newInstance(String param1, String param2) {
        Crypto_Fragment fragment = new Crypto_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //send volley json request
        volleySingleton = VolleySingleton.getsInstance();
        //initialize request
        requestQueue = volleySingleton.getRequestQueue();


        sendJsonRequest();


    }


    private void sendJsonRequest()
    {

        //swipeRefreshLayout.setRefreshing(true);



        //get value from settings screen to set number of items to display
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String setting = preferences.getString("value_tobe_displayed","10");
        //key value from settings_screen.xxml file
        selectedcurrencymode = preferences.getString("currency_selection","USD");
        //Toast.makeText(getActivity(),selectedcurrencymode,Toast.LENGTH_LONG).show();


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading data");
        progressDialog.show();

        //to set time limit for progress dismiss
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                progressDialog.dismiss();
            }
        }, 6000);





        //json array request
        //JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url+"?limit="+setting, null, new Response.Listener<JSONArray>()
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url+"?convert="+selectedcurrencymode+"&limit="+setting, null, new Response.Listener<JSONArray>()

        {
            @Override
            public void onResponse(JSONArray response)
            {
                progressDialog.dismiss();


                parseJsonResponse(response);
                list_markets = parseJsonResponse(response);
                currency_adapter.setCurrencylist(list_markets);
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {


            }
        });


        /*
        //json object request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getActivity(), "response" + response, Toast.LENGTH_LONG).show();
                parseJsonResponse(response);
                list_markets = parseJsonResponse(response);
                currency_adapter.setCurrencylist(list_markets);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
*/




        //add json request to requestqeue
        requestQueue.add(request);

    }

    //change return type from void to arraylist of currency
    //private void parseJsonResponse(JSONObject response) {




    private ArrayList<Currency> parseJsonResponse(JSONArray response)

//    private ArrayList<Currency> parseJsonResponse(JSONObject response)
    {
        //create an empty array list to return  even if any exception occurs
        ArrayList<Currency> list_markets = new ArrayList<>();

       /* if (response == null || response.length() == 0) {
            return;
        }*/

        //get jsonarray response
        //use if response.has() method to search for desired key inside try block

        if(response !=null && response.length() > 0) {
            try {
                StringBuilder data = new StringBuilder();

                //JSONArray arrayMarkets = response.getJSONArray(KEY_MARKETS);

                //Currency currency = new Currency();


                for(int i = 0;i < response.length();i++)
                {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String name = jsonObject.getString(KEY_NAME);
                    String symbol = jsonObject.getString(KEY_SYMBOL);
                   // String price_usd = jsonObject.getString(KEY_PRICE_USD);
                    String price_btc = jsonObject.getString(KEY_PRICE_BTC);
                    String rank = jsonObject.getString(KEY_RANK);



                    String Hvolumeusd = jsonObject.getString(KEY_24H_VOLUME_USD);
                    String market_cap_usd = jsonObject.getString(KEY_MARKETCAP_USD);
                    String available_supply = jsonObject.getString(KEY_AVAILABLESUPPLY);
                    String total_supply = jsonObject.getString(KEY_TOTALSUPPLY);
                    String max_supply = jsonObject.getString(KEY_MAXSUPPLY);
                    String percent_change_1h = jsonObject.getString(KEY_PERCENTCAHNGE_1H);
                    String percent_change_24h = jsonObject.getString(KEY_PERCENTCAHNGE_24H);
                    String percent_change_7d = jsonObject.getString(KEY_PERCENTCAHNGE_7D);
                    String last_updated = jsonObject.getString(KEY_LASTUPDATED);






              /*  JSONObject tickers = response.getJSONObject(KEY_TICKER);
                String base = tickers.getString(KEY_BASE);
                String target = tickers.getString(KEY_TARGET);
                String price = tickers.getString(KEY_PRICE);


                //iside markets array we have objects,get them in for loop

                JSONArray markets = tickers.getJSONArray(KEY_MARKETS);
                for (int i = 0; i < markets.length(); i++) {
                    JSONObject currentmarket = markets.getJSONObject(i);
                    String marketname = currentmarket.getString(KEY_MARKET);
                    String marketprice = currentmarket.getString(KEY_MARKET_PRICE);
                    String marketvolume = currentmarket.getString(KEY_MARKET_VOLUME);
                    data.append(marketname + "\n"); */


                    //new currency object
                    Currency currency = new Currency();
                    //initialize using setter methods


                    switch (selectedcurrencymode)
                    {
                        case "AUD":
                            price_common = jsonObject.getString(KEY_PRICE_AUD);
                            currency.setPrice_aud(price_common);
                            break;
                        case "USD":
                            price_common = jsonObject.getString(KEY_PRICE_USD);
                            currency.setPrice_usd(price_common);
                            break;
                        case "INR":
                            price_common = jsonObject.getString(KEY_PRICE_INR);
                            currency.setPrice_inr(price_common);
                            break;
                        case "MYR":
                            price_common = jsonObject.getString(KEY_PRICE_MYR);
                            currency.setPrice_myr(price_common);
                            break;
                        case "EUR":
                            price_common = jsonObject.getString(KEY_PRICE_EUR);
                            currency.setPrice_eur(price_common);
                            break;
                        case "BRL":
                            price_common = jsonObject.getString(KEY_PRICE_BRL);
                            currency.setPrice_brl(price_common);
                            break;
                        case "CAD":
                            price_common = jsonObject.getString(KEY_PRICE_CAD);
                            currency.setPrice_cad(price_common);
                            break;
                        case "CHF":
                            price_common = jsonObject.getString(KEY_PRICE_CHF);
                            currency.setPrice_chf(price_common);
                            break;
                        case "CLP":
                            price_common = jsonObject.getString(KEY_PRICE_CLP);
                            currency.setPrice_clp(price_common);
                            break;
                        case "CNY":
                            price_common = jsonObject.getString(KEY_PRICE_CNY);
                            currency.setPrice_cny(price_common);
                            break;
                        case "CZK":
                             price_common = jsonObject.getString(KEY_PRICE_CZK);
                             currency.setPrice_czk(price_common);
                             break;
                        case "DKK":
                            price_common = jsonObject.getString(KEY_PRICE_DKK);
                            currency.setPrice_dkk(price_common);
                            break;
                        case "GBP":
                            price_common = jsonObject.getString(KEY_PRICE_GBP);
                            currency.setPrice_gbp(price_common);
                            break;
                        case "HKD":
                            price_common = jsonObject.getString(KEY_PRICE_HKD);
                            currency.setPrice_hkd(price_common);
                            break;
                        case "HUF":
                            price_common = jsonObject.getString(KEY_PRICE_HUF);
                            currency.setPrice_huf(price_common);
                            break;
                        case "IDR":
                            price_common = jsonObject.getString(KEY_PRICE_IDR);
                            currency.setPrice_idr(price_common);
                            break;
                        case "ILS":
                            price_common = jsonObject.getString(KEY_PRICE_ILS);
                            currency.setPrice_ils(price_common);
                            break;
                        case "JPY":
                            price_common = jsonObject.getString(KEY_PRICE_JPY);
                            currency.setPrice_jpy(price_common);
                            break;
                        case "KRW":
                            price_common = jsonObject.getString(KEY_PRICE_KRW);
                            currency.setPrice_krw(price_common);
                            break;
                        case "MXN":
                            price_common = jsonObject.getString(KEY_PRICE_MXN);
                            currency.setPrice_mxn(price_common);
                            break;
                        case "NZD":
                            price_common = jsonObject.getString(KEY_PRICE_NZD);
                            currency.setPrice_nzd(price_common);
                            break;
                        case "NOK":
                            price_common = jsonObject.getString(KEY_PRICE_NOK);
                            currency.setPrice_nok(price_common);
                            break;
                        case "PLN":
                            price_common = jsonObject.getString(KEY_PRICE_PLN);
                            currency.setPrice_pln(price_common);
                            break;
                        case "RUB":
                            price_common = jsonObject.getString(KEY_PRICE_RUB);
                            currency.setPrice_rub(price_common);
                            break;
                        case "SEK":
                            price_common = jsonObject.getString(KEY_PRICE_SEK);
                            currency.setPrice_sek(price_common);
                            break;
                        case "SGD":
                            price_common = jsonObject.getString(KEY_PRICE_SGD);
                            currency.setPrice_sgd(price_common);
                            break;
                        case "THB":
                            price_common = jsonObject.getString(KEY_PRICE_THB);
                            currency.setPrice_thb(price_common);
                            break;
                        case "TRY":
                            price_common = jsonObject.getString(KEY_PRICE_TRY);
                            currency.setPrice_try(price_common);
                            break;
                        case "TWD":
                            price_common = jsonObject.getString(KEY_PRICE_TWD);
                            currency.setPrice_twd(price_common);
                            break;
                        case "ZAR":
                            price_common = jsonObject.getString(KEY_PRICE_ZAR);
                            currency.setPrice_zar(price_common);
                            break;
                    }


                    currency.setName(name);
                    currency.setSymbol(symbol);
                    //currency.setPrice_usd(price_usd);
                    currency.setPrice_btc(price_btc);
                    currency.setRank(rank);


                    currency.setHvolumeusd(Hvolumeusd);
                    currency.setMarket_cap_usd(market_cap_usd);
                    currency.setAvailable_supply(available_supply);
                    currency.setTotal_supply(total_supply);
                    currency.setMax_supply(max_supply);
                    currency.setPercent_change_1h(percent_change_1h);
                    currency.setPercent_change_24h(percent_change_24h);
                    currency.setPercent_change_7d(percent_change_7d);
                    currency.setLast_updated(last_updated);


                    /*currency.setMarket(marketname);
                    currency.setMarket_price(marketprice);
                    currency.setMarket_volume(marketvolume);*/

                    list_markets.add(currency);
                }
                // Toast.makeText(getActivity(), "marketname" + base+"\n"+target+"\n"+price+"\n"+data, Toast.LENGTH_LONG).show();
               // Toast.makeText(getActivity(), "marketname" + list_markets.toString() + "\n", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


            swipeRefreshLayout.setRefreshing(false);

        }
        return list_markets;
    }

        //defined in fragment_crypto nested scrollview
        BottomSheetBehavior bottomSheetBehavior ;
        NestedScrollView sheet;

        SwipeRefreshLayout swipeRefreshLayout;

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            View view = inflater.inflate(R.layout.fragment_crypto,container,false);



            Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar_include);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            setHasOptionsMenu(true);


            swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.layout);

            currency_list = (RecyclerView)view.findViewById(R.id.market_list);
            currency_list.setLayoutManager(new LinearLayoutManager(getActivity()));

            //initialize adapter by passing getactivity
            currency_adapter = new Currency_Adapter(getActivity());


            //recyclerview adapter assign
            currency_adapter.setClicklistener(this);

            currency_list.setAdapter(currency_adapter);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    sendJsonRequest();

                }
            });
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(android.R.color.holo_blue_dark));
           /* swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run()
                {
                    swipeRefreshLayout.setRefreshing(true);
                    sendJsonRequest();

                }
            });*/




            sendJsonRequest();

            // Inflate the layout for this fragment
            return view;

        }





    @Override

    public void itemclicked(View view,int position,String currencyname,String symbol,String rank,String usd,String btc,
                            String Hvolumeusd,String market_cap_usd,String available_supply,String total_supply,
                             String max_supply,String percent_change_1h,String percent_change_24h,
                            String percent_change_7d,String last_updated)
    {


        selectedcoinname = currencyname;

        Intent intent = new Intent(getActivity(),SelectedCoinActivity.class);


        intent.putExtra("currency_choice",selectedcurrencymode);
       intent.putExtra("name",currencyname);
        intent.putExtra("symbol",symbol);
        intent.putExtra("rank",rank);
        intent.putExtra("usd",usd);
        intent.putExtra("btc",btc);
        intent.putExtra("Hvolumeusd",Hvolumeusd);

        intent.putExtra("market_cap_usd",market_cap_usd);
        intent.putExtra("available_supply",available_supply);
        intent.putExtra("total_supply",total_supply);
        intent.putExtra("max_supply",max_supply);
        intent.putExtra("percent_change_1h",percent_change_1h);
        intent.putExtra("percent_change_24h",percent_change_24h);
        intent.putExtra("percent_change_7d",percent_change_7d);
        intent.putExtra("last_updated",last_updated);


        startActivity(intent);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_item,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);

    }

    /*static String getCurrencyPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Keys.Currency_keys.KEY_NAME, "Bitcoin");
    }*/

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        newText = newText.toLowerCase();
        ArrayList<Currency> newlist = new ArrayList<>();
        for (Currency currency: list_markets)
        {
            String name = currency.getName().toLowerCase();
            String symbol = currency.getSymbol().toLowerCase();
            if(name.contains(newText)||symbol.contains(newText))
            {
                newlist.add(currency);
            }
        }
        currency_adapter.setFilterlist(newlist);
        return false;
    }



}

