package com.vidya.pagesmgmt.screens;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vidya.pagesmgmt.R;
import com.vidya.pagesmgmt.controllers.InsightsController;
import com.vidya.pagesmgmt.model.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        if(p.getDate().before(new Date(new Date().getTime() - DateUtils.DAY_IN_MILLIS))){
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
            postView.setTvDate(sdf.format(p.getDate()));
        } else postView.setTvDate(DateUtils.getRelativeTimeSpanString(p.getDate().getTime()).toString());
        postView.setTvViews(p.getId());
        if(p.isPublished()) postView.setPublished(true);
        else {
            postView.setPublished(false);
            postView.bindListener(p.getId());
        }

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private class PostView extends RecyclerView.ViewHolder {

        TextView tvMessage, tvDate, tvViews;
        CardView postView;
        View saveIcon;

        public PostView(View itemView) {
            super(itemView);
            postView = (CardView) itemView.findViewById(R.id.cv_post_card);
            this.tvMessage = (TextView) postView.findViewById(R.id.tvMessage);
            this.saveIcon = postView.findViewById(R.id.icon_save);
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
            if(b) {
                postView.setCardBackgroundColor(Color.parseColor("#DEE9EE"));
                saveIcon.setVisibility(View.GONE);
            }
            else {
                postView.setCardBackgroundColor(Color.parseColor("#ECF0F2"));
                saveIcon.setVisibility(View.VISIBLE);
            }

        }

        public void bindListener(final String postId){
            saveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(App.getContext(), "PostID : " + postId, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setTvViews(String postId) {

            InsightsController.getInsights(postId, new InsightsController.OnInsightsFetchedListener() {
                @Override
                public void onSuccess(Integer postViewCount) {
                    tvViews.setText( postViewCount + " views");
                }

                @Override
                public void onFailure(String message) {
                    Log.i(RecyclerViewAdapter.this.getClass().getSimpleName(),
                            message);
                }
            });
        }
    }
}
