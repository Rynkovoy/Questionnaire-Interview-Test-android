package com.qit.android.rest.api;

import com.qit.android.models.answer.Variant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
@Deprecated
public interface VariantApi {

    @GET("/api/variants/{variantId}")
    Call<Variant> findVariants(@Path("variantId") Long questionId);

    @GET("/api/variants")
    Call<List<Variant>> findAllVariants();

    @POST("/api/variants")
    Call<Variant> saveVariant(@Body Variant question);

    @DELETE("/api/variants/{variantId}")
    void removeVariant(@Path("variantId") Long questionId);

}
