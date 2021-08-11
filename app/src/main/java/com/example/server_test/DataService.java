package com.example.server_test;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public class DataService {
    private String BASE_URL = "http://3.21.178.170/"; // TODO REST API 퍼블릭 IP로 변경

    Retrofit retrofitClient =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    SelectAPI select = retrofitClient.create(SelectAPI.class);
    InsertAPI insert = retrofitClient.create(InsertAPI.class);
    UpdateAPI update = retrofitClient.create(UpdateAPI.class);
    DeleteAPI delete = retrofitClient.create(DeleteAPI.class);
}

interface InsertAPI{
    @POST("members/new")
    Call<Member> insertOne(@Body Map<String, String> map);
}

interface SelectAPI{
    @GET("members")
    Call<List<Member>> selectAll();

    @GET("members/findById")
    Call<Member> selectOneId(@Path("user_id") String user_id);

    @GET("members/findByName")
    Call<Member> selectOneName(@Path("user_name") String user_name);
}

interface UpdateAPI{
    @POST("members/update")
    Call<Member> updateOne(@Query("user_id") String user_id, @Body Map<String, String> map);
}

interface DeleteAPI{
    @POST("members/delete")
    Call<ResponseBody> deleteOne(@Query("user_id") String user_id);
}