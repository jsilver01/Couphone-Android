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

    @GET("/coupons")
    fun getCoupons(
        @Header("Authorization") token: String
    ): Call<CouponResponse>

    @GET("/coupons/status/{coupon-id}")
    fun getCouponUseResponse(
        @Header("Authorization") token: String,
        @Path("coupon-id") couponId: Int
    ): Call<CouponUseResponse>

    @GET("/coupons/stamp/{coupon-id}")
    fun getCouponGetResponse(
        @Header("Authorization") token: String,
        @Path("coupon-id") couponId: Int
    ): Call<CouponGetResponse>
}