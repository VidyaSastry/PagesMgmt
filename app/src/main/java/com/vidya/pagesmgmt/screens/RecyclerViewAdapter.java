package com.vidya.pagesmgmt.screens;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vidya.pagesmgmt.R;
import com.vidya.pagesmgmt.model.Post;

import java.util.List;

/**
 * Created by Vidya on 4/10/17.
 *
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;

    public RecyclerViewAdapter(List<Post> postList) {
        this.postList = postList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        return new PostView(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post p = postList.get(position);

        PostView postView = (PostView) holder;
        postView.setTvMessage(p.getMessage());
        postView.setTvDate(p.getDate().toString());
        postView.setTvViews(p.getId());
        if(p.isPublished()) postView.setPublished(true);
        else postView.setPublished(false);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private class PostView extends RecyclerView.ViewHolder {

        TextView tvMessage, tvDate, tvViews;
        CardView postView;

        public PostView(View itemView) {
            super(itemView);
            postView = (CardView) itemView.findViewById(R.id.cv_post_card);
            this.tvMessage = (TextView) postView.findViewById(R.id.tvMessage);
            this.tvDate = (TextView) postView.findViewById(R.id.tvDate);
            this.tvViews = (TextView) postView.findViewById(R.id.tvViews);
        }

        public void setTvMessage(String tvMessage) {
            this.tvMessage.setText(tvMessage);
        }

        public void setTvDate(String tvDate) {
            this.tvDate.setText(tvDate);
        }

        public void setPublished(boolean b){
            if(b) postView.setCardBackgroundColor(Color.parseColor("#DEE9EE"));
            else postView.setCardBackgroundColor(Color.parseColor("#ECF0F2"));

        }

        public void setTvViews(String tvViews) {
            this.tvViews.setText("views");
        }
    }
}
