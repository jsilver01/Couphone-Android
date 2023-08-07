package com.kuit.couphone.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    const val BASE_URL2 = "https://couphone.shop/"

    fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return builder.addInterceptor(logging).build()
    }

    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient()).build()

        return retrofit
    }
    var user_token : String = ""