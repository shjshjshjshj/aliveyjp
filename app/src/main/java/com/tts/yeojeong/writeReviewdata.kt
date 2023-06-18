package com.tts.yeojeong

import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tts.yeojeong.`class`.keywordinfo
import com.tts.yeojeong.`class`.writereviewcategoryadapter
import com.tts.yeojeong.`class`.writereviewselectionadapter
import com.tts.yeojeong.databinding.ActivityWriteReviewBinding
import com.tts.yeojeong.databinding.ActivityWriteReviewdataBinding
import com.tts.yeojeong.dataset.Writereviewcategorydata
import com.tts.yeojeong.dataset.Writereviewselectiondata
import com.tts.yeojeong.ui.home.home
import com.tts.yeojeong.ui.join.JoinStepsefirst
import com.tts.yeojeong.ui.join.tutorial
import com.tts.yeojeong.ui.placedetail.placedetailPage
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class writeReviewdata : AppCompatActivity() {


    private lateinit var binding: ActivityWriteReviewdataBinding

    var image : String = ""
    var des = ""
    var name = ""
    var addr = ""
    var category = ""
    var tel = ""
    var key1 : String = ""
    var key2 : String = ""
    var key3 : String = ""

    var key1d : Int = 0
    var key2d : Int = 0
    var key3d : Int = 0
    var img1 : Uri? = null
    var img2 : Uri? = null
    var img3 : Uri? = null
    var userinput : String? = null
    var nickname : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteReviewdataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        UserDB.getRef().document("info").get().addOnSuccessListener{ result ->
            val checkname = result["name"]

            nickname = checkname.toString()

        }


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
                        img1 = uris[0]

                        binding.newcardview2.visibility = View.VISIBLE
                        Glide.with(this).load(uris[1])
                            .into(binding.newcardview2)
                        img2 = uris[1]

                        binding.newcardview3.visibility = View.VISIBLE
                        Glide.with(this).load(uris[2])
                            .into(binding.newcardview3)
                        img3 = uris[2]
                    }
                    else if (uris.size == 2){
                        Log.e("sett2", "2")
                        Log.d("PhotoPicker", "Selected URI: $uris")
                        binding.newcardview.visibility = View.VISIBLE
                        Glide.with(this).load(uris[0]).into(binding.newcardview)
                        img1 = uris[0]

                        binding.newcardview2.visibility = View.VISIBLE
                        Glide.with(this).load(uris[1]).into(binding.newcardview2)
                        img2 = uris[1]
                    }
                    else if (uris.size == 1){
                        Log.e("sett1", "1")
                        binding.newcardview.visibility = View.VISIBLE
                        Glide.with(this).load(uris[0]).into(binding.newcardview)
                        img1 = uris[0]
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

        binding.key0t1.setOnClickListener{
            t0touch(1)
            key1 = binding.keyworddes0t1.getText() as String
            key1d = 1
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key0t2.setOnClickListener{
            t0touch(2)
            key1 = binding.keywordd0t2.getText() as String
            key1d= 2
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key0t3.setOnClickListener{
            t0touch(3)
            key1 = binding.keywordd0t3.getText() as String
            key1d = 3
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key0t4.setOnClickListener{
            t0touch(4)
            key1 = binding.keywordd0t4.getText() as String
            key1d = 4
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key0t5.setOnClickListener{
            t0touch(5)
            key1 = binding.keywordd0t5.getText() as String
            key1d = 5
            checking(key1, key2, key3, key1d, key2d, key3d)
        }


        binding.key1t1.setOnClickListener{
            t1touch(1)
            key2 = binding.keyworddes1t1.getText() as String
            key2d = 1
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key1t2.setOnClickListener{
            t1touch(2)
            key2 = binding.keywordd1t2.getText() as String
            key2d = 2
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key1t3.setOnClickListener{
            t1touch(3)
            key2 = binding.keywordd1t3.getText() as String
            key2d = 3
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key1t4.setOnClickListener{
            t1touch(4)
            key2 = binding.keywordd1t4.getText() as String
            key2d = 4
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key1t5.setOnClickListener{
            t1touch(5)
            key2 = binding.keywordd1t5.getText() as String
            key2d = 5
            checking(key1, key2, key3, key1d, key2d, key3d)
        }


        binding.key2t1.setOnClickListener{
            t2touch(1)
            key3 = binding.keyworddes2t1.getText() as String
            key3d = 1
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key2t2.setOnClickListener{
            t2touch(2)
            key3 = binding.keywordd2t2.getText() as String
            key3d = 2
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key2t3.setOnClickListener{
            t2touch(3)
            key3 = binding.keywordd2t3.getText() as String
            key3d = 3
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key2t4.setOnClickListener{
            t2touch(4)
            key3 = binding.keywordd2t4.getText() as String
            key3d = 4
            checking(key1, key2, key3, key1d, key2d, key3d)
        }
        binding.key2t5.setOnClickListener{
            t2touch(5)
            key3 = binding.keywordd2t5.getText() as String
            key3d = 5
            checking(key1, key2, key3, key1d, key2d, key3d)
        }



        binding.inputarea.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.inputchecker.text = "0 / 200"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                userinput = binding.inputarea.text.toString()
                binding.inputchecker.text = userinput!!.length.toString() + " / 200"
            }

            override fun afterTextChanged(s: Editable?) {
                userinput = binding.inputarea.text.toString()
                binding.inputchecker.text = userinput!!.length.toString() + " / 200"
            }

        })






        binding.addpicture.setOnClickListener {
            binding.newcardview.visibility = View.GONE
            binding.newcardview2.visibility = View.GONE
            binding.newcardview3.visibility = View.GONE
            binding.newcardview3.setImageURI(null)
            binding.newcardview2.setImageURI(null)
            binding.newcardview.setImageURI(null)

            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }


    }
    fun t0touch(i : Int){
        binding.key0t1.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key0t2.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key0t3.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key0t4.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key0t5.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.keyworddes0t1.setTextColor(Color.parseColor("#000000"))
        binding.keywordd0t2.setTextColor(Color.parseColor("#000000"))
        binding.keywordd0t3.setTextColor(Color.parseColor("#000000"))
        binding.keywordd0t4.setTextColor(Color.parseColor("#000000"))
        binding.keywordd0t5.setTextColor(Color.parseColor("#000000"))
        when (i) {
            1 ->  binding.key0t1.setBackgroundResource(R.drawable.bg_innerbluekey)
            2 -> binding.key0t2.setBackgroundResource(R.drawable.bg_innerbluekey)
            3 -> binding.key0t3.setBackgroundResource(R.drawable.bg_innerbluekey)
            4 -> binding.key0t4.setBackgroundResource(R.drawable.bg_innerbluekey)
            5 -> binding.key0t5.setBackgroundResource(R.drawable.bg_innerbluekey)
        }
        when (i) {
            1 -> binding.keyworddes0t1.setTextColor(Color.parseColor("#FFFFFF"))
            2 -> binding.keywordd0t2.setTextColor(Color.parseColor("#FFFFFF"))
            3 -> binding.keywordd0t3.setTextColor(Color.parseColor("#FFFFFF"))
            4 -> binding.keywordd0t4.setTextColor(Color.parseColor("#FFFFFF"))
            5 -> binding.keywordd0t5.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }
    fun t1touch(i : Int){
        binding.key1t1.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key1t2.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key1t3.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key1t4.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key1t5.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.keyworddes1t1.setTextColor(Color.parseColor("#000000"))
        binding.keywordd1t2.setTextColor(Color.parseColor("#000000"))
        binding.keywordd1t3.setTextColor(Color.parseColor("#000000"))
        binding.keywordd1t4.setTextColor(Color.parseColor("#000000"))
        binding.keywordd1t5.setTextColor(Color.parseColor("#000000"))
        when (i) {
            1 ->  binding.key1t1.setBackgroundResource(R.drawable.bg_innerbluekey)
            2 -> binding.key1t2.setBackgroundResource(R.drawable.bg_innerbluekey)
            3 -> binding.key1t3.setBackgroundResource(R.drawable.bg_innerbluekey)
            4 -> binding.key1t4.setBackgroundResource(R.drawable.bg_innerbluekey)
            5 -> binding.key1t5.setBackgroundResource(R.drawable.bg_innerbluekey)
        }
        when (i) {
            1 -> binding.keyworddes1t1.setTextColor(Color.parseColor("#FFFFFF"))
            2 -> binding.keywordd1t2.setTextColor(Color.parseColor("#FFFFFF"))
            3 -> binding.keywordd1t3.setTextColor(Color.parseColor("#FFFFFF"))
            4 -> binding.keywordd1t4.setTextColor(Color.parseColor("#FFFFFF"))
            5 -> binding.keywordd1t5.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }
    fun t2touch(i : Int){
        binding.key2t1.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key2t2.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key2t3.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key2t4.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.key2t5.setBackgroundResource(R.drawable.bg_innerwhiteradius)
        binding.keyworddes2t1.setTextColor(Color.parseColor("#000000"))
        binding.keywordd2t2.setTextColor(Color.parseColor("#000000"))
        binding.keywordd2t3.setTextColor(Color.parseColor("#000000"))
        binding.keywordd2t4.setTextColor(Color.parseColor("#000000"))
        binding.keywordd2t5.setTextColor(Color.parseColor("#000000"))
        when (i) {
            1 ->  binding.key2t1.setBackgroundResource(R.drawable.bg_innerbluekey)
            2 -> binding.key2t2.setBackgroundResource(R.drawable.bg_innerbluekey)
            3 -> binding.key2t3.setBackgroundResource(R.drawable.bg_innerbluekey)
            4 -> binding.key2t4.setBackgroundResource(R.drawable.bg_innerbluekey)
            5 -> binding.key2t5.setBackgroundResource(R.drawable.bg_innerbluekey)
        }
        when (i) {
            1 -> binding.keyworddes2t1.setTextColor(Color.parseColor("#FFFFFF"))
            2 -> binding.keywordd2t2.setTextColor(Color.parseColor("#FFFFFF"))
            3 -> binding.keywordd2t3.setTextColor(Color.parseColor("#FFFFFF"))
            4 -> binding.keywordd2t4.setTextColor(Color.parseColor("#FFFFFF"))
            5 -> binding.keywordd2t5.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }
    fun checking( key1 : String, ke2 : String, key3 : String, key1d : Int, key2d : Int, key3d : Int){
        if (key1 != "" && key2 != "" && key3 != ""){
            binding.checknextbtn.setBackgroundResource(R.drawable.bg_innerblueradius)
            binding.bottombtntxt.setTextColor(Color.parseColor("#FFFFFF"))

                binding.checknextbtn.setOnClickListener {

                    val date = LocalDateTime.now().toString()
                    Log.e("firwworking", "ddd")
                    val day = LocalDate.now().toString()

                    val reviewid = date + name + FirebaseAuth.getInstance().currentUser?.uid.toString()

                    val infodata = hashMapOf( "writer" to FirebaseAuth.getInstance().currentUser?.uid.toString(), "writername" to nickname,
                        "name" to name, "key1" to key1, "key2" to key2, "key3" to key3, "key1d" to key1d, "key2d" to key2d, "key3d" to key3d, "img1" to img1, "img2" to img2, "img3" to img3, "textcon" to userinput, "date" to day)


                    var reviewref = Firebase.storage.reference
                    img1?.let { it1 -> reviewref.child(name+nickname+"img1").putFile(it1) }
                    img2?.let { it1 -> reviewref.child(name+nickname+"img2").putFile(it1) }
                    img3?.let { it1 -> reviewref.child(name+nickname+"img3").putFile(it1) }




                    val review = FirebaseFirestore.getInstance().collection(name + "review")

                    review.document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(infodata, SetOptions.merge()).addOnCompleteListener {

                        onBackPressed()
                    }




            }
        }
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
