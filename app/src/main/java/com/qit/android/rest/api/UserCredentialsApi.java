package com.qit.android.rest.api;

import com.qit.android.rest.dto.UserCredentialDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserCredentialsApi {

    @GET("/users/credentials")
    Call<List<UserCredentialDTO>> findAllUsers();

    @GET("/users/credentials/{username}")
    Call<UserCredentialDTO> findUser(@Path("username") String username);

}
