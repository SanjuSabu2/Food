package com.example.food.ViewPager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;
import com.example.food.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    List<String> images;
    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public int getCount() {

        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.e("Working?", "0");
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.item, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageViewMain);

//        imageView.setImageResource(images.get(position));
//        Glide.with(context).load(images.get(position)).into(imageView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }

}