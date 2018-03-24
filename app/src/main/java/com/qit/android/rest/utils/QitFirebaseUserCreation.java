package com.qit.android.rest.utils;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.qit.android.models.user.User;


public class QitFirebaseUserCreation {

    private FirebaseAuth mAuth;

    public QitFirebaseUserCreation(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(final User user, final Context context, final ProgressDialog progressDialog, final Intent intent, final ActivityOptions transitionActivityOptions){

        mAuth.createUserWithEmailAndPassword(user.getLogin(), user.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("USER_FIREBASE", "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("user"+"/"+firebaseUser.getUid());
                            myRef.setValue(user);

                            Toast.makeText(context, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            context.startActivity(intent, transitionActivityOptions.toBundle());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("USER_FIREBASE", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Creation of user is failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }
}
