package com.cookandroid.location_show;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class LoadImage {
    public LoadImage(TextView tv, String icon){
    }

    public TextView load(TextView tv, String iconUrl){
        iconUrl = "http://openweathermap.org/img/w/"+iconUrl+".png";
        Glide.with(tv.getContext()).load(iconUrl).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                tv.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        return tv;
    }
}
