package com.tts.yeojeong.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tts.yeojeong.R
import com.tts.yeojeong.databinding.ActivityDashboardBinding
import com.tts.yeojeong.databinding.ActivityReadyBinding
import com.tts.yeojeong.ui.home.home
import com.tts.yeojeong.ui.notifications.Notifications
import com.tts.yeojeong.ui.result.Result

class Ready : AppCompatActivity() {

    private lateinit var binding: ActivityReadyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        setContentView(R.layout.activity_ready)

        supportActionBar?.hide();

        binding = ActivityReadyBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.fakeHome.setOnClickListener(){
            navhome()
        }

        binding.fakeResult.setOnClickListener(){
            navmyp()
        }
        binding.fakeMy.setOnClickListener(){
            navReport()
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
        val intent = Intent(this, com.tts.yeojeong.ui.result.Result::class.java)
        startActivity(intent)
    }
    fun navhome() {
        val intent = Intent(this, home::class.java)
        startActivity(intent)
    }

}