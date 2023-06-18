package com.tts.yeojeong.api

import com.tts.yeojeong.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface tourImgInterface {
    @GET("detailImage1?serviceKey="+ BuildConfig.API_KEY+"&MobileOS=AND&MobileApp=YeoJeong&_type=json&listYN=N&subImageYN=Y&numOfRows=100&pageNo=1")
    fun getTourimg(
        @Query("contentId")
        contentId: String

    ) : Call<TOURIMG>

    data class TOURIMG (val response : REPONSE)

}