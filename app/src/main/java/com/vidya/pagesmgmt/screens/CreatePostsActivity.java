package com.vidya.pagesmgmt.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vidya.pagesmgmt.R;
import com.vidya.pagesmgmt.controllers.CreateController;

public class CreatePostsActivity extends AppCompatActivity implements CreateController.OnCreateListener{

    EditText message;
    Button post, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_posts);
        message = (EditText) findViewById(R.id.post_content);
        post = (Button) findViewById(R.id.create_posts_button);
        save = (Button) findViewById(R.id.save_posts_button);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getText().equals("")){
                    new CreateController().createPosts(CreatePostsActivity.this, message.getText().toString(),"publish");
                }else{
                    Toast.makeText(CreatePostsActivity.this,"Please enter message",Toast.LENGTH_LONG).show();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getText().equals("")){
                    new CreateController().createPosts(CreatePostsActivity.this,message.getText().toString(),"save");
                }else{
                    Toast.makeText(CreatePostsActivity.this,"Please enter message",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateSucces(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}

