package com.qit.android.rest.api;

import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.dto.QuestionnaireDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionnaireApi {

    @GET("/api/quizzes/questionnaires/{quizId}")
    Call<Questionnaire> findQuestionnaire(@Path("quizId") Long quizId);

    @GET("/api/quizzes/questionnaires")
    Call<List<Questionnaire>> findAllQuestionnaires();

    @POST("/api/quizzes/questionnaires")
    Call<Questionnaire> saveQuestionnaire(@Body Questionnaire questionnaire);

    @DELETE("/api/quizzes/questionnaires/{quizId}")
    void removeQuestionnaire(@Path("quizId") Long quizId);

}
