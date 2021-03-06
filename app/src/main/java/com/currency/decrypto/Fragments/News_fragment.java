package com.currency.decrypto.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.currency.decrypto.Adapters.News_adapter;
import com.currency.decrypto.Crypto_Fragment;
import com.currency.decrypto.Models.News;
import com.currency.decrypto.News_openActivity;
import com.currency.decrypto.R;
import com.currency.decrypto.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.currency.decrypto.Keys.News_keys.News_keysinterface.*;




/**
 * Created by swetha on 12/23/17.
 */

public class News_fragment extends Fragment implements News_adapter.Clicklistener
{
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    //Arraylist to store list of  markets
    private ArrayList<News> list_articles = new ArrayList<>();
    private RecyclerView news_list;
    //adapter for customized list
    private News_adapter news_adapter;
    private Context context;



    //url
    //public static final String url = "https://api.cryptonator.com/api/full/btc-usd";
    public static final String url = "https://min-api.cryptocompare.com/data/news/";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public News_fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static News_fragment newInstance(String param1, String param2) {
        News_fragment fragment = new News_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {

                progressDialog.dismiss();
                parseJsonResponse(response);
                list_articles = parseJsonResponse(response);
                news_adapter.setArticlelist(list_articles);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        //add json request to requestqeue
        requestQueue.add(request);

    }

    //change return type from void to arraylist of currency
    //private void parseJsonResponse(JSONObject response) {




    private ArrayList<News> parseJsonResponse(JSONArray response)




//    private ArrayList<Currency> parseJsonResponse(JSONObject response)
    {
        //create an empty array list to return  even if any exception occurs
        ArrayList<News> list_articles = new ArrayList<>();

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
                    String title = jsonObject.getString(KEY_TITLE);
                    String websitename = jsonObject.getString(KEY_SOURCE);

                    String imageurl = jsonObject.getString(KEY_IMAGEURL);
                    String body = jsonObject.getString(KEY_BODY);
                    String news_url = jsonObject.getString(KEY_URL);
                    //convert from unixtimestamp
                    String time = jsonObject.getString(KEY_PUBLISHEDON);


                    long time_long = Long.valueOf(time).longValue()*1000;
                    DateFormat dateFormat =  new SimpleDateFormat("hh:mm a",Locale.getDefault());
                    String localtime = dateFormat.format(new Date(time_long));







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
                    News news = new News();
                    //initialize using setter methods

                    news.setTitle(title);
                    news.setPublished_on(localtime);
                    //news.setPublished_on(datestring);
                    news.setSource(websitename);
                    news.setImageurl(imageurl);
                    news.setUrl(news_url);
                    news.setBody(body);


                    /*currency.setMarket(marketname);
                    currency.setMarket_price(marketprice);
                    currency.setMarket_volume(marketvolume);*/

                    list_articles.add(news);
                }


                // Toast.makeText(getActivity(), "marketname" + base+"\n"+target+"\n"+price+"\n"+data, Toast.LENGTH_LONG).show();
                // Toast.makeText(getActivity(), "marketname" + list_markets.toString() + "\n", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            swipeRefreshLayout.setRefreshing(false);


        }
        return list_articles;

    }

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_news,container,false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar_include);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("News");



        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipelayout_news);

        news_list = (RecyclerView)view.findViewById(R.id.list_news);
        news_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initialize adapter by passing getactivity
        news_adapter = new News_adapter(getActivity());

        //recyler touch adapter
        news_adapter.setClicklistener(this);


        news_list.setAdapter(news_adapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                sendJsonRequest();

            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(android.R.color.holo_blue_dark));


        sendJsonRequest();

        // Inflate the layout for this fragment
        return view;
    }

//Recyclerview item click
    @Override
    public void itemclicked(View view, int position, String title, String body, String url, String imageurl)
    {

        //Intent intent = new Intent(getActivity(),News_openActivity.class);
        Intent intent = new Intent(this.getActivity(),News_openActivity.class);

        intent.putExtra("message",title);
        intent.putExtra("body",body);

        intent.putExtra("url",url);
        intent.putExtra("imageurl",imageurl);

        startActivity(intent);
       // startActivity(new Intent(getActivity(),News_openActivity.class));


    }

}

