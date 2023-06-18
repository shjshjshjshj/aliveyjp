package com.tts.yeojeong.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object tourObject {


    private fun getTour(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/B551011/KorService1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    fun getTourService(): tourApiPosInterface{
        return  getTour().create(tourApiPosInterface::class.java) //retrofit객체 만듦!
    }
    fun getTourImgService(): tourApiImgResponse{
        return getTour().create(tourApiImgResponse::class.java)
    }
    fun getTourKeyService(): tourKeySearch {
        return getTour().create(tourKeySearch::class.java)
    }
}