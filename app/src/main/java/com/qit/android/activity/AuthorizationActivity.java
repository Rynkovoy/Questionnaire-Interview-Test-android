package com.qit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.qit.R;
import com.qit.android.constants.SharedPreferencesTags;
import com.qit.android.rest.api.AuthorizationApi;
import com.qit.android.rest.dto.UserCredentialDTO;
import com.qit.android.rest.utils.QitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends AppCompatActivity {

    private AppCompatEditText etLogin;
    private AppCompatEditText etPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        sharedPreferences = getSharedPreferences(this.getClass().getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SharedPreferencesTags.IS_AUTHORIZE, false)) {
            Intent intent = new Intent(getApplicationContext(), QitActivity.class);
            startActivity(intent);
        }

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
    }

    public void logIn(final View view) {
        if (etLogin == null || String.valueOf(etLogin.getText()).isEmpty()
                || etPassword == null || String.valueOf(etPassword.getText()).isEmpty()) {
            Snackbar.make(view, getResources().getText(R.string.empty_credentials), Snackbar.LENGTH_LONG).show();
            return;
        }

        final UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setUsername(String.valueOf(etLogin.getText()));
        userCredentialDTO.setPassword(String.valueOf(etPassword.getText()));
        QitApi.getApi(AuthorizationApi.class).authorize(userCredentialDTO).enqueue(new Callback<UserCredentialDTO>() {
            @Override
            public void onResponse(Call<UserCredentialDTO> call, Response<UserCredentialDTO> response) {
                if (response.body() != null && !response.body().isEnabled()) {
                    Snackbar.make(view, getResources().getText(R.string.auth_banned), Snackbar.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SharedPreferencesTags.IS_AUTHORIZE, true);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), QitActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<UserCredentialDTO> call, Throwable t) {
                Snackbar.make(view, getResources().getText(R.string.wrong_credentials), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void register(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}

