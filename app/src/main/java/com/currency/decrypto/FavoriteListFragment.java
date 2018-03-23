package com.currency.decrypto;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.currency.decrypto.Adapters.Currency_Adapter;
import com.currency.decrypto.Adapters.Favoriteslist_Adapter;
import com.currency.decrypto.Models.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santosh on 2/13/18.
 */

public class FavoriteListFragment extends Fragment implements  Favoriteslist_Adapter.Clicklistener
{
    public static final String ARG_ITEM_ID = "favorite_list";

    RecyclerView favoriteList;
    Sharedpreferences sharedPreference;
    Context context;
    ArrayList<Currency> favorites_list = new ArrayList<>();

    Activity activity;
    Favoriteslist_Adapter coinListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto, container,
                false);
        // Get favorite items from SharedPreferences.
        sharedPreference = new Sharedpreferences();
        ArrayList<Currency> favorites = sharedPreference.getFavorites(activity);

        if (favorites == null) {
            showAlert("No favorite items","add one");
        } else {

            if (favorites.size() == 0)
            {
                showAlert("No favorite items","add one");
            }

            favoriteList = (RecyclerView) view.findViewById(R.id.market_list);

            favoriteList.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (favorites != null) {
                coinListAdapter = new Favoriteslist_Adapter(context, favorites);
                coinListAdapter.setClicklistener(this);
                favoriteList.setAdapter(coinListAdapter);
            }

               /* favoriteList.setOnItemClickListener(new OnItemClickListener()
                {

                    public void onItemClick(AdapterView<?> parent, View arg1,
                                            int position, long arg3)

                                            {

                    }
                });*/

               /* favoriteList
                        .setOnItemLongClickListener(new OnItemLongClickListener() {

                            @Override
                            public boolean onItemLongClick(
                                    AdapterView<?> parent, View view,
                                    int position, long id) {

                                ImageView button = (ImageView) view
                                        .findViewById(R.id.imgbtn_favorite);

                                String tag = button.getTag().toString();
                                if (tag.equalsIgnoreCase("grey")) {
                                    sharedPreference.addFavorite(activity,
                                            favorites.get(position));
                                    Toast.makeText(
                                            activity,
                                            activity.getResources().getString(
                                                    R.string.add_favr),
                                            Toast.LENGTH_SHORT).show();

                                    button.setTag("red");
                                    button.setImageResource(R.drawable.heart_red);
                                } else {
                                    sharedPreference.removeFavorite(activity,
                                            favorites.get(position));
                                    button.setTag("grey");
                                    button.setImageResource(R.drawable.heart_grey);
                                    productListAdapter.remove(favorites
                                            .get(position));
                                    Toast.makeText(
                                            activity,
                                            activity.getResources().getString(
                                                    R.string.remove_favr),
                                            Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });*/
           // }
        }
        return view;
    }

    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }

   /* @Override
    public void onResume() {
        getActivity().setTitle("favorites");
        getActivity().getActionBar().setTitle("favorites");
        super.onResume();
    }*/

    @Override
    public void itemclicked(View view, int position, String currencyname, String symbol, String rank, String selectedcurrency, String btc, String Hvolumeusd, String market_cap_usd, String available_supply, String total_supply, String max_supply, String percent_change_1h, String percent_change_24h, String percent_change_7d, String last_updated)
    {

    }
}



