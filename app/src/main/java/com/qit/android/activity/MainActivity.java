package com.qit.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.adapters.UserCredentialsAdapter;
import com.qit.android.rest.dto.UserCredentialDTO;
import com.qit.android.rest.utils.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.user_credentials_item, initData());
        UserCredentialsAdapter credentialsAdapter = new UserCredentialsAdapter(this, initRetroData());
        listView.setAdapter(credentialsAdapter);
    }

    private List<UserCredentialDTO> initData() {
        List<UserCredentialDTO> list = new ArrayList<>();
        list.add(new UserCredentialDTO("rynkovoy", "123", true));
        list.add(new UserCredentialDTO("rynkovoy1", "123", true));
        list.add(new UserCredentialDTO("rynkovoy2", "123", false));
        list.add(new UserCredentialDTO("rynkovoy3", "123", false));
        list.add(new UserCredentialDTO("rynkovoy3", "123", true));
        return list;
    }

    private List<UserCredentialDTO> initRetroData() {
        final List<UserCredentialDTO> list = new ArrayList<>();

        RetroClient.getUserCredentialsApi().findAllUsers().enqueue(new Callback<List<UserCredentialDTO>>() {

            @Override
            public void onResponse(Call<List<UserCredentialDTO>> call, Response<List<UserCredentialDTO>> response) {
                list.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<UserCredentialDTO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Cannot load data", Toast.LENGTH_SHORT).show();
            }
        });

        return list;
    }

}
