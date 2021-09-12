package com.cleverTapTest.clevertapandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;

import com.bumptech.glide.Glide;

public class ProductViewedActivity extends AppCompatActivity {
    private AppCompatImageView imageProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_viewed);

        imageProduct = findViewById(R.id.image_product);

        Glide.with(this).load("https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg").into(imageProduct);
    }
}