package com.tts.yeojeong

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tts.yeojeong.`class`.keywordinfo
import com.tts.yeojeong.`class`.writereviewcategoryadapter
import com.tts.yeojeong.`class`.writereviewselectionadapter
import com.tts.yeojeong.databinding.ActivityWriteReviewBinding
import com.tts.yeojeong.dataset.Writereviewcategorydata
import com.tts.yeojeong.dataset.Writereviewselectiondata
import com.tts.yeojeong.ui.placedetail.placedetailPage

class writeReview : AppCompatActivity(), MainEventListener {


    private lateinit var binding: ActivityWriteReviewBinding

    var image : String = ""
    var des = ""
    var name = ""
    var addr = ""
    var category = ""
    var tel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        val pickMultipleMedia =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
                // Callback is invoked after th user selects a media item or closes the photo picker.
                if (uris.isNotEmpty()) {
                    Log.e("usize", uris.size.toString())

                    if (uris.size == 3 ){
                        Log.e("sett3", "3")
                        binding.newcardview.visibility = View.VISIBLE
                        Glide.with(this).load(uris[0])
                            .into(binding.newcardview)

                        binding.newcardview2.visibility = View.VISIBLE
                        Glide.with(this).load(uris[1])
                            .into(binding.newcardview2)

                        binding.newcardview3.visibility = View.VISIBLE
                        Glide.with(this).load(uris[2])
                            .into(binding.newcardview3)
                    }
                     else if (uris.size == 2){
                        Log.e("sett2", "2")
                        Log.d("PhotoPicker", "Selected URI: $uris")
                        binding.newcardview.visibility = View.VISIBLE
                        Glide.with(this).load(uris[0]).into(binding.newcardview)

                        binding.newcardview2.visibility = View.VISIBLE
                        Glide.with(this).load(uris[1]).into(binding.newcardview2)
                }
                    else if (uris.size == 1){
                        Log.e("sett1", "1")
                        binding.newcardview.visibility = View.VISIBLE
                        Glide.with(this).load(uris[0]).into(binding.newcardview)
                }

                } else {
                        Log.d("PhotoPicker", "No media selected")
                }





            }

        if (intent.hasExtra("image")){
            image = intent.getStringExtra("image").toString()
            des = intent.getStringExtra("des").toString()
            name = intent.getStringExtra("name").toString()
            addr = intent.getStringExtra("addr").toString()
            category = intent.getStringExtra("category").toString()
            tel = intent.getStringExtra("tel").toString()
            if (name != ""){
                binding.reviewplacename.text = name
                binding.reviewCatogory.text = category
                Glide.with(this).load(image)
                    .into(binding.placeimagearea)
            }
        }


        val ct1 = mutableListOf<Writereviewcategorydata>()
        val layer = mutableListOf<Writereviewselectiondata>()
        ct1.add(Writereviewcategorydata("음료가 맛있어요", 1))
        ct1.add(Writereviewcategorydata("특별한 메뉴가 있어요", 1))
        ct1.add(Writereviewcategorydata("디저트가 맛있어요", 1))
        ct1.add(Writereviewcategorydata("가성비가 좋아요", 1))
        ct1.add(Writereviewcategorydata("종류가 다양해요", 1))
        layer.add(Writereviewselectiondata("음식/가격", ct1))

        val ct2 = mutableListOf<Writereviewcategorydata>()
        val comport = mutableListOf<Writereviewselectiondata>()
        ct2.add(Writereviewcategorydata("좌석이 편해요", 2))
        ct2.add(Writereviewcategorydata("친절해요", 2))
        ct2.add(Writereviewcategorydata("실내가 청결해요", 2))
        ct2.add(Writereviewcategorydata("오래 머무르기 좋아요", 2))
        ct2.add(Writereviewcategorydata("선물하기 좋아요", 2))
        layer.add(Writereviewselectiondata("편의시설/기타", ct2))

        val ct3 = mutableListOf<Writereviewcategorydata>()
        val mood = mutableListOf<Writereviewselectiondata>()
        ct3.add(Writereviewcategorydata("뷰가 좋아요", 3))
        ct3.add(Writereviewcategorydata("인테리어가 멋져요", 3))
        ct3.add(Writereviewcategorydata("아늑해요", 3))
        ct3.add(Writereviewcategorydata("공간이 넓어요", 3))
        ct3.add(Writereviewcategorydata("야외 공간이 멋져요", 3))
        layer.add(Writereviewselectiondata("분위기", ct3))

        /*
        val ct4 = mutableListOf<Writereviewcategorydata>()
        val none = mutableListOf<Writereviewselectiondata>()
        ct4.add(Writereviewcategorydata("선택할 키워드가 없어요", 4))
        layer.add(Writereviewselectiondata(" ", ct4)) */
        var touching = false

        binding.inputarea.setOnClickListener{
            Log.e("tesingob", keywordinfo.keyword[0].toString() + keywordinfo.keyword[1].toString() + keywordinfo.keyword[2].toString())

         }


        binding.inputarea.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.inputchecker.text = "0 / 200"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = binding.inputarea.text.toString()
                binding.inputchecker.text = userinput.length.toString() + " / 200"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = binding.inputarea.text.toString()
                binding.inputchecker.text = userinput.length.toString() + " / 200"
            }

        })







        binding.selectkeyword.adapter = writereviewselectionadapter(this, layer)
        binding.selectkeyword.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.addpicture.setOnClickListener {
            binding.newcardview.visibility = View.GONE
            binding.newcardview2.visibility = View.GONE
            binding.newcardview3.visibility = View.GONE
            binding.newcardview3.setImageURI(null)
            binding.newcardview2.setImageURI(null)
            binding.newcardview.setImageURI(null)

            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        binding.checknextbtn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.bottombtntxt.setTextColor(Color.parseColor("#727996"))

        binding.backBTN.setOnClickListener {
            val intent = Intent(this, placedetailPage::class.java)
            startActivity(intent)
        }
    }

    override fun saved(key1 : String, key2 : String, key3 : String) {
        var key = key1
        var key2 = key2
        var key3 = key3

        Log.e("settg", key2)
    }



    fun ontrue() {
        val checkbtn = findViewById<LinearLayout>(R.id.checknextbtn)
        val checktxt = findViewById<TextView>(R.id.bottombtntxt)
        checkbtn.setBackgroundResource(R.drawable.bg_innerblueradius)
        checktxt.setTextColor(Color.parseColor("#FFFFFF"))
        checkbtn.setOnClickListener{
            onBackPressed()
        }
    }

}
