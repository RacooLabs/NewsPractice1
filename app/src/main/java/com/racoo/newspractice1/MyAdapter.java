package com.racoo.newspractice1;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;
    private static View.OnClickListener onClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SimpleDraweeView ImageView_title;
        public TextView TextView_title;
        public TextView TextView_content;
        public View rootView;


        public MyViewHolder(View v) {

            super(v);

            ImageView_title = (SimpleDraweeView) v.findViewById(R.id.ImageView_Title);
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_content = v.findViewById(R.id.TextView_content);
            rootView = v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);





        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NewsData> myDataset, Context context, View.OnClickListener onClick) {
        mDataset = myDataset;
        onClickListener = onClick;
        Fresco.initialize(context);
    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);


        MyViewHolder vh = new MyViewHolder(v);



        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        NewsData newsData = mDataset.get(position);
        holder.TextView_title.setText(newsData.getTitle());

        String content = newsData.getContent();

        if(content == null || content.length()<=1){
            holder.TextView_content.setText("-");
        } else{
            holder.TextView_content.setText(newsData.getContent());
        }


        Uri uri = Uri.parse(newsData.getUrlToImage());
        holder.ImageView_title.setImageURI(uri);







    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : null ;
    }

    public NewsData getNews(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }



}