package com.tts.yeojeong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class detailReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_review)
        supportActionBar?.hide();
    }
}