package com.kuit.couphone.data

import com.kuit.couphone.data.kakaoInfo.AddressInfo
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    // 로그인 후 서버로 데이터 전송
    @POST("/auth/login")
    fun postUserInfo(
        @Body user :User
    ): Call<UserResponse>

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

    @GET("/stores/nearby")
    fun getStoresNearby(
        @Header("Authorization") token: String,
        @Query("longitude") longitude :Number,
        @Query("latitude") latitude :Number,
        @Query("is1km") is1km :Boolean,
        @Query("query") query : String?
    ):Call<StoreResponse>
    @PATCH("/users/form")
    fun patchUserInfo(
        @Header("Authorization") token: String,
        @Body  userForm:UserForm,
    ) :Call<UserFormResult>
}