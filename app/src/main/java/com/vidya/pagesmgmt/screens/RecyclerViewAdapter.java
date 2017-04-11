package com.vidya.pagesmgmt.screens;

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
        return new CardView(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post p = postList.get(position);

        CardView cardView = (CardView) holder;
        cardView.setTvMessage(p.getMessage());
        cardView.setTvDate(p.getDate().toString());
        cardView.setTvViews("123");

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private class CardView extends RecyclerView.ViewHolder {

        TextView tvMessage, tvDate, tvViews;

        public CardView(View itemView) {
            super(itemView);
            android.support.v7.widget.CardView cardView = (android.support.v7.widget.CardView) itemView.findViewById(R.id.cv_post_card);
            this.tvMessage = (TextView) cardView.findViewById(R.id.tvMessage);
            this.tvDate = (TextView) cardView.findViewById(R.id.tvDate);
            this.tvViews = (TextView) cardView.findViewById(R.id.tvViews);
        }

        public void setTvMessage(String tvMessage) {
            this.tvMessage.setText(tvMessage);
        }

        public void setTvDate(String tvDate) {
            this.tvDate.setText(tvDate);

        }

        public void setTvViews(String tvViews) {
            this.tvViews.setText(tvViews);
        }
    }
}
