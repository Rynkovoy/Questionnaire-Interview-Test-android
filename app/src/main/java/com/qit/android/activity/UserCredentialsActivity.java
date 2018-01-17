package com.qit.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.adapters.UserCredentialsAdapter;
import com.qit.android.rest.dto.UserCredentialDTO;
import com.qit.android.rest.utils.QitApi;
import com.qit.android.utils.DimensionUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCredentialsActivity extends AppCompatActivity {

    private static final String USERS_COUNT = "USERS_COUNT";
    private static final String ONE_USER = "ONE";
    private static final String ALL_USERS = "ALL";

    private ListView listView;
    private UserCredentialsAdapter userCredentialsAdapter;
    private LinearLayout credentialsLayout;
    private EditText etUsername;
    private Button btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creadentials);

        listView = findViewById(R.id.listView);
        credentialsLayout = findViewById(R.id.credentialsLayout);
        userCredentialsAdapter = new UserCredentialsAdapter(this, initData());
    }

    private List<UserCredentialDTO> initData() {
        final List<UserCredentialDTO> credentialDTOs = new ArrayList<>();
        String usersCountTag = getIntent().getStringExtra(USERS_COUNT);

        switch (usersCountTag) {
            case ONE_USER:
                initOneUserCredentials(credentialDTOs);
                break;
            case ALL_USERS:
                initAllUsersCredentials(credentialDTOs);
                break;
        }
        return credentialDTOs;
    }


    private void initAllUsersCredentials(final List<UserCredentialDTO> credentialDTOs) {
        QitApi.getUserCredentialsApi().findAllUsers().enqueue(new Callback<List<UserCredentialDTO>>() {
            @Override
            public void onResponse(Call<List<UserCredentialDTO>> call, Response<List<UserCredentialDTO>> response) {
                credentialDTOs.addAll(response.body());
                listView.setAdapter(userCredentialsAdapter);
            }

            @Override
            public void onFailure(Call<List<UserCredentialDTO>> call, Throwable t) {
                Toast.makeText(UserCredentialsActivity.this, "Cannot load data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initOneUserCredentials(final List<UserCredentialDTO> credentialDTOs) {
        etUsername = new EditText(this);
        etUsername.setHint("Username");
        etUsername.setHeight(DimensionUtils.dp2px(this, 60));

        btnFind = new Button(this);
        btnFind.setText("Find");
        btnFind.setHeight(DimensionUtils.dp2px(this, 60));
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credentialDTOs.clear();
                userCredentialsAdapter.notifyDataSetChanged();
                if (etUsername == null || etUsername.getText().equals("")) {
                    Toast.makeText(UserCredentialsActivity.this, "Type username", Toast.LENGTH_SHORT).show();
                }

                QitApi.getUserCredentialsApi().findUser(String.valueOf(etUsername.getText())).enqueue(new Callback<UserCredentialDTO>() {
                    @Override
                    public void onResponse(Call<UserCredentialDTO> call, Response<UserCredentialDTO> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(UserCredentialsActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        credentialDTOs.add(response.body());

                        listView.setAdapter(userCredentialsAdapter);
                    }

                    @Override
                    public void onFailure(Call<UserCredentialDTO> call, Throwable t) {
                        Toast.makeText(UserCredentialsActivity.this, "Cannot load data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        credentialsLayout.addView(etUsername, 0);
        credentialsLayout.addView(btnFind, 1);
    }

}
