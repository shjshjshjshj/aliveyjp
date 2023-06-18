package com.tts.yeojeong

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kakao.sdk.common.KakaoSdk

class GlabaoApplication : Application() {
    override  fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.API_KAKAO)
    }
}