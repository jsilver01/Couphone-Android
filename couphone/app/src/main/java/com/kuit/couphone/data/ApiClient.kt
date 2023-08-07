package com.kuit.couphone.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    private val BASE_URL = "https://dapi.kakao.com/"
    private var retrofit: Retrofit? = null
    val apiClient: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}