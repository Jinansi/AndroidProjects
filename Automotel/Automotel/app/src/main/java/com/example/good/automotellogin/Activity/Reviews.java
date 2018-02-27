package com.example.good.automotellogin.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.good.automotellogin.Adapter.ReviewAdapter;
import com.example.good.automotellogin.Bean.ReviewData;
import com.example.good.automotellogin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOOD on 07/03/2016.
 */
public class Reviews  extends AppCompatActivity{
        RecyclerView rv ;
    Button Upload_review;

    List<ReviewData> review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_cardview);

      //  final Animation animTranslate = AnimationUtils.loadAnimation(this,R.anim.anim_button_translate);
        rv= (RecyclerView) findViewById(R.id.my_recycler_view);
        Upload_review= (Button) findViewById(R.id.give_review);
        Upload_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  v.startAnimation(animTranslate);
                Intent i = new Intent(Reviews.this,PostReview.class);
                startActivity(i);
            }
        });
        review = new ArrayList<>();
        review.add(new ReviewData("Jinansi",R.drawable.restaurant1,"Awesome place.."));
        review.add(new ReviewData("Khyati",R.drawable.restaurant1,"Must Go...."));

    final  LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        ReviewAdapter reviewAdapter =new ReviewAdapter(review);
            rv.setAdapter(reviewAdapter);



    }
}
