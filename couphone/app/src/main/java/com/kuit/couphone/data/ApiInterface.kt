package com.kuit.couphone.data

import android.telecom.Call
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
        @Header("Authorization") token: String
    ): Call<UserInfoResponse>

    @POST("/brands")
    fun postBrandRegister(
        @Header("Authorization") token: String
    ): Call<BrandRegisterResponse>

    @GET("/brands")
    fun getBrand(
        @Header("Authorization") token: String
    ): Call<BrandResponse>

    @GET("/brands/{brand-id}")
    fun getBrandDetailed(
        @Header("Authorization") token: String,
        @Path("brand-id") id: Int
    ): Call<BrandDetailedResponse>
}