package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food.Database.Cart;


public class AddtocartActivity extends AppCompatActivity {
    int f=0,p;
    Button addToCart, cancel;
    ImageButton backBtn;
    Context context;
    TextView decrementButton, incrementButton, counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        ImageView prImage = findViewById(R.id.img2primage);
        TextView prPrice = findViewById(R.id.txt2prprice);
        TextView prTitle = findViewById(R.id.txt2prtitle);
        TextView prDesc = findViewById(R.id.txt2prdesc);
        addToCart = findViewById(R.id.addtocartbtn);
        cancel=findViewById(R.id.cancelbtn);
        decrementButton=findViewById(R.id.decrementButton);
        incrementButton=findViewById(R.id.incrementButton);
        backBtn =findViewById(R.id.backbtn);
        counter=findViewById(R.id.counter);

        Intent intent=getIntent();
        final String imageurl=intent.getStringExtra("imageUrl");
        final String name=intent.getStringExtra("prName");
        final String price=intent.getStringExtra("prPrice");
        final String id=intent.getStringExtra("id");
        final String desc=intent.getStringExtra("desc");
        final int[] count = {MainActivity.mydb.cartDao().returncount(id)};

        Glide.with(this).load(imageurl).into(prImage);
        if(count[0]==0) {
            counter.setText(String.valueOf(1));
        }
        else
            counter.setText(String.valueOf(count[0]));
        prPrice.setText(price);
        prDesc.setText(desc);
        prTitle.setText(name);
        
        counter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String S=s.toString();
                prPrice.setText(String.valueOf(Integer.parseInt(S)*Integer.parseInt(price)));
            }
        });

        backBtn.setOnClickListener(v -> {
            finish();
        });

        decrementButton.setOnClickListener(v -> {
            if (count[0] >= 1) {
                count[0]--;
                counter.setText(String.valueOf(count[0]));

            }
        });

        incrementButton.setOnClickListener(v -> {
            count[0]++;
            counter.setText(String.valueOf(count[0]));
        });

        cancel.setOnClickListener(v -> {
            finish();
        });
        addToCart.setOnClickListener(v -> {
            Cart cart = new Cart();
            cart.setId(id);
            cart.setImageid(imageurl);
            cart.setName(name);
            cart.setPrice(String.valueOf(price));
            cart.setDescript(desc);

//            if(count[0] ==0) {
//                Toast.makeText(AddtocartActivity.this, "Add atleast 1 item!", Toast.LENGTH_SHORT).show();
//            }else
            if (MainActivity.mydb.cartDao().isAddToCart(id) != 1) {

                if(count[0]==0) {
                    counter.setText(String.valueOf(1));
                    cart.setItemcount(1);
                }
                else {
                    counter.setText(String.valueOf(count[0]));
                    cart.setItemcount(count[0]);
                }
                MainActivity.mydb.cartDao().addToCart(cart);

//                counter.setText(String.valueOf(count[0]));
                Toast.makeText(AddtocartActivity.this, "Item added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddtocartActivity.this, "Item already in Cart", Toast.LENGTH_SHORT).show();
                if (count[0]==0){
                    count[0]=1;
                    MainActivity.mydb.cartDao().updatecount(id, count[0]);
                }
                else
                    MainActivity.mydb.cartDao().updatecount(id, count[0]);
            }
        });
    }
}
