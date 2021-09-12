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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        // For question 1 A
        CleverTapAPI.getDefaultInstance(context);
        CleverTapAPI clevertap = CleverTapAPI.getDefaultInstance(getApplicationContext());


        CleverTapInstanceConfig clevertapAdditionalInstanceConfig = CleverTapInstanceConfig.createInstance(context, context.getString(R.string.clevertap_account_id), context.getString(R.string.clevertap_account_token));
        clevertapAdditionalInstanceConfig.setDebugLevel(CleverTapAPI.LogLevel.DEBUG); // default is CleverTapAPI.LogLevel.INFO
        clevertapAdditionalInstanceConfig.setAnalyticsOnly(true); // disables the user engagement features of the instance, default is false

        // for question 1 B
        btnProductViewed.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductViewedActivity.class);
            startActivity(intent);
        });

        // for question 1 C
        btnSend.setOnClickListener(v -> {
            validationCheck(clevertap);
        });

        // for question 1 D i have implemented push notification
    }

    private void initViews() {
        context = this;
        btnProductViewed = findViewById(R.id.btn_product_viewed);
        btnSend = findViewById(R.id.btn_send);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
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
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}