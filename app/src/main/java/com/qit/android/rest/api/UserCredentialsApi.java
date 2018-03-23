package com.qit.android.rest.api;

import com.qit.android.rest.dto.UserCredentialDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
@Deprecated
public interface UserCredentialsApi {

    @GET("api/users/credentials")
    Call<List<UserCredentialDTO>> findAllUsers();

    @GET("api/users/credentials/{username}")
    Call<UserCredentialDTO> findUser(@Path("username") String username);

    @POST("api/users/credentials")
    Call<UserCredentialDTO> registerUser(@Body UserCredentialDTO userCredentialDTO);

}
