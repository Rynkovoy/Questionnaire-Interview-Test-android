package com.qit.android.rest.api;

import com.qit.android.models.question.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
@Deprecated
public interface QuestionApi {

    @GET("/api/questions/{questionId}")
    Call<Question> findQuestion(@Path("questionId") Long questionId);

    @GET("/api/questions")
    Call<List<Question>> findAllQuestions();

    @POST("/api/questions")
    Call<Question> saveQuestion(@Body Question question);

    @DELETE("/api/questions/{questionId}")
    void removeQuestion(@Path("questionId") Long questionId);

    @GET("/api/questions")
    Call<List<Question>> findQuestionByQuestionnaireId(@Query("quizId") Long questionId);
}
