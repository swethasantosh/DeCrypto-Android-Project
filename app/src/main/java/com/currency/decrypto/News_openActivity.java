package com.currency.decrypto;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.currency.decrypto.Adapters.News_adapter;
import com.currency.decrypto.Fragments.News_fragment;


/**
 * Created by swetha on 12/24/17.
 */

public class News_openActivity extends AppCompatActivity
{
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    WebView webView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_openwebview);

        final ImageView image;
        Button button;
        TextView titletext,body;


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News");


        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();


        image = (ImageView)findViewById(R.id.image_detail);
        titletext = (TextView)findViewById(R.id.title_open);
        body = (TextView)findViewById(R.id.news_body);
        button = (Button)findViewById(R.id.button_website);




        Intent intent = getIntent();
        String message_title = intent.getStringExtra("message");
        String message_body = intent.getStringExtra("body");
        final String message_url = intent.getStringExtra("url");
        final String message_imageurl = intent.getStringExtra("imageurl");


        titletext.setText(message_title);
        body.setText(message_body);



        if(message_imageurl!=null) {

            imageLoader.get(message_imageurl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    image.setImageBitmap(response.getBitmap());

                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                    Log.d("d", error.toString());


                }
            });
        }
        else {finish();}

        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent intent1 = new Intent("android.intent.action.VIEW");
                Uri uri = Uri.parse(message_url);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                //intent1.setData(uri);

                //News_openActivity.this.startActivity(intent1);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
