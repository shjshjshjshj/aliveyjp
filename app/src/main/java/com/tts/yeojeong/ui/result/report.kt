package com.tts.yeojeong.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tts.yeojeong.R
import com.tts.yeojeong.databinding.ActivityReportBinding

class report : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

    }
}