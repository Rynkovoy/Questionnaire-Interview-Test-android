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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.android.models.user.User;
import com.qit.android.models.user.UserCreds;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

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

                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = firebaseDatabase.getReference("user");
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                        if (dataSnapshot1.getKey().equalsIgnoreCase(firebaseUser.getUid())){
                                            User user1 = dataSnapshot1.getValue(User.class);
                                            FirebaseEventinfoGodObj.setFirebaseUserFullName(user1.getFirstName()+" "+user1.getLastName());
                                            FirebaseEventinfoGodObj.setFirebaseUSerEmail(user1.getLogin());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.i("FIREBASE_LOGINING", databaseError.getDetails());
                                    Toast.makeText(context, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });


//                            Toast.makeText(context, "Authentication success.",
//                                    Toast.LENGTH_SHORT).show();
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
