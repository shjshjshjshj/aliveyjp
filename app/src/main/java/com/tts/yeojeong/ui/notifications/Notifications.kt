package com.tts.yeojeong.ui.notifications

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.storage.FirebaseStorage
import com.tts.yeojeong.R
import com.tts.yeojeong.UserDB
import com.tts.yeojeong.api.TOURKey
import com.tts.yeojeong.api.imITEM
import com.tts.yeojeong.api.tourObject
import com.tts.yeojeong.databinding.ActivityNotificationsBinding
import com.tts.yeojeong.dataset.Bookcarddata
import com.tts.yeojeong.ui.dashboard.Ready
import com.tts.yeojeong.ui.home.home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Notifications : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding


    var nick : Any? = null
    var bookname : Any? = null
    var bookdate : Any? = null

    var TourArray : List<imITEM>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        setContentView(R.layout.activity_notifications)

        supportActionBar?.hide();

        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        UserDB.getRef().document("info").get().addOnSuccessListener{ result ->
            nick = result["name"]
        }
        UserDB.getRef().document("book").get().addOnSuccessListener{ result ->
            bookname = result["name"]
            bookdate = result["istutorial"]
        }
        val img = FirebaseStorage.getInstance().getReference("/" + nick + "profile")
        img.downloadUrl.addOnCompleteListener(OnCompleteListener<Uri> { task ->
            if (task.isSuccessful) {
                Glide.with(this).load(task.result).into(binding.myprofile)
            } else {
                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }

        })





        binding.mytab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 탭이 선택 되었을 때
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택되지 않은 상태로 변경 되었을 때
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택 되었을 때
            }
        })

        binding.mypager.adapter = mytabadapter(this)

        binding.myname.setText(UserDB.Companion.username.name.toString())

        TabLayoutMediator(binding.mytab, binding.mypager) {tab, position ->
            when(position) {
                0 -> tab.text = "북마크"
                1 -> tab.text = "리뷰"
                2 -> tab.text = "리포트"
            }
        }.attach()





        binding.notiDash.setOnClickListener(){
            navRoute()
        }

        binding.notiResult.setOnClickListener(){
            navmyp()
        }
        binding.notiHome.setOnClickListener(){
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

    private fun getTourPos(keyword: String) {



        val call = tourObject.getTourKeyService().getTourkeysearch(keyword)
        // 비동기적으로 실행하기
        call.enqueue(object : Callback<TOURKey> {
            // 응답 성공 시
            override fun onResponse(call: Call<TOURKey>, response: Response<TOURKey>) {
                if (response.isSuccessful) {
                    Log.e("readytourss ", "ready")

                    TourArray = response.body()!!.response.body.items.item

                    val totalCount = response.body()!!.response.body.totalCount - 1

                    Log.e("letstest", TourArray!![1].title+"and"+totalCount + TourArray!![1].firstimage)

                    val dataSet = mutableListOf<Bookcarddata>()

                    for (i in 0..totalCount){
                        //don't view no image card
                        if (TourArray!![i].firstimage != ""){
                            dataSet.add(Bookcarddata(TourArray!![i].title, TourArray!![i].firstimage, TourArray!![i].addr1, TourArray!![i].contentid, TourArray!![i].tel, TourArray!![i].mapx, TourArray!![i].mapy))}
                    }



                }
            }

            // 응답 실패 시
            override fun onFailure(call: Call<TOURKey>, t: Throwable) {
                Log.d("tourapi fail", t.message.toString())
            }
        })
    }

}