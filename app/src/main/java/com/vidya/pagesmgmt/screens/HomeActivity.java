package com.vidya.pagesmgmt.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vidya.pagesmgmt.R;
import com.vidya.pagesmgmt.controllers.ViewController;
import com.vidya.pagesmgmt.controllers.ViewController.OnPostsFetchListener;
import com.vidya.pagesmgmt.model.Post;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnPostsFetchListener {

    RecyclerViewAdapter recyclerViewAdapter;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    FloatingActionButton button;

    List<Post> allPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        button = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreatePostsActivity.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ViewController().getFeed(HomeActivity.this);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new ViewController().getFeed(this);
    }

    @Override
    public void onSuccess(List<Post> postList) {
        Log.i(this.getClass().getSimpleName(), postList.toString());
        recyclerViewAdapter = new RecyclerViewAdapter(postList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "Feed update failed", Toast.LENGTH_LONG).show();
    }
}
