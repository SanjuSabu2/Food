package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food.Database.Cart;
import com.example.food.Database.CartDao;



public class AddtocartActivity extends AppCompatActivity {
    int f=0,p;
    Button addtocart,cancel;
    ImageButton backbtn;
    Context context;
    TextView decrementButton,incrementButton,counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        ImageView primage = findViewById(R.id.img2primage);
        TextView prprice = findViewById(R.id.txt2prprice);
        TextView prtitle = findViewById(R.id.txt2prtitle);
        TextView prdesc = findViewById(R.id.txt2prdesc);
        addtocart=(Button)findViewById(R.id.addtocartbtn);
        cancel=findViewById(R.id.cancelbtn);
        decrementButton=findViewById(R.id.decrementButton);
        incrementButton=findViewById(R.id.incrementButton);
        backbtn=findViewById(R.id.backbtn);
        counter=findViewById(R.id.counter);

        Intent intent=getIntent();
        final String imageurl=intent.getStringExtra("imageurl");
        final String name=intent.getStringExtra("prname");
        final String price=intent.getStringExtra("prprice");
        final String id=intent.getStringExtra("id");
        final String desc=intent.getStringExtra("desc");
        final int[] count = {MainActivity.mydb.cartDao().returncount(id)};

        Glide.with(this).load(imageurl).into(primage);
        if(count[0]==0) {
            counter.setText(String.valueOf(1));
        }
        else
            counter.setText(String.valueOf(count[0]));
        prprice.setText(price);
        prdesc.setText(desc);
        prtitle.setText(name);
        
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
                prprice.setText(String.valueOf(Integer.parseInt(S)*Integer.parseInt(price)));
            }
        });

        backbtn.setOnClickListener(v -> {
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
        addtocart.setOnClickListener(v -> {
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
                MainActivity.mydb.cartDao().updatecount(id, count[0]);
            }
        });
    }
}
