package com.kuit.couphone.data

import com.kuit.couphone.data.kakaoInfo.AddressInfo
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiInterface {
    // 로그인 후 서버로 데이터 전송
    @POST("/auth/login")
    fun postUserInfo(
        @Header("Authorization") token: String?,
        @Query("query") query: String?,
    ): Call<AddressInfo?>?


}