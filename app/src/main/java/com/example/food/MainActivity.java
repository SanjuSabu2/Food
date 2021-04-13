package com.example.food;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.API.MyRetrofit;
import com.example.food.Adapter.ProductAdapter;
import com.example.food.Database.MyDatabase;
import com.example.food.Model.ProductData;
import com.example.food.Model.ResponseArray;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ResponseArray> Response;
    ImageView cartbtn;
    int f=0;
    TextView cartcount;
    View view = this.getCurrentFocus();
    EditText search;
    ProductAdapter adapter;
    public static MyDatabase mydb;
    ProductData myProductData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.RR);
        cartbtn = findViewById(R.id.cart_btn);
        cartcount = findViewById(R.id.cartcount);
        search = findViewById(R.id.search_bar);

//        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getdata();

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyCartActivity.class));
            }
        });
        mydb = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "MyCart").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                Filter = new ArrayList<ResponseArray>();
////                for (int i = 0; i < Response.size(); i++) {
////                    if (Response.get(i).getTitle().contains(s)) {
////                        Filter.add(Response.get(i));
////                    }
////                }
////                adapter = new ProductAdapter(Filter, MainActivity.this);
////                recyclerView.setAdapter(adapter);
//
//
////                (MyRetrofit.getMyApi().getProductData("1","10")).enqueue(new Callback<ProductData>() {
////                    @Override
////                    public void onResponse(Call<ProductData> call, Response<ProductData> response) {
//
////                        Resp = new ArrayList<ResponseArray>();
////                        myProductData = response.body();
////                        if (myProductData.getStatus() == 100) {
////                            if(myProductData.getResponseArray().size() > 0) {
////                                Resp.addAll(response.body().getResponseArray());
////                                for(int i=0;i<Resp.size();i++) {
////                                    if (Resp.get(i).getTitle().contains(s)) {
////                                        Filter.add(Resp.get(i));
////                                    }
////                                }
////                                adapter = new ProductAdapter(Filter, MainActivity.this);
////                                recyclerView.setAdapter(adapter);
////
////                            }
////                            else
////                                Toast.makeText(MainActivity.this,"No Data found", Toast.LENGTH_SHORT).show();
////                        }
////                        else
////                            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
////                    }
////
////                    @Override
////                    public void onFailure(Call<ProductData> call, Throwable t) {
////                        Log.d("response", t.getStackTrace().toString());
////
////
////                    }
////                });
//
//
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void afterTextChanged(Editable s) {
//                f=1;
//                if(s.length()==3){
//                    closeKeyboard();
//                }
//                filter(s.toString());
//            }
//        });
    }
//    @Override
//    public void onBackPressed(){
//        if (f==1){
//            search.setText("");
//            f=0;
//        }
//        else
//            finish();
//    }


//    private void closeKeyboard()
//    {
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }

//    private void filter(String text) {
//        ArrayList<ResponseArray> filteredList = new ArrayList<ResponseArray>();
//        for (ResponseArray item : Response) {
//            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//        adapter = new ProductAdapter(filteredList, MainActivity.this);
//        recyclerView.setAdapter(adapter);
//    }

    private void updatacartcount() {
        if (cartcount == null)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mydb.cartDao().countCart() == 0)
                    cartcount.setVisibility(View.INVISIBLE);
                else {
                    cartcount.setVisibility(View.VISIBLE);
                    cartcount.setText(String.valueOf(mydb.cartDao().returncartcount()));
//                    cartcount.setText(String.valueOf(mydb.cartDao().countCart()));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatacartcount();
    }


    private void getdata() {

        (MyRetrofit.getMyApi().getProductData("0", "10")).enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                Response = new ArrayList<ResponseArray>();
                myProductData = response.body();
                if (myProductData.getStatus() == 100) {
                    if (myProductData.getResponseArray().size() > 0) {
                        Response.addAll(response.body().getResponseArray());
                        adapter = new ProductAdapter(Response, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    } else
                        Toast.makeText(MainActivity.this, "No Data found", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());
            }
        });
    }
}
