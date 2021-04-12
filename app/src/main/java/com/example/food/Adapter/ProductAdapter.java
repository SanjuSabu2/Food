package com.example.food.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.AddtocartActivity;
import com.example.food.Model.ProductData;
import com.example.food.Model.ResponseArray;
import com.example.food.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final ArrayList<ResponseArray> myProductData;
    private final Context context;

    public ProductAdapter(ArrayList<ResponseArray> myProductData, Context context) {
        this.myProductData = myProductData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(myProductData.get(position).getImage()).into(holder.image);
        holder.price.setText("₹ "+ myProductData.get(position).getPrice());
        holder.title.setText(myProductData.get(position).getTitle());
        holder.desc.setText(myProductData.get(position).getDescription());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddtocartActivity.class);
            String imageUrl = myProductData.get(position).getImage();
            String prPrice = String.valueOf(myProductData.get(position).getPrice());
            intent.putExtra("imageUrl",imageUrl);
            intent.putExtra("prName",myProductData.get(position).getTitle());
            intent.putExtra("prPrice",prPrice);
            intent.putExtra("id",myProductData.get(position).getId());
            intent.putExtra("desc",myProductData.get(position).getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myProductData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView price,title,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgprimage);
            price = itemView.findViewById(R.id.txtprprice);
            title = itemView.findViewById(R.id.txtprtitle);
            desc = itemView.findViewById(R.id.txtprdesc);

        }
    }
}
