package com.example.photobay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements PhotoAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private List<String> mList;
    private PhotoAdapter adapter;
    private RequestQueue mRequestQueue;
    String keyword;
    public static final String Url_Key="url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mRecyclerView = (RecyclerView) findViewById(R.id.RV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getString(MainActivity.key);

        //Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show();

        mList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        ParseJson();

    }

    private void ParseJson() {
        String URl = "https://pixabay.com/api/?key=17134703-b840b92e9f2348116834937d2&q="+keyword+"&image_type=photo";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String url  =jsonObject.getString("webformatURL");
                        mList.add(url);
                    }

                    adapter = new PhotoAdapter(SearchResultActivity.this,mList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(SearchResultActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void OnItemClick(int position) {

        Intent intent = new Intent(SearchResultActivity.this,DetailsActivity.class);
        String url = mList.get(position);
        intent.putExtra(Url_Key,url);
        startActivity(intent);
    }
}