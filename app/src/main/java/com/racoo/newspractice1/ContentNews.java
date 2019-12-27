package com.racoo.newspractice1;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

public class ContentNews extends AppCompatActivity {

    private NewsData newsData;
    private TextView TextView_newscontent, TextView_newstitle;
    private Uri uri;
    private SimpleDraweeView ImageView_newstitle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_news);

        setCom();
        getNewsDetails();
        setNews();

    }


    public void setCom(){

        TextView_newscontent = findViewById(R.id.TextView_newscontent);
        TextView_newstitle = findViewById(R.id.TextView_newstitle);
        ImageView_newstitle = (SimpleDraweeView) findViewById(R.id.ImageView_newstitle);

    }

    public void getNewsDetails(){

        Intent intent = getIntent();

        if(intent != null){

            Bundle bld = intent.getExtras();
            Object obj = bld.get("newsData");

            if(obj != null && obj instanceof NewsData){
                this.newsData = (NewsData) obj;
            }

        }


    }

    public void setNews(){



        if (this.newsData != null){

            String title = this.newsData.getTitle();

            if(title != null) {

                TextView_newstitle.setText(title);
            }

            String content = this.newsData.getContent();

            if(content != null) {

                TextView_newscontent.setText(content);

            }


            String urlToImage = this.newsData.getUrlToImage();

            if(urlToImage != null){

                uri = Uri.parse(urlToImage);
                ImageView_newstitle.setImageURI(uri);



            }


        }




    }

}

