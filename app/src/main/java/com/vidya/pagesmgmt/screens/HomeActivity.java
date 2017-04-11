package com.vidya.pagesmgmt.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.vidya.pagesmgmt.R;
import com.vidya.pagesmgmt.controllers.Controller;
import com.vidya.pagesmgmt.controllers.Controller.OnPostsFetchListener;
import com.vidya.pagesmgmt.model.Post;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnPostsFetchListener {

    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Controller().getFeed(this);
    }

    @Override
    public void onSuccess(List<Post> postList) {
        Toast.makeText(this, "Feed updated", Toast.LENGTH_LONG).show();
        Log.i(this.getClass().getSimpleName(), postList.toString());
        recyclerViewAdapter = new RecyclerViewAdapter(postList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "Feed update failed", Toast.LENGTH_LONG).show();
    }
}
