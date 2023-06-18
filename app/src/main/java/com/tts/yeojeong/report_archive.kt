package com.tts.yeojeong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tts.yeojeong.databinding.ActivityReportArchiveBinding

class report_archive : AppCompatActivity() {

    private lateinit var binding: ActivityReportArchiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReportArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}