package com.tts.yeojeong.ui.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tts.yeojeong.R
import com.tts.yeojeong.databinding.ActivityResultBinding
import com.tts.yeojeong.ui.dashboard.Dashboard
import com.tts.yeojeong.ui.dashboard.Ready
import com.tts.yeojeong.ui.home.home
import com.tts.yeojeong.ui.notifications.Notifications

class Result : AppCompatActivity() {


    private lateinit var binding: ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        setContentView(R.layout.activity_result)

        supportActionBar?.hide();

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.resultDash.setOnClickListener(){
            navRoute()
        }

        binding.resultNotmy.setOnClickListener(){
            navReport()
        }
        binding.resultHome.setOnClickListener(){
            navhome()
        }

    }
    fun navRoute () {
        //val intent = Intent(this, Dashboard::class.java)
        val intent = Intent(this, Ready::class.java)
        startActivity(intent)
    }
    fun navReport () {
        val intent = Intent(this, Notifications::class.java)
        startActivity(intent)
    }
    fun navmyp () {
        val intent = Intent(this, Result::class.java)
        startActivity(intent)
    }
    fun navhome() {
        val intent = Intent(this, home::class.java)
        startActivity(intent)
    }


}