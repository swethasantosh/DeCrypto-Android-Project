package com.currency.decrypto.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.currency.decrypto.Crypto_Fragment;
import com.currency.decrypto.Models.Currency;
import com.currency.decrypto.R;
import com.currency.decrypto.Sharedpreferences;
import com.currency.decrypto.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swetha on 12/18/17.
 */

public class Favoriteslist_Adapter extends RecyclerView.Adapter<Favoriteslist_Adapter.ViewHolder_Currency>
{
    //create array list to store current  item
    private ArrayList<Currency> currencylist = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private Context context;
    private Clicklistener clicklistener;

    //    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    String selectedcurrency = Crypto_Fragment.selectedcurrencymode;



    //filter lisst
    public void setFilterlist(ArrayList<Currency> newlist)
    {
        currencylist = new ArrayList<>();
        currencylist.addAll(newlist);
        notifyDataSetChanged();
    }



    public Favoriteslist_Adapter(Context context,ArrayList<Currency> arrayList)
    {
        this.currencylist = arrayList;
    }

    //constructor
    public Favoriteslist_Adapter(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    //set arraylist
    public void setCurrencylist(ArrayList<Currency> currencylist)
    {
        this.currencylist = currencylist;
        //notify data has been changed
        notifyItemRangeChanged(0,currencylist.size());
    }
    @Override
    public ViewHolder_Currency onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.custom_currency_list,parent,false);
        //pass view obj to inflate layout
        ViewHolder_Currency viewHolder_currency = new ViewHolder_Currency(view);
        return viewHolder_currency;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_Currency holder, int position)
    {
        //gives the current item at position
        final Currency current_currency = currencylist.get(position);

        holder.name.setText(current_currency.getName());
        holder.symbol.setText(current_currency.getSymbol());
        //holder.price_usd.setText(current_currency.getPrice_usd());


        //String roundedprice = String.format("%.2f",current_currency.getPrice_btc());
        holder.price_btc.setText(current_currency.getPrice_btc());



        holder.rank.setText(current_currency.getRank());
        holder.hchange.setText(current_currency.getPercent_change_1h()+"%");
        holder.daychange.setText(current_currency.getPercent_change_24h()+"%");
        holder.weekchange.setText(current_currency.getPercent_change_7d()+"%");

        if(current_currency.getPercent_change_1h().toString().contains("-"))
        {
            holder.hchange.setTextColor(Color.parseColor("#ff0000"));

        }else
        {
            holder.hchange.setTextColor(Color.parseColor("#00ff80"));

        }
        if(current_currency.getPercent_change_24h().toString().contains("-"))
        {
            holder.daychange.setTextColor(Color.parseColor("#ff0000"));

        }else
        {
            holder.daychange.setTextColor(Color.parseColor("#00ff80"));

        }if(current_currency.getPercent_change_7d().toString().contains("-"))
    {
        holder.weekchange.setTextColor(Color.parseColor("#ff0000"));

    }else
    {
        holder.weekchange.setTextColor(Color.parseColor("#00ff80"));

    }
        switch (selectedcurrency)
        {
            case "AUD":
                holder.price_selection.setText(current_currency.getPrice_aud()+" A$");
                break;
            case "USD":
                holder.price_selection.setText(current_currency.getPrice_usd()+" $");
                break;
            case "INR":
                holder.price_selection.setText( String.valueOf(Math.round(Float.parseFloat(current_currency.getPrice_inr())))+" ₹");
                //holder.price_selection.setText(current_currency.getPrice_inr()+"₹");
                //holder.price_selection.setText(Math.round(Float.parseFloat(current_currency.getPrice_inr())));

                break;
            case "MYR":
                holder.price_selection.setText(current_currency.getPrice_myr()+" RM");

                break;
            case "EUR":
                holder.price_selection.setText(current_currency.getPrice_eur()+" €");

                break;
            case "BRL":
                holder.price_selection.setText(current_currency.getPrice_brl()+" R$");

                break;
            case "CAD":
                holder.price_selection.setText(current_currency.getPrice_cad()+" C$");

                break;
            case "CHF":
                holder.price_selection.setText(current_currency.getPrice_chf()+" Fr");

                break;
            case "CLP":
                holder.price_selection.setText(current_currency.getPrice_clp()+" $");

                break;
            case "CNY":
                holder.price_selection.setText(current_currency.getPrice_cny()+" ¥");

                break;
            case "CZK":
                holder.price_selection.setText(current_currency.getPrice_czk()+" Kč");

                break;
            case "DKK":
                holder.price_selection.setText(current_currency.getPrice_dkk()+" kr");

                break;
            case "GBP":
                holder.price_selection.setText(current_currency.getPrice_gbp()+" £");

                break;
            case "HKD":
                holder.price_selection.setText(current_currency.getPrice_hkd()+" HK$");

                break;
            case "HUF":
                holder.price_selection.setText(current_currency.getPrice_huf()+" €");

                break;
            case "IDR":
                holder.price_selection.setText(current_currency.getPrice_idr()+" Rp");

                break;
            case "ILS":
                holder.price_selection.setText(current_currency.getPrice_ils()+" ₪");

                break;
            case "JPY":
                holder.price_selection.setText(current_currency.getPrice_jpy()+" ¥");

                break;
            case "KRW":
                holder.price_selection.setText(current_currency.getPrice_krw()+" ₩");

                break;
            case "MXN":
                holder.price_selection.setText(current_currency.getPrice_mxn()+" $");

                break;
            case "NZD":
                holder.price_selection.setText(current_currency.getPrice_nzd()+" $");

                break;
            case "NOK":
                holder.price_selection.setText(current_currency.getPrice_nok()+" kr");

                break;
            case "PLN":
                holder.price_selection.setText(current_currency.getPrice_pln()+" zł");

                break;
            case "RUB":
                holder.price_selection.setText(current_currency.getPrice_rub()+" \u20BD");

                break;
            case "SEK":
                holder.price_selection.setText(current_currency.getPrice_sek()+" kr");

                break;
            case "SGD":
                holder.price_selection.setText(current_currency.getPrice_sgd()+" $");

                break;
            case "THB":
                holder.price_selection.setText(current_currency.getPrice_thb()+" ฿");

                break;
            case "TRY":
                holder.price_selection.setText(current_currency.getPrice_try()+" TRY");

                break;
            case "TWD":
                holder.price_selection.setText(current_currency.getPrice_twd()+" NT$");

                break;
            case "ZAR":
                holder.price_selection.setText(current_currency.getPrice_zar()+" R");

                break;


        }


    }



    //recycler item click
    public void setClicklistener(Clicklistener clicklistener)
    {
        this.clicklistener = clicklistener;
    }


    @Override
    public int getItemCount()
    {
        return currencylist.size();
    }


    //static  class ViewHolder_Currency extends RecyclerView.ViewHolder implements View.OnClickListener {
    class ViewHolder_Currency extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView name,symbol,price_selection,price_btc,hchange,daychange,weekchange,rank;
        private ImageButton favorite_icon;


        public ViewHolder_Currency(View itemView)
        {
            super(itemView);

            //recyclerview single item click
            itemView.setOnClickListener(this);

            name = (TextView)itemView.findViewById(R.id.textView);
            symbol = (TextView)itemView.findViewById(R.id.textView2);
            price_selection= (TextView)itemView.findViewById(R.id.textView3);
            price_btc = (TextView)itemView.findViewById(R.id.textView4);
            rank=(TextView)itemView.findViewById(R.id.ranktv);

            //favorite_icon = (ImageButton) itemView.findViewById(R.id.favoriteicon);



            hchange = (TextView)itemView.findViewById(R.id.textView22);
            daychange = (TextView)itemView.findViewById(R.id.textView23);
            weekchange = (TextView)itemView.findViewById(R.id.textView25);

        }










        //recyclerview single row  click
        @Override
        public void onClick(View v)
        {
            if(clicklistener!= null)
            {
                String currency_name = currencylist.get(getPosition()).getName();
                String currency_symbol = currencylist.get(getPosition()).getSymbol();
                //String currency_value = currencylist.get(getPosition()).getPrice_usd();
                String btcprice = currencylist.get(getPosition()).getPrice_btc();
                String rank = currencylist.get(getPosition()).getRank();

                String Hvolumeusd = currencylist.get(getPosition()).getHvolumeusd();
                String market_cap_usd = currencylist.get(getPosition()).getMarket_cap_usd();
                String available_supply = currencylist.get(getPosition()).getAvailable_supply();
                String total_supply = currencylist.get(getPosition()).getTotal_supply();
                String max_supply = currencylist.get(getPosition()).getMax_supply();
                String percent_change_1h = currencylist.get(getPosition()).getPercent_change_1h();
                String percent_change_24h = currencylist.get(getPosition()).getPercent_change_24h();
                String percent_change_7d = currencylist.get(getPosition()).getPercent_change_7d();
                String last_updated = currencylist.get(getPosition()).getLast_updated();


                String currency_value = null;

                switch (selectedcurrency)
                {
                    case "AUD":
                        currency_value = currencylist.get(getPosition()).getPrice_usd();

                        break;
                    case "USD":
                        currency_value = currencylist.get(getPosition()).getPrice_usd();
                        break;
                    case "INR":
                        currency_value = currencylist.get(getPosition()).getPrice_inr();
                        break;
                    case "MYR":
                        currency_value = currencylist.get(getPosition()).getPrice_myr();

                        break;
                    case "EUR":
                        currency_value = currencylist.get(getPosition()).getPrice_eur();

                        break;
                    case "BRL":
                        currency_value = currencylist.get(getPosition()).getPrice_brl();

                        break;
                    case "CAD":
                        currency_value = currencylist.get(getPosition()).getPrice_cad();

                        break;
                    case "CHF":
                        currency_value = currencylist.get(getPosition()).getPrice_chf();

                        break;
                    case "CLP":
                        currency_value = currencylist.get(getPosition()).getPrice_clp();

                        break;
                    case "CNY":
                        currency_value = currencylist.get(getPosition()).getPrice_cny();

                        break;
                    case "CZK":
                        currency_value = currencylist.get(getPosition()).getPrice_czk();

                        break;
                    case "DKK":
                        currency_value = currencylist.get(getPosition()).getPrice_dkk();

                        break;
                    case "GBP":
                        currency_value = currencylist.get(getPosition()).getPrice_gbp();

                        break;
                    case "HKD":
                        currency_value = currencylist.get(getPosition()).getPrice_hkd();

                        break;
                    case "HUF":
                        currency_value = currencylist.get(getPosition()).getPrice_huf();

                        break;
                    case "IDR":
                        currency_value = currencylist.get(getPosition()).getPrice_idr();

                        break;
                    case "ILS":
                        currency_value = currencylist.get(getPosition()).getPrice_ils();

                        break;
                    case "JPY":
                        currency_value = currencylist.get(getPosition()).getPrice_jpy();

                        break;
                    case "KRW":
                        currency_value = currencylist.get(getPosition()).getPrice_krw();

                        break;
                    case "MXN":
                        currency_value = currencylist.get(getPosition()).getPrice_mxn();

                        break;
                    case "NZD":
                        currency_value = currencylist.get(getPosition()).getPrice_nzd();

                        break;
                    case "NOK":
                        currency_value = currencylist.get(getPosition()).getPrice_nok();

                        break;
                    case "PLN":
                        currency_value = currencylist.get(getPosition()).getPrice_pln();

                        break;
                    case "RUB":
                        currency_value = currencylist.get(getPosition()).getPrice_rub();

                        break;
                    case "SEK":
                        currency_value = currencylist.get(getPosition()).getPrice_sek();

                        break;
                    case "SGD":
                        currency_value = currencylist.get(getPosition()).getPrice_sgd();

                        break;
                    case "THB":
                        currency_value = currencylist.get(getPosition()).getPrice_thb();

                        break;
                    case "TRY":
                        currency_value = currencylist.get(getPosition()).getPrice_try();

                        break;
                    case "TWD":
                        currency_value = currencylist.get(getPosition()).getPrice_twd();

                        break;
                    case "ZAR":
                        currency_value = currencylist.get(getPosition()).getPrice_zar();

                        break;


                }





                clicklistener.itemclicked(v,getPosition(),currency_name,currency_symbol,rank,currency_value,btcprice,
                        Hvolumeusd,market_cap_usd,available_supply,total_supply,max_supply,percent_change_1h,percent_change_24h,
                        percent_change_7d,last_updated);
            }


        }
    }
    public interface Clicklistener
    {
        public void itemclicked(View view,int position,String currencyname,String symbol,String rank,String selectedcurrency,String btc,
                                String Hvolumeusd,String market_cap_usd,String available_supply, String total_supply,
                                String max_supply,String percent_change_1h,String percent_change_24h,
                                String percent_change_7d,String last_updated);
    }

}
