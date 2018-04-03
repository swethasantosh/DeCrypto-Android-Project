package com.currency.decrypto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.animation.AnimationEasing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by swetha on 1/9/18.
 */

public class SelectedcoinChart extends Fragment implements  OnChartGestureListener, OnChartValueSelectedListener {
    //static String url = "https://min-api.cryptocompare.com/data/histohour?fsym=BTC&tsym=USD&limit=60&aggregate=3&e=CCCAGG";
    String url = null;
    static ArrayList<String> time_list = new ArrayList<>();
    static ArrayList<Entry> price_list = new ArrayList<>();

    public static final String KEY_DATA="Data";

    public static final String KEY_DASH="";
    public static final String KEY_TIME="time";
    public static final String KEY_HIGH="high";
    static Context context;

    static LineChart mChart;
    View v;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String selectedcoinsymbol =null;
    String selectedcurrencymode;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //send volley json request
        volleySingleton = VolleySingleton.getsInstance();
        //initialize request
        requestQueue = volleySingleton.getRequestQueue();



        //retrieve selectedcoin  from cryptofragment
        Intent i = getActivity().getIntent();
        selectedcoinsymbol = i.getStringExtra( "symbol");

        selectedcurrencymode = i.getStringExtra("currency_choice");


       // url = "https://min-api.cryptocompare.com/data/histohour?fsym="+selectedcoinsymbol+"&tsym=USD&limit=60&aggregate=3&e=CCCAGG";
        url = "https://min-api.cryptocompare.com/data/histoday?fsym="+selectedcoinsymbol+"&tsym="+selectedcurrencymode+"&limit=365&aggregate=3&e=CCCAGG";

