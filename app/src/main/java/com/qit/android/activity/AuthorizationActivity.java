package com.qit.android.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.transition.Slide;
import android.support.transition.TransitionInflater;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qit.R;
import com.qit.android.constants.SharedPreferencesTags;
import com.qit.android.models.user.User;
import com.qit.android.models.user.UserCreds;
import com.qit.android.rest.api.AuthorizationApi;
import com.qit.android.rest.dto.UserCredentialDTO;
import com.qit.android.rest.utils.QitApi;
import com.qit.android.rest.utils.QitFirebaseUserLogin;
import com.qit.android.utils.BtnClickAnimUtil;

import net.igenius.customcheckbox.CustomCheckBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends AppCompatActivity {

    private AppCompatEditText etLogin;
    private AppCompatEditText etPassword;
    private SharedPreferences sharedPreferences;

    private CustomCheckBox rememberCheckBox;

    private Button etForgotePass;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etLogin = findViewById(R.id.etLoginAut);
        etPassword = findViewById(R.id.etPasswordAut);
        etForgotePass = findViewById(R.id.etForgotPass);

        rememberCheckBox = findViewById(R.id.rememberCheckBox);

        String sharedLogin = PreferenceManager.getDefaultSharedPreferences(this).getString("LOGIN", "");
        String sharedPassword = PreferenceManager.getDefaultSharedPreferences(this).getString("PASSWORD", "");

        if (!sharedLogin.equalsIgnoreCase("") && !sharedPassword.equalsIgnoreCase("")){
            etLogin.setText(sharedLogin);
            etPassword.setText(sharedPassword);
        }

    }

    public void logIn(final View view) {

        BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, this);

        if (etLogin == null || String.valueOf(etLogin.getText()).isEmpty()
                || etPassword == null || String.valueOf(etPassword.getText()).isEmpty()) {
            Snackbar.make(view, getResources().getText(R.string.empty_credentials), Snackbar.LENGTH_LONG).show();
            return;
        }

        if (rememberCheckBox.isEnabled()){
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("LOGIN", String.valueOf(etLogin.getText())).apply();
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("PASSWORD", String.valueOf(etPassword.getText())).apply();
        }

        final UserCreds user = new UserCreds();
        user.setLogin(String.valueOf(etLogin.getText()));
        user.setPassword(String.valueOf(etPassword.getText()));

        ProgressDialog dialog = new ProgressDialog(AuthorizationActivity.this);
        dialog.setMessage("Processing...");
        dialog.show();

        Intent i = new Intent(AuthorizationActivity.this, NewEventOrChoseEventActivity.class);

        View sharedView = findViewById(R.id.imgLogo);
        String transitionName = "appLogo";

        ActivityOptions transitionActivityOptions = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(AuthorizationActivity.this, sharedView, transitionName);
        }

        QitFirebaseUserLogin qitFirebaseUserLogin = new QitFirebaseUserLogin();
        FirebaseUser firebaseUser = qitFirebaseUserLogin.getFirebaseUser(user, this, dialog, i, transitionActivityOptions);

//        if (firebaseUser == null) {
//            etForgotePass.setVisibility(View.VISIBLE);
//            Handler handler = new Handler();
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    etForgotePass.setVisibility(View.GONE);
//                }
//            };
//            handler.postDelayed(runnable, 5000);
//        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void register(View view) {

        BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, this);

        Intent i = new Intent(AuthorizationActivity.this, RegistrationActivity.class);

        View sharedView = findViewById(R.id.imgLogo);
        String transitionName = "appLogo";

        ActivityOptions transitionActivityOptions = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(AuthorizationActivity.this, sharedView, transitionName);
        }
        startActivity(i, transitionActivityOptions.toBundle());
        finish();

    }

    public void forgot(View view) {
        BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, this);
    }
}

