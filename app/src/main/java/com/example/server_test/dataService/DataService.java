package com.example.server_test.dataService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataService {
    private String BASE_URL = "http://3.21.178.170/"; // TODO REST API 퍼블릭 IP로 변경

    Retrofit retrofitClient =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


    public MemberAPI member = retrofitClient.create(MemberAPI.class);
    public PubAPI pub = retrofitClient.create(PubAPI.class);
}

interface CompetitionAPI {
    // Competition 추가

    // Competition 조회

    // Competition 수정

    // Competition 삭제

}