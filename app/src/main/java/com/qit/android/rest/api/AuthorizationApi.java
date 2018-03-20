package com.qit.android.rest.api;

import com.qit.android.models.user.User;
import com.qit.android.models.user.UserCreds;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizationApi {

    @POST("/api/authorization")
    Call<User> authorize(@Body UserCreds user);

}
