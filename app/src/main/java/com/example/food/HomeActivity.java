package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.food.ViewPager.API.RetrofitClient;
import com.example.food.ViewPager.Model.MainModel;
import com.example.food.ViewPager.Model.ResponseArray;
import com.example.food.ViewPager.ViewPagerAdapter;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity {
    Button appButton;
    MainModel myImage;
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    ArrayList<ResponseArray> ResponseArrayData;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        RetrofitClient.getMyApi().getImages().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, retrofit2.Response<MainModel> response) {
                ResponseArrayData = new ArrayList<ResponseArray>();
                myImage = response.body();
                String status = response.body().getStatus().toString();
                if (status.equalsIgnoreCase("100")) {
                    ResponseArrayData.add(response.body().getResponseArray());
                    List<String> bannerdata;
                    bannerdata = ResponseArrayData.get(0).getBannerImages();
                    mViewPagerAdapter = new ViewPagerAdapter(HomeActivity.this, bannerdata);
                    mViewPager.setAdapter(mViewPagerAdapter);

                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            mViewPager.post(new Runnable(){
                                @Override
                                public void run() {
                                    mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1)%bannerdata.size());
                                }
                            });
                        }
                    };
                    timer = new Timer();
                    timer.schedule(timerTask, 3000, 3000);
                }

            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }

        });

        appButton = findViewById(R.id.appButton);
        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
