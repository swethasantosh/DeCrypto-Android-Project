package com.currency.decrypto.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.currency.decrypto.Models.News;
import com.currency.decrypto.News_openActivity;
import com.currency.decrypto.R;
import com.currency.decrypto.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by swetha on 12/23/17.
 */

public class News_adapter extends RecyclerView.Adapter<News_adapter.ViewHolder_News>
{
    //create array list to store current  item
    private ArrayList<News> articlelist = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    private Clicklistener clicklistener;

    private Context context;

    public News_adapter(Context context)
    {
        this.context = context;

        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader= volleySingleton.getImageLoader();
    }
    //set arraylist
    public void setArticlelist(ArrayList<News> ariclelist)
    {
        this.articlelist = ariclelist;
        //notify data has been changed
        notifyItemRangeChanged(0,ariclelist.size());
    }

    @Override
    public ViewHolder_News onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.custom_newslist,parent,false);
        //pass view obj to inflate layout
        ViewHolder_News viewHolder_news = new ViewHolder_News(view);
        return viewHolder_news;
    }

    @Override
    public void onBindViewHolder(final News_adapter.ViewHolder_News holder, int position)
    {
        //gives the current item at position
        News current_news = articlelist.get(position);


        holder.news_title.setText(current_news.getTitle());
        holder.news_time.setText(current_news.getPublished_on());
        holder.news_websitename.setText(current_news.getSource());
        String imageurl = current_news.getImageurl();

        imageLoader.get(imageurl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.image.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
    //recycler item click
    public void setClicklistener(Clicklistener clicklistener)
    {
        this.clicklistener = clicklistener;
    }


    @Override
    public int getItemCount()
    {
        return articlelist.size();
    }


     class ViewHolder_News extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView news_title,news_time,news_websitename;
        private ImageView image;


        public ViewHolder_News(View itemView)
        {
            super(itemView);
            //recyclerview item click listener

            itemView.setOnClickListener(this);

            image = (ImageView)itemView.findViewById(R.id.newsimage);
            news_title = (TextView)itemView.findViewById(R.id.textView8);
            news_time = (TextView)itemView.findViewById(R.id.textView9);
            news_websitename= (TextView)itemView.findViewById(R.id.textView10);
        }

        @Override
        public void onClick(View v)
        {
            context.startActivity(new Intent(context,News_openActivity.class));
            if(clicklistener!=null)
            {
                //clicklistener.itemclicked(v,getPosition());
                String title = articlelist.get(getPosition()).getTitle();
                String body_news  = articlelist.get(getPosition()).getBody();
                String news_url = articlelist.get(getPosition()).getUrl();
                String image_url = articlelist.get(getPosition()).getImageurl();
                clicklistener.itemclicked(v,getPosition(),title,body_news,news_url,image_url);


            }

        }
    }
    public interface  Clicklistener
    {
        public void itemclicked(View view,int position,String title,String body,String url,String imageurl);
    }

}