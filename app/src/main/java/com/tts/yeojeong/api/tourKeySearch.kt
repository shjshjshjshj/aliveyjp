package com.tts.yeojeong.api

import com.tts.yeojeong.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface tourKeySearch {
    @GET("searchKeyword1?serviceKey="+ BuildConfig.API_KEY+"&MobileOS=AND&MobileApp=YeoJeong&_type=json&listYN=Y&arrange=A&numOfRows=700&pageNo=1")
    fun getTourkeysearch(
        @Query("keyword")
        keyword: String
    ) : Call<TOURKey>

}