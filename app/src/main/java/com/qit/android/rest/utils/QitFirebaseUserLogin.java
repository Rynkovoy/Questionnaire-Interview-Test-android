package com.qit.android.rest.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qit.android.models.user.UserCreds;

public class QitFirebaseUserLogin {

    private FirebaseAuth mAuth;

    public QitFirebaseUserLogin(){
        mAuth = FirebaseAuth.getInstance();
    }

    FirebaseUser firebaseUser;
    public FirebaseUser getFirebaseUser(UserCreds user, final Context context, final ProgressDialog progressDialog, final Intent intent, final ActivityOptions transitionActivityOptions){

        mAuth.signInWithEmailAndPassword(user.getLogin(), user.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FIREBASE_LOGINING", "signInWithEmail:success");
                            firebaseUser = mAuth.getCurrentUser();
                            Toast.makeText(context, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            context.startActivity(intent, transitionActivityOptions.toBundle());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FIREBASE_LOGINING", "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
        return firebaseUser;
    }
}
