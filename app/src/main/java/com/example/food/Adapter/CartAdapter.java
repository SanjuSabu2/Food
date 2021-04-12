package com.example.food.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.Database.Cart;
import com.example.food.MainActivity;
import com.example.food.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final List<Cart>carts;
    private final Context context;
    TextView cartEmpty;

    public CartAdapter(List<Cart> carts, Context context, TextView cartempty) {
        this.carts = carts;
        this.context = context;
        this.cartEmpty = cartempty;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Cart cart=carts.get(position);

        Glide.with(context).load(carts.get(position).getImageid()).into(holder.prImage);
        holder.prprice.setText("₹ "+carts.get(position).getPrice());
        holder.prdesc.setText(carts.get(position).getDescript());
        holder.prtitle.setText(carts.get(position).getName());
        holder.quantity.setText("x"+ MainActivity.mydb.cartDao().returncount(carts.get(position).getId()));
        holder.deletbtn.setOnClickListener(v -> {
            carts.remove(position);
            notifyDataSetChanged();
            MainActivity.mydb.cartDao().deleteItem(cart.getId());
            int cartCount= MainActivity.mydb.cartDao().countCart();
            Intent k=new Intent("mymsg");
            k.putExtra("cartCount",cartCount);
            LocalBroadcastManager.getInstance(context).sendBroadcast(k);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,getItemCount());
        });
        if (carts.size()!=0){
            Log.e("CART","notempty");
            cartEmpty.setVisibility(View.GONE);
        }
        else {
            Log.e("CART","empty");
            cartEmpty.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView prImage;
        private final TextView prprice;
        private final TextView prtitle;
        private final TextView prdesc;
        private final TextView decrementButton;
        private final TextView quantity;
        private final TextView incrementButton;
        private final ImageButton deletbtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            decrementButton = itemView.findViewById(R.id.decrementButton);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            quantity = itemView.findViewById(R.id.quantity);
            prtitle = itemView.findViewById(R.id.txtprtitle);
            prdesc = itemView.findViewById(R.id.txtprdesc);
            prImage=itemView.findViewById(R.id.imgprimage);
            deletbtn=itemView.findViewById(R.id.deletebtn);
            prprice=itemView.findViewById(R.id.txtprprice);
        }
    }
}
