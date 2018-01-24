package com.qit.android.rest.api;

import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.rest.dto.QuizDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionnaireApi {

    @GET("/api/questionnaires/{quizId}")
    Call<QuestionnaireDTO> findQuestionnaire(@Path("quizId") Long quizId);

    @GET("/api/questionnaires")
    Call<List<QuestionnaireDTO>> findAllQuestionnaires();

    @POST("/api/questionnaires")
    Call<QuestionnaireDTO> saveQuestionnaire(@Body QuestionnaireDTO questionnaireDTO);

    @DELETE("/api/questionnaires/{quizId}")
    void removeQuestionnaire(@Path("quizId") Long quizId);

}
