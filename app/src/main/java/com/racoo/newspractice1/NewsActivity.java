package com.racoo.newspractice1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestQueue queue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        queue = Volley.newRequestQueue(this);
        getNews();

    }



    public void getNews(){

        String url ="https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=9b7d5b92b405427fb30c350d1ef5a02f";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");
                            List<NewsData> news = new ArrayList<>();


                            for (int i = 0, j = arrayArticles.length(); i<j ; i++){

                                JSONObject obj = arrayArticles.getJSONObject(i);

                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("content"));
                                news.add(newsData);

                            }


                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(v.getTag() != null){
                                        int position = (int) v.getTag();
                                        Intent intent = new Intent(NewsActivity.this, ContentNews.class);

//                                        intent.putExtra("title",((MyAdapter)mAdapter).getNews(position).getTitle());
//                                        intent.putExtra("content",((MyAdapter)mAdapter).getNews(position).getContent());
//                                        intent.putExtra("image",((MyAdapter)mAdapter).getNews(position).getUrlToImage());

                                        intent.putExtra("newsData",((MyAdapter)mAdapter).getNews(position));

                                        //부모 타입으로 자식 객체를 참조했을때 는 부모의 메소드만 사용 가능,
                                        //부모 타입을 자식 객체로 참조했을 때 자식의 메소드를 쓰고 싶다면 다시됨 자식 타입으로 클래스 형변환 해줘야
                                        //https://programmers.co.kr/learn/courses/5/lessons/193 중요한 팁.


                                       startActivity(intent);

                                    }

                                }
                            });
                            recyclerView.setAdapter(mAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }



}
