package com.tts.yeojeong.ui.join

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.SetOptions
import com.tts.yeojeong.R
import com.tts.yeojeong.UserDB
import com.tts.yeojeong.`class`.tutopageradapter
import com.tts.yeojeong.databinding.ActivityTutorialBinding
import com.tts.yeojeong.ui.home.home

class tutorial : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        initViewpager()

        binding.btnNext.setOnClickListener{
            val intent = Intent(this, home::class.java)
            val infodata = hashMapOf( "istutorial" to true)

            UserDB.getRef().document("info").set(infodata, SetOptions.merge())
            startActivity(intent)
        }
    }

    private fun initViewpager() {
        var viewPagerAdapter = tutopageradapter(this)
        viewPagerAdapter.addFragment(tutof())
        viewPagerAdapter.addFragment(tutosec())
        viewPagerAdapter.addFragment(tutothir())
        viewPagerAdapter.addFragment(tutoflour())
        viewPagerAdapter.addFragment(tutofift())

        binding.tutoArea.apply {
            adapter = viewPagerAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (position == 4){
                        binding.btnNext.visibility = View.VISIBLE
                    }


                    if (position == 0) {
                        functional()
                        binding.indi1.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatorsel))
                    }
                    else if(position == 1) {
                        functional()
                        binding.indi2.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatorsel))

                    }
                    else if(position == 2) {
                        functional()
                        binding.indi3.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatorsel))

                    }
                    else if(position == 3) {
                        functional()
                        binding.indi4.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatorsel))

                    }
                    else{
                        functional()
                        binding.indi5.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatorsel))

                    }


                }
            })
        }


    }
    fun functional (){
        binding.indi1.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatornon))
        binding.indi2.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatornon))
        binding.indi3.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatornon))
        binding.indi4.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatornon))
        binding.indi5.setImageDrawable(resources.getDrawable(R.drawable.bg_indicatornon))
    }

}