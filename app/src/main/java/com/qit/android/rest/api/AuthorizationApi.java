package com.qit.android.rest.api;

import com.qit.android.rest.dto.UserCredentialDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizationApi {

    @POST("/api/authorization")
    Call<UserCredentialDTO> authorize(@Body UserCredentialDTO userCredentialDTO);

}
