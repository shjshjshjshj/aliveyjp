package com.tts.yeojeong.ui.placedetail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.tts.yeojeong.R
import com.tts.yeojeong.UserDB
import com.tts.yeojeong.databinding.ActivityPlaceDetailBinding
import com.tts.yeojeong.ui.home.home
import com.tts.yeojeong.ui.join.JoinStepsefirst
import com.tts.yeojeong.ui.join.tutorial
import com.tts.yeojeong.writeReview
import com.tts.yeojeong.writeReviewdata
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.io.IOException
import java.time.LocalDate
import java.util.*



class placedetailPage : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceDetailBinding

    var image: String = ""
    var des = ""
    var name = ""
    var addr = ""
    var category = ""
    var tel = ""
    var mapx: Double? = null
    var mapy: Double? = null
    var book = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide();






        if (intent.hasExtra("image")) {
            image = intent.getStringExtra("image").toString()
            des = intent.getStringExtra("des").toString()
            name = intent.getStringExtra("name").toString()
            addr = intent.getStringExtra("addr").toString()
            category = intent.getStringExtra("category").toString()
            tel = intent.getStringExtra("tel").toString()
            mapx = (intent.getStringExtra("mapx")?.toDouble() ?: Double) as Double
            mapy = (intent.getStringExtra("mapy")?.toDouble() ?: Double) as Double
            val mapPoint = MapPoint.mapPointWithGeoCoord(
                mapy!!,
                mapx!!
            )
            kakaomap(mapPoint)
            Log.e("testval", mapx.toString() + mapy.toString())
            if (image != "") {
                Glide.with(this).load(image)
                    .into(binding.placeimage)
                binding.pname.text = name
                binding.placeAdress.text = addr
                binding.categoryText.text = category
            }
            if (tel == "") {
                binding.telArea.visibility = View.GONE
            }
            if (category == "자연") {
                binding.siteButton.visibility = View.GONE
            }

            checkingbook(name)
            checkingreview(name)





            if (mapy == null) {
                var location: List<Address>? = null
                try {
                    location = Geocoder(this).getFromLocationName(addr, 1)
                } catch (e: IOException) {
                    if (location != null) {
                        if (location!!.isEmpty()) {
                            Log.e("error", "thsisfailgeo")
                        } else {
                            mapx = location!!.get(0).latitude
                            mapy = location!!.get(0).longitude
                            val mapPoint = MapPoint.mapPointWithGeoCoord(mapy!!, mapx!!)
                            kakaomap(mapPoint)
                        }
                    }
                }
            }

        }




        binding.back.setOnClickListener {
            val intent = Intent(this, home::class.java)

            startActivity(intent)
        }
        binding.writeButton.setOnClickListener {
            val intent = Intent(this, writeReviewdata::class.java)
            intent.putExtra("name", name)
            intent.putExtra("image", image)
            intent.putExtra("addr", addr)
            intent.putExtra("category", category)
            startActivity(intent)
        }


        binding.bookButton.setOnClickListener {
            val date = LocalDate.now().toString()
            val bookdata = hashMapOf(
                "name$name" to name,
                "date$name" to date,
                "category$category" to category
            )
            if (book == false) {
                UserDB.getRef().document("book").set(bookdata, SetOptions.merge())

                binding.icBook.setImageDrawable(resources.getDrawable(R.drawable.ic_booksel))
                book = true
                Log.e("book", "book")
            } else {
                val deletebook = hashMapOf<String, Any>(
                    "name" + name to FieldValue.delete(),
                    "date" + name to FieldValue.delete()
                )
                UserDB.getRef().document("book").update(deletebook)
                binding.icBook.setImageDrawable(resources.getDrawable(R.drawable.vectorbook))
                book = false
            }


        }
        binding.writeReview.setOnClickListener {
            val intent = Intent(this, writeReviewdata::class.java)
            intent.putExtra("name", name)
            intent.putExtra("image", image)
            intent.putExtra("addr", addr)
            intent.putExtra("category", category)
            startActivity(intent)
        }


        val shareButton = findViewById<LinearLayout>(R.id.shareButton)


        //below non kakao api
        val callbutton = findViewById<LinearLayout>(R.id.callButton)

        val placetel = tel
        callbutton.setOnClickListener {
            if (tel != "") {
                var intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(placetel)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "연결할 전화번호가 없어요", Toast.LENGTH_LONG).show()
            }

        } //connect call button

        val siteButton = findViewById<LinearLayout>(R.id.siteButton)
        val siteURL = findViewById<TextView>(R.id.siteURL)
        val placeURL = "https://www.mju.ac.kr"
        siteURL.setText(placeURL)
        siteButton.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(placeURL)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        } //connect hyperLInk

        val placeAdress = findViewById<TextView>(R.id.placeAdress)
        val placeAdressButton = findViewById<LinearLayout>(R.id.placeAdressButton)
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        placeAdress.setText(addr)

        placeAdressButton.setOnClickListener {
            clipboard.setPrimaryClip(ClipData(ClipData.newPlainText("label", addr)))
            Toast.makeText(this, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
        }

        val backBtn = findViewById<ImageButton>(R.id.back)
        backBtn.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }//connect Top_back button

    }

    fun kakaomap(mapPoint: MapPoint) {

        val mapView = MapView(this)//make kakao map view
        binding.KakaoView.addView(mapView)
        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))

        // 줌 인
        mapView.zoomIn(true)

        // 줌 아웃
        mapView.zoomOut(true)

        // sample location

        // 지도의 중심을 서울역으로 변경 후 줌 레벨도 변경 해줌.
        mapView.setMapCenterPoint(mapPoint, true)
        mapView.setZoomLevel(2, true)

        // 마커 생성
        val marker = MapPOIItem()
        marker.itemName = name
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        // 설정한 메소드를 marker에 적용함.
        mapView.addPOIItem(marker)

    }

    override fun onBackPressed() {
        val intent = Intent(this, home::class.java)
        startActivity(intent)
    }

    fun geoCoding(address: String): Location {
        return try {
            Geocoder(this, Locale.KOREA).getFromLocationName(address, 1)?.let {
                Location("").apply {
                    latitude = it[0].latitude
                    longitude = it[0].longitude
                }
            } ?: Location("").apply {
                latitude = 0.0
                longitude = 0.0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            geoCoding(address) //재시도
        }
    }

    class CustomBalloonAdapter(inflater: LayoutInflater) : CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.balloon, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_txt)
        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName   // 해당 마커의 정보 이용 가능
            address.text = "getCalloutBalloon"
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            address.text = "getPressedCalloutBalloon"
            return mCalloutBalloon
        }
    }

    fun checkingbook(name: String) {

        UserDB.getRef().document("book").get().addOnSuccessListener { result ->
            val checkbook = result["name$name"]
            Log.e("testbook", result.toString())

            Log.e("thestbook", checkbook.toString())

            if (checkbook == name) {
                binding.icBook.setImageDrawable(resources.getDrawable(R.drawable.ic_booksel))
                book = true
            }

        }
    }

    fun checkingreview(name: String) {

        var writer: Any? = null
        var textcon: Any? = null
        var key1: Any? = null
        var key2: Any? = null
        var key3: Any? = null
        var date: Any? = null
        var pname: Any? = null
        var img1: Any? = null
        var img2: Any? = null
        var img3: Any? = null
        val fsb = FirebaseFirestore.getInstance()


        fsb.collection(name + "review").get().addOnCompleteListener { task ->

            var j = 1
            var afound = false
            var isimg = 0
            if (task.isSuccessful) {
                for (i in task.result!!) {
                    writer = i.data["writername"]
                    textcon = i.data["textcon"]
                    key1 = i.data["key1"]
                    key2 = i.data["key2"]
                    key3 = i.data["key3"]
                    pname = i.data["name"]
                    date = i.data["date"]
                    img1 = i.data["img1"]
                    img2 = i.data["img2"]
                    img3 = i.data["img3"]
                    Log.e("writter", writer.toString())
                    Log.e("textcon", textcon.toString())
                    Log.e("key1", key1.toString())
                    Log.e("key2", key2.toString())
                    Log.e("key3", key3.toString())


                    binding.keyWord1.text = key1 as CharSequence?
                    binding.keyWord2.text = key2 as CharSequence?
                    binding.keyWord3.text = key3 as CharSequence?

                    if (img3 != null){
                        isimg = 3
                    } else if (img2 != null){
                        isimg = 2
                    } else if (img1 != null){
                        isimg = 1
                    } else {
                        isimg = 0
                    }
                    Log.e("setisimg", isimg.toString() + "aaa" + j.toString())

                    if (writer != null) {
                        serreviewui(j, writer.toString(), date.toString(), pname.toString(), isimg)

                    }
                    j += 1


                }


            }


        }
    }

    private fun serreviewui(position: Int, name: String, date: String, place : String, isimg : Int) {
        val img = FirebaseStorage.getInstance().getReference("/" + name + "profile")
        Log.e("testvalue", position.toString() + name + date + place + isimg.toString())

        when (position) {
            1 -> binding.review1AllArea.visibility = View.VISIBLE
            2 -> binding.review2AllArea.visibility = View.VISIBLE
            3 -> binding.review3AllArea.visibility = View.VISIBLE
        }

        var i = 1
        val k = isimg

        when (position) {
            1 -> binding.reviewDate1.text = date as CharSequence?
            2 -> binding.reviewDate2.text = date as CharSequence?
            3 -> binding.reviewDate3.text = date as CharSequence?
        }

        when (position) {
            1 -> binding.profileName.setText(name)
            2 -> binding.profileName2.setText(name)
            3 -> binding.profileName3.setText(name)
        }

        img.downloadUrl.addOnCompleteListener(OnCompleteListener<Uri> { task ->
            if (task.isSuccessful) {
                when (position) {
                    1 -> Glide.with(this).load(task.result).into(binding.review1profile)
                    2 -> Glide.with(this).load(task.result).into(binding.review2profile)
                    3 -> Glide.with(this).load(task.result).into(binding.review3profile)
                }
            } else {
                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }

        })

        if (k == 1){
            Log.e("View only", "vbvb"+place)
            when (position) {
                1 -> binding.review1Image.visibility = View.VISIBLE
                2 -> binding.review2Image.visibility = View.VISIBLE
                3 -> binding.review3Image.visibility = View.VISIBLE
            }
        }
        else if (k > 1){
            Log.e("View m", "vbvb"+place)
            when (position) {
                1 -> binding.review1multiArea.visibility = View.VISIBLE
                2 -> binding.review2multiArea.visibility = View.VISIBLE
                3 -> binding.review3multiArea.visibility = View.VISIBLE
            }
        }






        if (isimg == 1){
            val reviewimg = FirebaseStorage.getInstance().getReference("/" + place + name + "img1")
            reviewimg.downloadUrl.addOnCompleteListener(OnCompleteListener<Uri> { task ->
                if (task.isSuccessful) {
                    when (position) {
                        1 -> Glide.with(this).load(task.result).into(binding.review1Image)
                        2 -> Glide.with(this).load(task.result).into(binding.review2Image)
                        3 -> Glide.with(this).load(task.result).into(binding.review3Image)
                    }
                } else {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }

            })
        }  else {
                val reviewimg1 = FirebaseStorage.getInstance().getReference( place + name + "img1")
                val reviewimg2 = FirebaseStorage.getInstance().getReference( place + name + "img2")
                val reviewimg3 = FirebaseStorage.getInstance().getReference( place + name + "img3")
                Log.e("set img", i.toString() + "/" + place + name + "img" + i.toString() + position.toString() )
                reviewimg1.downloadUrl.addOnCompleteListener(OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful) {
                            when (position) {
                                1 -> Glide.with(this).load(task.result).into(binding.review1img1)
                                2 -> Glide.with(this).load(task.result).into(binding.review2img1)
                                3 -> Glide.with(this).load(task.result).into(binding.review3img1)
                            }

                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }

                })
                reviewimg2.downloadUrl.addOnCompleteListener(OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful) {
                            when (position) {
                                1 -> Glide.with(this).load(task.result).into(binding.review1img2)
                                2 -> Glide.with(this).load(task.result).into(binding.review2img2)
                                3 -> Glide.with(this).load(task.result).into(binding.review3img2)
                            }

                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }

                })
                reviewimg3.downloadUrl.addOnCompleteListener(OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful) {
                            when (position) {
                                1 -> Glide.with(this).load(task.result).into(binding.review1img3)
                                2 -> Glide.with(this).load(task.result).into(binding.review2img3)
                                3 -> Glide.with(this).load(task.result).into(binding.review3img3)
                            }
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }

                })


        }






    }
}
