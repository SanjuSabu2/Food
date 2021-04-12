package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.food.Adapter.CartAdapter;
import com.example.food.Database.Cart;

import java.util.List;

public class MyCartActivity extends AppCompatActivity {
    RecyclerView rv;
    CartAdapter cartAdapter;
    List<Cart> carts;
    TextView tvCount, totalPrice, cartEmpty;
    CardView cardView;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        rv=findViewById(R.id.resp);
//        tvCount =findViewById(R.id.countcart);
        totalPrice =findViewById(R.id.totalprice);
        cartEmpty =findViewById(R.id.cartempty);
        cardView=findViewById(R.id.cardView);
        backBtn =findViewById(R.id.backbtn);
        backBtn.setOnClickListener(v -> finish());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getCartData();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessage,new IntentFilter("mymsg"));
        int sum = MainActivity.mydb.cartDao().carttotal();
        totalPrice.setText(String.valueOf(sum));
        LocalBroadcastManager.getInstance(this).registerReceiver(sMessage,new IntentFilter("mymsg"));

    }

    private void updatacartcount() {
        int count=cartAdapter.getItemCount();
        if (count==0){
            tvCount.setVisibility(View.INVISIBLE);
        }else {
//            tvCount.setText(String.valueOf(MainActivity.mydb.cartDao().returncartcount()));
//            cartcount.setText(String.valueOf(mydb.cartDao().returncartcount()));
        }
    }

    private void getCartData() {
        carts=MainActivity.mydb.cartDao().getData();
        cartAdapter=new CartAdapter(carts,this, cartEmpty);
        rv.setAdapter(cartAdapter);
        updatacartcount();

    }
    public BroadcastReceiver mMessage=new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            if (carts.size() == 0){
//                tvCount.setVisibility(View.INVISIBLE);
                cartEmpty.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.INVISIBLE);
            }else {
//                tvCount.setText(String.valueOf(carts.size()));
            }
        }
    };
    public BroadcastReceiver sMessage=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int sum = MainActivity.mydb.cartDao().carttotal();
            totalPrice.setText(String.valueOf(sum));
        }
    };
}
