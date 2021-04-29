package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shoppingapp.Models.Shop;
import com.example.shoppingapp.databinding.ActivityMainBinding;
import com.example.shoppingapp.databinding.ActivityShopDetailsBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ShopDetailsActivity extends AppCompatActivity {

    private ActivityShopDetailsBinding binding;
    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        shop = (Shop) intent.getSerializableExtra("shop");
        updateUI();

        binding.tvAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Uri gmmIntentUri =
                        Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(shop.getAddress()));

                Toast.makeText(ShopDetailsActivity.this, "Opening Google Maps...", Toast.LENGTH_SHORT).show();

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    Toast.makeText(ShopDetailsActivity.this, "Opening Google Maps...", Toast.LENGTH_SHORT).show();
                    startActivity(mapIntent);
                }

            }
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Download this amazing application now: " +
                        "https://play.google.com/store/apps/details?id=com.letsdevelopit.lfydcustomer&hl=en_US&gl=US";
                String shareSub = "Shopping App";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(shareIntent, "Share Via"));

            }
        });

    }

    private void updateUI() {

        binding.tvAdd.setText(shop.getAddress());
        binding.tvName.setText(shop.getName());
        binding.tvCat.setText(shop.getCategory());
        binding.tvCBack.setText("CashBack: " + shop.getCashback());
        Picasso.get().load(shop.getImage()).into(binding.shopImg);

    }
}