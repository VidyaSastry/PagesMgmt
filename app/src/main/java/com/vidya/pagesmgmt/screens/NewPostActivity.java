package com.vidya.pagesmgmt.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vidya.pagesmgmt.R;
import com.vidya.pagesmgmt.controllers.PagePostController;

import static com.vidya.pagesmgmt.controllers.PagePostController.PUBLISH;
import static com.vidya.pagesmgmt.controllers.PagePostController.SAVE;

public class NewPostActivity extends AppCompatActivity implements PagePostController.OnCreateListener{

    EditText message;
    Button post, save;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_posts);
        message = (EditText) findViewById(R.id.post_content);
        post = (Button) findViewById(R.id.create_posts_button);
        save = (Button) findViewById(R.id.save_posts_button);

        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Processing...");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getText().toString().equals("")){
                    PagePostController.createPosts(NewPostActivity.this, message.getText().toString(), PUBLISH);
                    progressDialog.show();
                }else{
                    Toast.makeText(NewPostActivity.this,"Please enter message",Toast.LENGTH_LONG).show();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!message.getText().toString().equals("")){
                    PagePostController.createPosts(NewPostActivity.this,message.getText().toString(), SAVE);
                    progressDialog.show();
                }else{
                    Toast.makeText(NewPostActivity.this,"Please enter message",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateSuccess(String result) {
        progressDialog.hide();
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}

