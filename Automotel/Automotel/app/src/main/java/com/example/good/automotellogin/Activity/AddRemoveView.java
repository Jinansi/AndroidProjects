package com.example.good.automotellogin.Activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.good.automotellogin.Adapter.Listeners.AddRemoveItemListener;
import com.example.good.automotellogin.R;

/**
 * Created by GOOD on 11/04/2016.
 */
public class AddRemoveView extends FrameLayout {
    Context context;
    AddRemoveItemListener addRemoveItemListener;
    int position;
    ImageView plus, minus;
    FrameLayout background1, background2;
    TextView items;
    int count = 0;
    int dp_25, dp_20, dp_35;
    public AddRemoveView(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public AddRemoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void setAddRemoveItemListener(int position, AddRemoveItemListener addRemoveItemListener){
        this.position = position;
        this.addRemoveItemListener = addRemoveItemListener;
    }

    private void init() {
//        this.setPadding(dp_20/10,dp_20/10,dp_20/10,dp_20/10);
        dp_20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
        dp_25 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        dp_35 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, context.getResources().getDisplayMetrics());

        FrameLayout.LayoutParams backgroundParams = new LayoutParams(dp_35,dp_25);
        background1 = new FrameLayout(context);
        background1.setBackgroundColor(Color.argb(0,128,204,255));
        background2 = new FrameLayout(context);
        background2.setBackgroundColor(Color.argb(0,128,204,255));
        this.addView(background1,backgroundParams);
        this.addView(background2,backgroundParams);
        ViewTreeObserver back=background1.getViewTreeObserver();
        back.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                background1.setX(getWidth() / 2 - background1.getWidth() / 2);
                background2.setX(getWidth() / 2 - background2.getWidth() / 2);
                background1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        plus = new ImageView(context);
        plus.setImageResource(R.drawable.plus);
        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    expand();
                    count += 1;
                    items.setText("" + count);
                } else {
                    count += 1;
                    items.setText("" + count);
                }
                addRemoveItemListener.onItemChanged(position,count);
            }
        });
        minus = new ImageView(context);
        minus.setImageResource(R.drawable.minus);
        minus.setVisibility(INVISIBLE);
        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 1) {
                    count = 0;
                    collapse();
                } else if (count > 1) {
                    count -= 1;
                    items.setText("" + count);
                }
                addRemoveItemListener.onItemChanged(position,count);
            }
        });
        items = new TextView(context);
        items.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        items.setTextColor(Color.BLACK);
        items.setVisibility(INVISIBLE);

        FrameLayout.LayoutParams imageLayoutParams = new LayoutParams(dp_25,dp_25);
//        imageLayoutParams.gravity = Gravity.CENTER;
        FrameLayout.LayoutParams textLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutParams.gravity = Gravity.CENTER;

        this.addView(items, textLayoutParams);
        this.addView(minus, imageLayoutParams);
        this.addView(plus, imageLayoutParams);
        plus.setX(this.getWidth() / 2);
        minus.setX(this.getWidth()/2);
        ViewTreeObserver vto=plus.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                plus.setX(getWidth()/2-plus.getWidth()/2);
                minus.setX(getWidth()/2-minus.getWidth()/2);
                plus.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    private void expand(){
        minus.setVisibility(VISIBLE);
        items.setVisibility(VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(plus, "translationX", 0);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(100);
        objectAnimator.start();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(minus, "translationX", this.getWidth()-minus.getWidth());
        objectAnimator1.setInterpolator(new DecelerateInterpolator());
        objectAnimator1.setDuration(100);
        objectAnimator1.start();

        ObjectAnimator backAnimator1 = ObjectAnimator.ofFloat(background1, "translationX", 0);
        backAnimator1.setInterpolator(new DecelerateInterpolator());
        backAnimator1.setDuration(100);
        backAnimator1.start();

        ObjectAnimator backAnimator2 = ObjectAnimator.ofFloat(background2, "translationX", this.getWidth()-background2.getWidth());
        backAnimator2.setInterpolator(new DecelerateInterpolator());
        backAnimator2.setDuration(100);
        backAnimator2.start();
    }

    private void collapse(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(plus, "translationX", this.getWidth()/2-plus.getWidth()/2);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(100);
        objectAnimator.start();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(minus, "translationX", this.getWidth()/2-minus.getWidth()/2);
        objectAnimator1.setInterpolator(new DecelerateInterpolator());
        objectAnimator1.setDuration(100);
        objectAnimator1.start();

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                items.setVisibility(INVISIBLE);
                minus.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        ObjectAnimator backAnimator1 = ObjectAnimator.ofFloat(background1, "translationX", this.getWidth()/2-background1.getWidth()/2);
        backAnimator1.setInterpolator(new DecelerateInterpolator());
        backAnimator1.setDuration(100);
        backAnimator1.start();

        ObjectAnimator backAnimator2 = ObjectAnimator.ofFloat(background2, "translationX", this.getWidth() / 2 - background2.getWidth() / 2);
        backAnimator2.setInterpolator(new DecelerateInterpolator());
        backAnimator2.setDuration(100);
        backAnimator2.start();

    }



}
