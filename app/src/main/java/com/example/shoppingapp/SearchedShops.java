package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.shoppingapp.Adapters.ShopAdapter;
import com.example.shoppingapp.Models.Shop;
import com.example.shoppingapp.databinding.ActivityMainBinding;
import com.example.shoppingapp.databinding.ActivitySearchedShopsBinding;

import java.util.ArrayList;

public class SearchedShops extends AppCompatActivity {

    ActivitySearchedShopsBinding binding;
    private ArrayList<Shop> shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_shops);
        binding = ActivitySearchedShopsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        shops = (ArrayList<Shop>) args.getSerializable("shops");

        final ShopAdapter adapter = new ShopAdapter(shops, this);
        binding.rvShops.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvShops.setLayoutManager(linearLayoutManager);

    }
}