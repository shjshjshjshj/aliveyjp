package com.tts.yeojeong.api

import com.google.gson.GsonBuilder
import com.tts.yeojeong.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class TourBuilder {
    open val baseUrl = BuildConfig.URL_Tour
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val clientVuilder = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply{

            level = HttpLoggingInterceptor.Level.BODY
        }
    )
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(clientVuilder.build())
        .build()

    protected  fun getRetrofit(): Retrofit {
        return  retrofit
    }
}