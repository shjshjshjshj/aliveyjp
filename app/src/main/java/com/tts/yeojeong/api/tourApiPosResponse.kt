package com.tts.yeojeong.api

import com.google.gson.annotations.SerializedName

data class tourItem(
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
    var title: String = ""
)

data class TOURPOS (val response : REPONSE)
data class REPONSE(val header : HEADER, val body : BODY)
data class HEADER(val resultCode : Int, val resultMsg : String)
data class BODY(val items : ITEMS, val numOfRows : Int, val PageNo : Int, val totalCount : Int)
data class ITEMS(val item : List<ITEM>)

data class ITEM(
    val addr1: String,
    val contenttypeid: String,
    val firstimage: String,
    val mapx: String,
    val mapy: String,
    val modifiedtime: String,
    val tel: String,
    val title: String,
    val cat1: String,
    val cat2: String,
    val cat3: String,
    val arrange: String
)
