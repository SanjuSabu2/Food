package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.food.ViewPager.API.RetrofitClient;
import com.example.food.ViewPager.Model.MainModel;
import com.example.food.ViewPager.Model.ResponseArray;
import com.example.food.ViewPager.ViewPagerAdapter;

import java.util.ArrayList;
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

//    ViewPager mViewPager;
//    int[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,};
//    ViewPagerAdapter mViewPagerAdapter;
//    Timer timer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//
//        mViewPager = (ViewPager) findViewById(R.id.viewPagerMain);
//
//
//        mViewPagerAdapter = new ViewPagerAdapter(HomeActivity.this, images);
//
//
//        mViewPager.setAdapter(mViewPagerAdapter);
//
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                mViewPager.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % images.length);
//                    }
//                });
//            }
//        };
//        timer = new Timer();
//        timer.schedule(timerTask, 3000, 3000);
//    }
//}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mViewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        RetrofitClient.getMyApi().getImages().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, retrofit2.Response<MainModel> response) {
                ResponseArrayData = new ArrayList<ResponseArray>();
                myImage = response.body();
                String status = response.body().getStatus().toString();
                if (status.equalsIgnoreCase("100")) {
                    ResponseArrayData.add(response.body().getResponseArray());
                    ArrayList<String> bannerdata;
                    bannerdata = ResponseArrayData.get(0).getBannerImages();
                    Log.e("Working", String.valueOf(bannerdata.size()));
                    mViewPagerAdapter = new ViewPagerAdapter(HomeActivity.this, bannerdata);
                    mViewPager.setAdapter(mViewPagerAdapter);

                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            mViewPager.post(new Runnable() {
                                @Override
                                public void run() {
                                    mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % bannerdata.size());
                                }
                            });
                        }
                    };
                    timer = new Timer();
                    timer.schedule(timerTask, 1000, 3000);
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

//public class MainActivity extends AppCompatActivity {
//
//    ViewPager mViewPager;
//
//
//    int[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,};
//
//
//    ViewPagerAdapter mViewPagerAdapter;
//
//    Timer timer;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//

//        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
//

//        mViewPagerAdapter = new ViewPagerAdapter(MainActivity.this, images);
//
//
//        mViewPager.setAdapter(mViewPagerAdapter);

//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                mViewPager.post(new Runnable(){
//
//                    @Override
//                    public void run() {
//                        mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1)%images.length);
//                    }
//                });
//            }
//        };
//        timer = new Timer();
//        timer.schedule(timerTask, 3000, 3000);
//    }
//}
