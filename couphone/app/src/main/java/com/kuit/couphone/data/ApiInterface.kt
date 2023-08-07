package com.kuit.couphone.data

import com.kuit.couphone.data.kakaoInfo.AddressInfo
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    // 로그인 후 서버로 데이터 전송
    @POST("/auth/login")
    fun postUserInfo(
        @Body user :User
    ): Call<UserResponse?>?

    @GET("/users")
    fun getUserInfo(
        @Header("accept") token: String
    ):Call<UserInfoResponse>


}