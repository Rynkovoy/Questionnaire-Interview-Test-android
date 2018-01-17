package com.qit.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.adapters.UserCredentialsAdapter;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;

    private static final String USERS_COUNT = "USERS_COUNT";
    private static final String ONE_USER = "ONE";
    private static final String ALL_USERS = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
    }


    public void clickButton(View view) {
        Intent intent = new Intent(this, UserCredentialsActivity.class);
        intent.putExtra(USERS_COUNT, ALL_USERS);
        startActivity(intent);
    }

    public void clickButton2(View view) {
        Intent intent = new Intent(this, UserCredentialsActivity.class);
        intent.putExtra(USERS_COUNT, ONE_USER);
        startActivity(intent);
    }
}