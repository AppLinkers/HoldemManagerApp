package com.example.server_test.dataService;

import com.example.server_test.pub.Pub;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface PubAPI {
    // Pub 추가
    @Multipart
    @POST("holdem_pub/save")
    Call<Pub> addPub(@Part MultipartBody.Part multipartFile, @PartMap Map<String, RequestBody> map);

    // Pub 조회
    @GET("holdem_pub/list")
    Call<List<Pub>> listPub();


    // Pub 수정

    // Pub 삭제

}
