package com.tts.yeojeong.api

import com.google.gson.annotations.SerializedName

data class modelweather (
    @SerializedName("rainType") var rainType: String = "",      // 강수 형태
    @SerializedName("humidity") var humidity: String = "",      // 습도
    @SerializedName("sky") var sky: String = "",           // 하능 상태
    @SerializedName("temp") var temp: String = "",          // 기온
    @SerializedName("fcstTime") var fcstTime: String = "",      // 예보시각
)


data class WEATHER (val response : wRESPONSE)
data class wRESPONSE(val header : wHEADER, val body : wBODY)
data class wHEADER(val resultCode : Int, val resultMsg : String)
data class wBODY(val dataType : String, val items : wITEMS, val totalCount : Int)
data class wITEMS(val item : List<wITEM>)

// category : 자료 구분 코드, fcstDate : 예측 날짜, fcstTime : 예측 시간, fcstValue : 예보 값
data class wITEM(val category : String, val fcstDate : String, val fcstTime : String, val fcstValue : String)
