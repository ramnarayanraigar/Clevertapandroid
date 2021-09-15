package com.cleverTapTest.clevertapandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.CleverTapInstanceConfig;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;

    private Button btnProductViewed, btnSend;
    private EditText editName, editEmail;

    CleverTapAPI clevertap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        cleverTapInitialization(); // For question 1 A, added clevertap sdk

        // for question 1 B
        btnProductViewed.setOnClickListener(v -> {
            productViewed();
        });

        // for question 1 C
        btnSend.setOnClickListener(v -> {
            validationCheck(clevertap);
        });

        // for question 1 D i have implemented push notification
    }


    private void productViewed() {
        HashMap<String, Object> prodViewedAction = new HashMap<>();
        prodViewedAction.put("Product Name", "CleverTap");
        prodViewedAction.put("Product ID", "1");
        prodViewedAction.put("Product Image", "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg");
        clevertap.pushEvent("Product Viewed", prodViewedAction);

        Toast.makeText(context, "Product Viewed called", Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        context = this;
        btnProductViewed = findViewById(R.id.btn_product_viewed);
        btnSend = findViewById(R.id.btn_send);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
    }

    private void cleverTapInitialization() {
        CleverTapAPI.getDefaultInstance(context);
        clevertap = CleverTapAPI.getDefaultInstance(getApplicationContext());

        CleverTapInstanceConfig clevertapAdditionalInstanceConfig = CleverTapInstanceConfig.createInstance(context, context.getString(R.string.clevertap_account_id), context.getString(R.string.clevertap_account_token));
        clevertapAdditionalInstanceConfig.setDebugLevel(CleverTapAPI.LogLevel.DEBUG); // default is CleverTapAPI.LogLevel.INFO
        clevertapAdditionalInstanceConfig.setAnalyticsOnly(true); // disables the user engagement features of the instance, default is false

        CleverTapAPI clevertapAdditionalInstance = CleverTapAPI.instanceWithConfig(context,clevertapAdditionalInstanceConfig);

    }

    // email and name validation check
    private void validationCheck(CleverTapAPI clevertap) {
        if (editName.getText().toString().isEmpty()) {
            Toast.makeText(context, "Enter name", Toast.LENGTH_LONG).show();
        } else if (!isEmailValid(editEmail.getText().toString())) {
            Toast.makeText(context, "Enter valid email", Toast.LENGTH_LONG).show();
        } else {
            HashMap<String, Object> profileUpdate = new HashMap<>();
            profileUpdate.put("Name", editName.getText().toString());    // name
            profileUpdate.put("Email", editEmail.getText().toString()); // Email address of the user

            clevertap.pushProfile(profileUpdate);
            Toast.makeText(context, "Email and name sent.", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}