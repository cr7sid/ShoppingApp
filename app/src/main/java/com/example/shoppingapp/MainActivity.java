package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shoppingapp.Adapters.ShopAdapter;
import com.example.shoppingapp.Models.Shop;
import com.example.shoppingapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etSearch.setThreshold(1);

        binding.etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.etSearch.showDropDown();
                return false;
            }
        });

        final ArrayList<Shop> shops = new ArrayList<>();
        final ShopAdapter adapter = new ShopAdapter(shops, this);

        binding.rvShops.setAdapter(adapter);

        mReference = FirebaseDatabase.getInstance().getReference("Shops");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvShops.setLayoutManager(linearLayoutManager);

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchShop(v.getText().toString());
                    return true;

                }

                return false;

            }
        });

        mReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        shops.clear();

                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {

                            Shop model = snapshot1.getValue(Shop.class);
                            shops.add(model);

                        }

                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {



                    }
                });

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                populateSearch(snapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mReference.addListenerForSingleValueEvent(valueEventListener);

    }

    private void populateSearch(DataSnapshot snapshot) {

        ArrayList<String> category = new ArrayList<>();

        if(snapshot.exists()) {

            for(DataSnapshot snap : snapshot.getChildren()) {

                String cat = snap.child("category").getValue(String.class);
                category.add(cat);

            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,category);
            binding.etSearch.setAdapter(adapter);
            binding.etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String str = binding.etSearch.getText().toString();
                    searchShop(str);

                }
            });


        } else {

            Log.i("ERROR", "NOT FOUND!!!!");

        }

    }

    private void searchShop(String cat) {

        Query query = mReference.orderByChild("category").equalTo(cat);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    ArrayList<Shop> shopsList = new ArrayList<>();
                    shopsList.clear();

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String name = dataSnapshot.child("name").getValue(String.class);
                        String category = dataSnapshot.child("category").getValue(String.class);
                        String address = dataSnapshot.child("address").getValue(String.class);
                        String image = dataSnapshot.child("image").getValue(String.class);
                        String cashback = dataSnapshot.child("cashback").getValue(String.class);
                        Shop model = new Shop(name, category, cashback, address, image);
                        shopsList.add(model);
//
                        Intent intent = new Intent(MainActivity.this, SearchedShops.class);
                        Bundle args = new Bundle();
                        args.putSerializable("shops",(Serializable) shopsList);
                        intent.putExtra("bundle",args);
                        startActivity(intent);
//                        Log.i("DATA!!!!", name + category + address + image);
//
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}