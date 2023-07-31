package com.kuit.couphone

import com.kuit.couphone.data.AddressInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiInterface {
    // 장소이름으로 검색
    @GET("/v2/local/search/address.json")
    fun getSearchLocation(
        @Header("Authorization") token: String?,
        @Query("query") query: String?,
    ): Call<AddressInfo?>?

    // 카테고리로 검색
    @GET("v2/local/search/category.json")
    fun getSearchCategory(
        @Header("Authorization") token: String?,
        @Query("category_group_code") category_group_code: String?,
        @Query("x") x: String?,
        @Query("y") y: String?,
        @Query("radius") radius: Int
    ): Call<AddressInfo?>?

    // 장소이름으로 특정위치기준으로 검색
    @GET("v2/local/search/keyword.json")
    fun getSearchLocationDetail(
        @Header("Authorization") token: String?,
        @Query("query") query: String?,
        @Query("x") x: String?,
        @Query("y") y: String?,
        @Query("size") size: Int
    ): Call<AddressInfo?>?

    //주소로 검색 (아직안쓰는중)

}