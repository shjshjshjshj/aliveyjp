package com.tts.yeojeong

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.tts.yeojeong.`class`.homecardadapter
import com.tts.yeojeong.`class`.homelayeradapter
import com.tts.yeojeong.api.*
import com.tts.yeojeong.databinding.ActivityResultBinding
import com.tts.yeojeong.databinding.ActivitySearchBinding
import com.tts.yeojeong.dataset.Homecarddata
import com.tts.yeojeong.dataset.Homelayerdata
import com.tts.yeojeong.ui.dashboard.Dashboard
import com.tts.yeojeong.ui.home.home
import com.tts.yeojeong.ui.notifications.Notifications
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivitySearch : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    var TourArray : List<imITEM>? = null
    lateinit var editText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();
        var touching = false




        binding.vtnCancel.setOnClickListener{
            binding.searchinput.text = null
            binding.vtnCancel.visibility = View.GONE
        }

        binding.searchinput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                editText = binding.searchinput.text.toString()

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                editText = binding.searchinput.text.toString()
                if (editText != null) {
                    binding.vtnCancel.visibility = View.VISIBLE
                }
                else {
                    binding.vtnCancel.visibility = View.GONE
                }
            }
        })

        binding.searchinput.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.iconSearch.performClick()
                handled = true
            }
            handled
        }

        binding.searchvack.setOnClickListener{
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }

        binding.iconSearch.setOnClickListener{
            Log.e("tyrt", editText)

            getTourPos(editText)
        }

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

                    val dataSet = mutableListOf<Homecarddata>()

                    binding.nonresultarea.visibility = View.GONE
                    for (i in 0..totalCount){
                        //don't view no image card
                        if (TourArray!![i].firstimage != ""){
                            dataSet.add(Homecarddata(TourArray!![i].title, TourArray!![i].firstimage, TourArray!![i].addr1, "자연", TourArray!![i].tel, TourArray!![i].mapx, TourArray!![i].mapy))}


                        binding.nonresult.visibility = View.GONE
                        binding.recoLayer.adapter = homecardadapter(this@ActivitySearch, dataSet)
                        binding.recoLayer.layoutManager = LinearLayoutManager(this@ActivitySearch, LinearLayoutManager.HORIZONTAL, false)
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