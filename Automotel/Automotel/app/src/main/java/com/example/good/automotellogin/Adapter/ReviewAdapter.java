package com.example.good.automotellogin.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.good.automotellogin.Bean.ReviewData;
import com.example.good.automotellogin.R;

import java.util.List;

/**
 * Created by GOOD on 05/04/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>{
    List<ReviewData> reviewData;
    public ReviewAdapter(List<ReviewData> reviewData){
        this.reviewData = reviewData;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews,parent,false);
        ReviewHolder holder = new ReviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.user_name.setText(reviewData.get(position).name);
        holder.upload_pic.setImageResource(reviewData.get(position).photo);
        holder.user_review.setText(reviewData.get(position).review);
    }

    @Override
    public int getItemCount() {
        return reviewData.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView user_name;
        TextView user_review;
        ImageView upload_pic;
        ReviewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.cardview);
            cardView.setCardElevation(2.2f);
            user_name = (TextView) itemView.findViewById(R.id.name);
            user_review = (TextView) itemView.findViewById(R.id.review);
            upload_pic = (ImageView) itemView.findViewById(R.id.upload_pic);
        }
    }
}
