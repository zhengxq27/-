package com.study.sysu.photo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MyService {

    @Multipart
    @POST("/uploadImage")
    Call<Object> upload(@Part("description") String description,
                              @Part MultipartBody.Part file);
}