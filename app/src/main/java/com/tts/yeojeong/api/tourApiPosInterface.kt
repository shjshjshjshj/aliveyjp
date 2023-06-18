package com.tts.yeojeong.api

import com.tts.yeojeong.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface tourApiPosInterface {
    @GET("locationBasedList1?serviceKey="+ BuildConfig.API_KEY)
    fun getTourNowPos(
        @Query("MobileOS")
        MobileOS: String,
        @Query("MobileApp")
        MobileApp: String,
        @Query("_type")
        _type: String,
        @Query("mapX")
        mapX: String,
        @Query("mapY")
        mapY: String,
        @Query("radius")
        radius: String,
        @Query("contentTypeId")
        contentTypeId: String,
        @Query("serviceKey")
        ServiceKey:String,
        @Query("arrange")
        arrange:String
    ) : Call<TOURPOS>
}