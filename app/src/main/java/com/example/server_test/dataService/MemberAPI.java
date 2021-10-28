package com.example.server_test.dataService;

import com.example.server_test.Member;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MemberAPI {
    // 회원 추가
    @POST("members/new")
    Call<Member> addMember(@Body Map<String, String> map);

    // 회원 조회
    @GET("members")
    Call<List<Member>> listMember();

    // 회원 수정
    @POST("members/update")
    Call<Member> updateMember(@Query("user_id") String user_id, @Body Map<String, String> map);

    // 회원 삭제
    @POST("members/delete")
    Call<ResponseBody> deleteMember(@Query("user_id") String user_id);

}