        //Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();
        sendJsonRequest(url);


    }




    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.selectedcoin_chart,container,false);
        context = v.getContext();
        mChart = (LineChart) v.findViewById(R.id.lineChart);

        Button hourbutton =(Button)v.findViewById(R.id.hbutton);
        Button d30button = (Button)v.findViewById(R.id.d30button);
        final Button daybutton = (Button)v.findViewById(R.id.day7button);
        Button ybutton = (Button)v.findViewById(R.id.ybutton);







       daybutton.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                url = "https://min-api.cryptocompare.com/data/histohour?fsym="+selectedcoinsymbol+"&tsym="+selectedcurrencymode +"&limit=168&e=CCCAGG";
                sendJsonRequest(url);

            }
        });





        d30button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url="https://min-api.cryptocompare.com/data/histohour?fsym="+selectedcoinsymbol+"&tsym="+selectedcurrencymode+"&limit=720&aggregate=3&e=CCCAGG";
                sendJsonRequest(url);

            }
        });


        hourbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                url = "https://min-api.cryptocompare.com/data/histominute?fsym="+selectedcoinsymbol+"&tsym="+selectedcurrencymode+"&limit=60&aggregate=3&e=CCCAGG";
                sendJsonRequest(url);

            }
        });
        ybutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                url = "https://min-api.cryptocompare.com/data/histoday?fsym="+selectedcoinsymbol+"&tsym="+selectedcurrencymode+"&limit=365&aggregate=3&e=CCCAGG";
                sendJsonRequest(url);

            }
        });

        return v;
    }



    private static void clearChart()
    {

        mChart.removeAllViews();
        mChart.notifyDataSetChanged();
        mChart.invalidate();
        time_list = new ArrayList();
        price_list = new ArrayList();

    }


    private void sendJsonRequest(String url)
    {

        //get selected coin url
        String url_1 = url;
        //Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();

       // JsonObjectRequest request_chart = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        JsonObjectRequest request_chart = new JsonObjectRequest(Request.Method.GET,url_1 , null, new Response.Listener<JSONObject>()

        {
            @Override
            public void onResponse(JSONObject response)
            {
                if(response!=null && response.length()>0)
                {
                    try
                    {
                        clearChart();

                        Log.d("CHART",response.getJSONArray(KEY_DATA).toString());
                        JSONArray jsonArray = response.getJSONArray(KEY_DATA);
                        if(jsonArray.length()>0)
                        {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                long time = Long.valueOf(jsonObject.getString(KEY_TIME)).longValue() * 1000;
                                String high = jsonObject.getString(KEY_HIGH);
                                Timestamp timestamp = new Timestamp(time);
                                time_list.add(dateFormat(context, time));
                                price_list.add(new Entry(Float.valueOf(high).floatValue(), i));
                                Log.d("CHART2", "Timestamp: " + timestamp.toString() + ", Close: " + high + " , time " + time);

                            }
                            setChartAxis(context, mChart, time_list, price_list);
                            Log.d("CHART3", "Min: " + getMinPrice(price_list) + ", Max: " + getMaxPrice(price_list));
                        }
                        else
                            Toast.makeText(context,"Try again later",Toast.LENGTH_LONG).show();

                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                        return;
                    }
                }
                else
                    {
                        Toast.makeText(context,"Try again later",Toast.LENGTH_LONG).show();
                    }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                NetworkResponse networkResponse=error.networkResponse;

            }
        });
        requestQueue.add(request_chart);

    }






    static String dateFormat(Context context, long timeStamp)
    {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd/MM hh:mm a", Locale.getDefault());
        return dateFormat.format(new Date(timeStamp));
    }

    static String getMinPrice(List<Entry> list) {
        float minPrice = ((Entry) list.get(0)).getVal();
        for (int i = 0; i < list.size(); i++) {
            float currentPrice = ((Entry) list.get(i)).getVal();
            if (currentPrice < minPrice) {
                minPrice = currentPrice;
            }
        }
        return String.valueOf(minPrice);
    }

    static String getMaxPrice(List<Entry> list) {
        float maxPrice = ((Entry) list.get(0)).getVal();
        for (int i = 0; i < list.size(); i++) {
            float currentPrice = ((Entry) list.get(i)).getVal();
            if (currentPrice > maxPrice) {
                maxPrice = currentPrice;
            }
        }
        return String.valueOf(maxPrice);
    }
    static void setChartAxis(Context context, LineChart chart, List<String> xAxis, List<Entry> yAxis)
    {
        LineDataSet set = new LineDataSet(yAxis, setDataSetLabel(context, yAxis));
        List dataSets = new ArrayList();
        dataSets.add(set);
        chart.setData(new LineData((List) xAxis, dataSets));
        chart.notifyDataSetChanged();
        chart.invalidate();
        setChartLineStyle(context, set);
    }
    static String setDataSetLabel(Context context, List<Entry> yAxis)
    {
        String selectedCoinSymbol = "BTC";
        String currencyPreference ="BTC";
        String currencySymbol = "$";
        if (selectedCoinSymbol.equals("BTC") && currencyPreference.equals("BTC"))
        {
            return  " (Min Price"  + " $" + getMinPrice(yAxis) + ", " + "Max Price "+ " $" + getMaxPrice(yAxis) + ")";
        }
        return  " (Min Price" +  " " + currencySymbol + getMinPrice(yAxis) + ", " +  " Max Price" + currencySymbol + getMaxPrice(yAxis) + ")";
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public static class CustomMarkerView extends MarkerView
    {
        private TextView tvContent = ((TextView) findViewById(R.id.tvContent));

        public CustomMarkerView(Context context, int layoutResource)
        {
            super(context, layoutResource);
        }

        @Override
        public void refreshContent(Entry e, int dataSetIndex)
        {
            this.tvContent.setText("" + e.getVal());


        }

      @Override
        public int getXOffset() {
            return 0;
        }

        @Override
        public int getYOffset() {
            return 0;
        }

        public void refreshContent(Entry e, Highlight highlight)
        {
            this.tvContent.setText("" + e.getVal());
        }

        public int getXOffset(float xPos) {
            return -(getWidth() / 2);
        }

        public int getYOffset(float yPos) {
            return -getHeight();
        }
    }
    static void setChartStyle(Context context, OnChartGestureListener gestureListener, OnChartValueSelectedListener chartValueSelectedListener, LineChart chart) {
        setChartUtils(gestureListener, chartValueSelectedListener, chart);
        setChartLegend(chart);
        setChartDescription(context, chart);
        setChartAxisStyle(chart);
    }
    static void setChartUtils(OnChartGestureListener chartGestureListener, OnChartValueSelectedListener chartValueSelectedListener, LineChart chart) {
        chart.setOnChartGestureListener(chartGestureListener);
        chart.setOnChartValueSelectedListener(chartValueSelectedListener);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setMarkerView(new CustomMarkerView(context, R.layout.marker_view));
        chart.animateX((int) 6, AnimationEasing.EasingOption.EaseInOutQuart);
    }
    static void setChartLegend(LineChart chart) {
        Legend legend = chart.getLegend();
        legend.setTextColor(-1);

        legend.setForm(Legend.LegendForm.LINE);
    }
    static void setChartLineStyle(Context context, LineDataSet dataSet) {
        dataSet.setColor(ContextCompat.getColor(context, R.color.greencolor));
        //dataSet.setFillColor(-7829368);
        dataSet.setFillColor(-6829596);

        dataSet.setDrawValues(false);
        dataSet.setCircleColor(ContextCompat.getColor(context, R.color.greencolor));
        //dataSet.setCircleRadius(4);
        dataSet.setDrawCircleHole(false);
        //dataSet.setLineWidth(C1287e.f1271c);
        dataSet.setLineWidth(3);

        dataSet.setValueTextSize(9.0f);
        dataSet.setDrawFilled(true);
        dataSet.setValueTextColor(-1);

    }
    @NonNull
    static String getChartDescription(Context context)
    {
        String selectedCoinSymbol = "BTC";
        String currencyPreference ="BTC";
        if (selectedCoinSymbol.equals("BTC") && currencyPreference.equals("BTC"))
        {
            return "BTC/USD";
        }
        return selectedCoinSymbol + "/" + currencyPreference;
    }

    static void setChartDescription(Context context, LineChart chart)
    {
        chart.setDescription(getChartDescription(context));
        //chart.setDescriptionColor(-1);
        chart.setNoDataTextDescription("something_went_wrong");
    }
    static void setChartAxisStyle(LineChart chart)
    {
        chart.getXAxis().setTextColor(-1);
        chart.getAxisRight().setTextColor(-1);
        chart.getAxisLeft().setEnabled(false);
    }

}
