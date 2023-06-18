package com.tts.yeojeong.api

import com.google.gson.annotations.SerializedName

data class tourApiKeySearchResponse(
    @SerializedName("addr1")
    var addr1: String = "",
    @SerializedName("cat1")
    var cat1: String = "",
    @SerializedName("cat2")
    var cat2: String = "",
    @SerializedName("cat3")
    var cat3: String = "",
    @SerializedName("contentid")
    var contentid: String = "",
    @SerializedName("firstimage")
    var firstimage: String = "",
    @SerializedName("tel")
    var tel: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("mapX")
    var mapx: String = "",
    @SerializedName("mapY")
    var mapy: String = "",
)
data class TOURKey (val response : imREPONSE)
data class imREPONSE(val header : imHEADER, val body : imBODY)
data class imHEADER(val resultCode : Int, val resultMsg : String)
data class imBODY(val items : imITEMS, val numOfRows : Int, val PageNo : Int, val totalCount : Int)
data class imITEMS(val item : List<imITEM>)

data class imITEM(
    val addr1: String,
    val addr2: String,
    val areacode : String,
    val contentid: String,
    val firstimage: String,
    val firstimage2 : String,
    val mapx: String,
    val mapy: String,
    val title: String,
    val tel: String
)

