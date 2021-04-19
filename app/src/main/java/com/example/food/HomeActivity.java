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
    Button appButton, pdfButton1, pdfButton2;
    MainModel myImage;
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    ArrayList<ResponseArray> ResponseArrayData;
    Timer timer;


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
        pdfButton1 = findViewById(R.id.pdfButton1);
        pdfButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PdfActivity.class);
                String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
                String pdfName = "PDF 1";
                intent.putExtra("url", url);
                intent.putExtra("pdfName", pdfName);
                startActivity(intent);
            }
        });
        pdfButton2 = findViewById(R.id.pdfButton2);
        pdfButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PdfActivity.class);
                String url = "https://www.antennahouse.com/hubfs/xsl-fo-sample/pdf/basic-link-1.pdf?hsLang=en";
                String pdfName = "PDF 2";
                intent.putExtra("url", url);
                intent.putExtra("pdfName", pdfName);
                startActivity(intent);
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

