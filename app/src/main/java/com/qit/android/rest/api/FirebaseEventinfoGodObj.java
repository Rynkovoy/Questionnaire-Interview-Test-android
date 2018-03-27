package com.qit.android.rest.api;

public class FirebaseEventinfoGodObj {

    private static String firebaseCurrentEventName;

    public static String getFirebaseCurrentEventName() {
        return firebaseCurrentEventName;
    }

    public static void setFirebaseCurrentEventName(String string) {
        firebaseCurrentEventName = string;
    }
}
