package com.tts.yeojeong.api

import com.google.gson.annotations.SerializedName

data class tourApiImgResponse(
    @SerializedName("contentid")
    var addr1: String = "",
    @SerializedName("originimgurl")
    var originimgurl: String = "",
    @SerializedName("serialnum")
    var serialnum: String = ""
)

