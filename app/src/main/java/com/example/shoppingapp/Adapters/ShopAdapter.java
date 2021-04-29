package com.example.shoppingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingapp.Models.Shop;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ShopDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    ArrayList<Shop> shopModels;
    Context context;

    public ShopAdapter(ArrayList<Shop> shopModels, Context context) {
        this.shopModels = shopModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_shop_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Shop model = shopModels.get(position);

        Picasso.get().load(model.getImage()).into(holder.ivShop);
        holder.tvName.setText(model.getName());
        holder.tvCategory.setText(model.getCategory());
        holder.tvCashback.setText("Cashback: " + model.getCashback());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShopDetailsActivity.class);
                intent.putExtra("shop", model);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shopModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivShop;
        private TextView tvName, tvDistance, tvCashback, tvCategory;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            ivShop = itemView.findViewById(R.id.img_shop);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            tvCashback = itemView.findViewById(R.id.tv_cashback);
            tvCategory = itemView.findViewById(R.id.tv_category);

        }
    }

}


