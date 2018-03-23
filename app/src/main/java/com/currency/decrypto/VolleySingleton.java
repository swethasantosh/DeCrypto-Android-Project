package com.currency.decrypto;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by swetha on 12/17/17.
 */

public class VolleySingleton
{
    //constructor
    private static  VolleySingleton sInstance = null;
    private ImageLoader imageLoader;
    private RequestQueue mRequestQueue;
    private VolleySingleton()
    {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache()
        {
            private LruCache<String,Bitmap> cache = new LruCache<>((int)Runtime.getRuntime().maxMemory()/1024/8);
            @Override
            public Bitmap getBitmap(String url)
            {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap)
            {

                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getsInstance()
    {
        if (sInstance==null)
        {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    //returns requetqueue
    public RequestQueue getRequestQueue()
    {
        return mRequestQueue;
    }
    public ImageLoader getImageLoader (){
        return imageLoader;
    }
}
