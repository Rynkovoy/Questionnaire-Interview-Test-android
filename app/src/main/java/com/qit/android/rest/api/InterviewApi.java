package com.qit.android.rest.api;

import com.qit.android.rest.dto.InterviewDTO;
import com.qit.android.rest.dto.QuestionnaireDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterviewApi {

    @GET("/api/interviews/{quizId}")
    Call<InterviewDTO> findInterview(@Path("quizId") Long quizId);

    @GET("/api/interviews")
    Call<List<InterviewDTO>> findAllInterviews();

    @POST("/api/interviews")
    Call<InterviewDTO> saveInterview(@Body InterviewDTO questionnaireDTO);

    @DELETE("/api/interviews/{quizId}")
    void removeInterview(@Path("quizId") Long quizId);

}
