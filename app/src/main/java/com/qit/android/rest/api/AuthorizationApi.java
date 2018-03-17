package com.qit.android.rest.api;

import com.qit.android.models.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizationApi {

    @POST("/api/users")
    Call<User> authorize(@Body User user);

}
