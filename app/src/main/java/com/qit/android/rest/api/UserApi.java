package com.qit.android.rest.api;

import com.qit.android.models.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("/api/users")
    Call<List<User>> findAllUsers();

    @GET("/api/users/{login}")
    Call<User> findUser(@Path("login") String username);

    @POST("/api/users")
    Call<User> registerUser(@Body User user);

}
