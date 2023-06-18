package com.tts.yeojeong.ui.home

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.kakao.util.maps.helper.Utility
import com.tts.yeojeong.*
import com.tts.yeojeong.R
import com.tts.yeojeong.`class`.homelayeradapter
import com.tts.yeojeong.api.*
import com.tts.yeojeong.databinding.ActivityHomeBinding
import com.tts.yeojeong.dataset.Homecarddata
import com.tts.yeojeong.dataset.Homelayerdata
import com.tts.yeojeong.ui.dashboard.Dashboard
import com.tts.yeojeong.ui.dashboard.Ready
import com.tts.yeojeong.ui.notifications.Notifications
import com.tts.yeojeong.ui.placedetail.placedetailPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는


    var backTime: Long = 0
    private var baseDate = "20210510"  // 발표 일자
    private var baseTime = "1400"      // 발표 시각
    private var curPoint : Point? = null    // 현재 위치의 격자 좌표를 저장할 포인트
    var toass = 1
    var TourArray : List<ITEM>? = null
    var weatherlate = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide();

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this@home, "${UserDB.Companion.username.name}님 여정에 오신 것을 환영합니다}", Toast.LENGTH_SHORT).show()


        binding.fPlaceButtoon.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, placedetailPage::class.java)
            startActivity(intent)
        })
        val permissionList = arrayOf<String>(
            // 위치 권한
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        ActivityCompat.requestPermissions(this, permissionList, 1)

        binding.search.setOnClickListener{
            val intent = Intent(this, ActivitySearch::class.java )
            startActivity(intent)
        }



        Log.e("hope", "i dont wanna die")
        //var nLoc = (activity as login).mLastLocation
        //val calnLoc = dfsXyConv(nLoc.latitude, nLoc.longitude)

        //Log.d("geo", (nLoc.longitude + nLoc.latitude).toString())

        val locationtext: TextView = binding.location



        val cardView: CardView = binding.cardView
        curPoint = Common().dfsXyConv(login().nowLocationX, login().nowLocationY)
        Log.e("posew", "poi" + curPoint!!.x + "a" + curPoint!!.y)

        cardView.setOnClickListener{
            toass += 1
            //requestLocation()
            startLocationUpdates()
        }

        //below related recycler
        val dataSet = mutableListOf(
            Homecarddata("여정여정", "http://tong.visitkorea.or.kr/cms/resource/74/1985174_image2_1.jpg", "서울", "자연", "000", "22", "22"),
            Homecarddata("여3여3여3", "http://tong.visitkorea.or.kr/cms/resource/74/1985174_image2_1.jpg", "서울", "자연", "000", "22", "22")
        )
        /*
        val layer = arrayOf(
            Homelayerdata(
                "Tour Api 표시",
                mutableListOf(Homecarddata("여정여정", "yodyodyodyod", "http://tong.visitkorea.or.kr/cms/resource/74/1985174_image2_1.jpg"))
            )
        )
        binding.recoLayer.adapter = homelayeradapter(requireContext(), layer)
        binding.recoLayer.layoutManager = LinearLayoutManager(requireContext()) */


        /*
        val recyclerView: RecyclerView = recyclerbinding.homeFirstCardList
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val homelayerrecyclerView : RecyclerView = binding.recoLayer
        val layerLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)


        homelayerrecyclerView.layoutManager = layerLayoutManager
        homelayerrecyclerView.adapter = homeLayerAdapter(layer)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = homeCardAdapter(dataSet) */

        //requestLocation()
        mLocationRequest =  LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        // 버튼 이벤트를 통해 현재 위치 찾기
        startLocationUpdates()


        binding.homeDash.setOnClickListener(){
            navRoute()
        }

        binding.homeResult.setOnClickListener(){
            navmyp()
        }
        binding.homeNotmy.setOnClickListener(){
            navReport()
        }



    }



    // 날씨 가져와서 설정하기
    private fun setWeather(nx : Int, ny : Int) {
        // 준비 단계 : base_date(발표 일자), base_time(발표 시각)
        // 현재 날짜, 시간 정보 가져오기
        val cal = Calendar.getInstance()
        baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time) // 현재 날짜
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시각
        val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 분
        // API 가져오기 적당하게 변환
        baseTime = Common().getBaseTime(timeH, timeM)
        // 현재 시각이 00시이고 45분 이하여서 baseTime이 2330이면 어제 정보 받아오기
        if (timeH == "00" && baseTime == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }
        Log.e("time", "time"+baseTime+"a"+baseDate)

        val call = WeatherObject.getRetrofitService().getWeather(60, 1, "JSON", baseDate, baseTime, nx, ny)

        call.enqueue(object : Callback<WEATHER> {
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                Log.e("ldld", "lostaaa")
                if (response.isSuccessful) {
                    // 날씨 정보 가져오기
                    val it: List<wITEM> = response.body()!!.response.body.items.item

                    // 현재 시각부터 1시간 뒤의 날씨 6개를 담을 배열
                    val weatherArr = arrayOf(modelweather(), modelweather(), modelweather(), modelweather(), modelweather(), modelweather())

                    Log.e("Arr", "moArr" + weatherArr)


                    // 배열 채우기
                    var index = 0
                    val totalCount = response.body()!!.response.body.totalCount - 1
                    for (i in 0..totalCount) {
                        index %= 6
                        when(it[i].category) {
                            "PTY" -> weatherArr[index].rainType = it[i].fcstValue     // 강수 형태
                            "REH" -> weatherArr[index].humidity = it[i].fcstValue     // 습도
                            "SKY" -> weatherArr[index].sky = it[i].fcstValue          // 하늘 상태
                            "T1H" -> weatherArr[index].temp = it[i].fcstValue         // 기온
                            else -> continue
                        }
                        index++
                    }
                    Log.e("result","rere" + weatherArr[1] + "a" + weatherArr[0])
                    Log.e("nowtemp", "now is " + weatherArr[0].temp)
                    //temptext.setText(weatherArr[0].temp)

                    weatherArr[0].fcstTime = "지금"
                    // 각 날짜 배열 시간 설정
                    for (i in 1..5) weatherArr[i].fcstTime = it[i].fcstTime
                    val tempText = binding.temp
                    when (weatherArr[0].temp.toInt()){
                        in -30..0 -> setColor("#516ED3")
                        in 1..12 -> setColor("#51C4D3")
                        in 13..20 -> setColor("#51D376")
                        in 21 .. 27 -> setColor("#E5CA3A")
                        else -> setColor("#E5823A")
                    }

                    /*if (weatherArr[0].temp.toInt() < 0) {
                        binding.cardView.setCardBackgroundColor(Color.parseColor("#516ED3"))
                        binding.temp.setTextColor(Color.parseColor("#516ED3"))
                    }
                            else if(1 <= weatherArr[0].temp.toInt() && weatherArr[0].temp.toInt() < 13){
                                 binding.cardView.setCardBackgroundColor(Color.parseColor("#51C4D3"))
                                 binding.temp.setTextColor(Color.parseColor("#51C4D3"))

                    }             else if(13 <= weatherArr[0].temp.toInt() && weatherArr[0].temp.toInt() < 21){
                                  binding.cardView.setCardBackgroundColor(Color.parseColor("#51D376"))
                                  binding.temp.setTextColor(Color.parseColor("#51D376"))

                    }                       else if(21 <= weatherArr[0].temp.toInt() && weatherArr[0].temp.toInt() < 27){
                                              binding.cardView.setCardBackgroundColor(Color.parseColor("E5CA3A"))
                                                binding.temp.setTextColor(Color.parseColor("#E5CA3A"))

                    }                            else if(21 <= weatherArr[0].temp.toInt() && weatherArr[0].temp.toInt() < 27){
                                                     binding.cardView.setCardBackgroundColor(Color.parseColor("E5823A"))
                                                     binding.temp.setTextColor(Color.parseColor("#E5823A"))

                    } */
                    tempText.setText(weatherArr[0].temp + " ℃")
                    if (weatherArr[0].rainType == "0") {
                        when(weatherArr[0].sky){
                            "1" -> binding.weatherIC.setImageDrawable(resources.getDrawable(R.drawable.ic_sun))
                            "3" -> binding.weatherIC.setImageDrawable(resources.getDrawable(R.drawable.ic_cloudy))
                        }
                    }
                    else when(weatherArr[0].rainType) {
                        "1" -> binding.weatherIC.setImageDrawable(resources.getDrawable(R.drawable.ic_rain))
                        "3" -> binding.weatherIC.setImageDrawable(resources.getDrawable(R.drawable.ic_snow))
                    }


                    Toast.makeText(this@home, "날씨가 새로고침되었습니다", Toast.LENGTH_SHORT).show()


                }
            }

            // 응답 실패 시
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                Log.d("api fail", t.message.toString())
            }
        })

    }




    //Call Tour Api related Pos
    private fun getTourPos(nx : String, ny : String, CotID : String) {



        val call = tourObject.getTourService().getTourNowPos( "AND", "YeoJeong", "json", nx, ny, "2000", CotID, BuildConfig.API_KEY, "A")
        val call2 = tourObject.getTourService().getTourNowPos( "AND", "YeoJeong", "json", nx, ny, "2000", "14", BuildConfig.API_KEY, "A")
        val call3 = tourObject.getTourService().getTourNowPos( "AND", "YeoJeong", "json", nx, ny, "2000", "15", BuildConfig.API_KEY, "A")

        Log.e("checking", "check"+call)


        var layer = mutableListOf<Homelayerdata>()

        // 비동기적으로 실행하기
        call.enqueue(object : Callback<TOURPOS> {
            // 응답 성공 시
            override fun onResponse(call: Call<TOURPOS>, response: Response<TOURPOS>) {
                if (response.isSuccessful) {
                    Log.e("readytourss ", "ready")
                    val it: List<ITEM> = response.body()!!.response.body.items.item

                    TourArray = response.body()!!.response.body.items.item

                    val totalCount = response.body()!!.response.body.totalCount - 1

                    Log.e("bodyView", TourArray!![0].title+"and"+totalCount)

                    val dataSet1 = mutableListOf<Homecarddata>()


                    for (i in 0..totalCount){
                        //don't view no image card
                        if (TourArray!![i].firstimage != ""){
                            dataSet1.add(Homecarddata(TourArray!![i].title, TourArray!![i].firstimage, TourArray!![i].addr1, "자연", TourArray!![i].tel, TourArray!![i].mapx, TourArray!![i].mapy))}


                    }
                    layer.add((Homelayerdata("가보고 싶은 스팟", dataSet1)))
                    layer.add((Homelayerdata("자연 장소 추천", dataSet1)))

                    binding.recoLayer.adapter = homelayeradapter(this@home, layer)
                    binding.recoLayer.layoutManager = LinearLayoutManager(this@home, LinearLayoutManager.VERTICAL, false)
                }
            }

            // 응답 실패 시
            override fun onFailure(call: Call<TOURPOS>, t: Throwable) {
                Log.d("tourapi fail", t.message.toString())
            }
        })
        call2.enqueue(object : Callback<TOURPOS> {
            // 응답 성공 시
            override fun onResponse(call: Call<TOURPOS>, response: Response<TOURPOS>) {
                if (response.isSuccessful) {
                    Log.e("readytourss ", "ready")
                    val it: List<ITEM> = response.body()!!.response.body.items.item

                    TourArray = response.body()!!.response.body.items.item

                    val totalCount = response.body()!!.response.body.totalCount - 1

                    Log.e("bodyView", TourArray!![0].title+"and"+totalCount)

                    val dataSet2 = mutableListOf<Homecarddata>()


                    for (i in 0..totalCount){
                        //don't view no image card
                        if (TourArray!![i].firstimage != ""){
                            dataSet2.add(Homecarddata(TourArray!![i].title, TourArray!![i].firstimage, TourArray!![i].addr1, "문화", TourArray!![i].tel, TourArray!![i].mapx, TourArray!![i].mapy))}

                    }
                    layer.add((Homelayerdata("문화 장소 추천", dataSet2)))
                    binding.recoLayer.adapter = homelayeradapter(this@home, layer)
                    binding.recoLayer.layoutManager = LinearLayoutManager(this@home, LinearLayoutManager.VERTICAL, false)

                }
            }

            // 응답 실패 시
            override fun onFailure(call: Call<TOURPOS>, t: Throwable) {
                Log.d("tourapi fail", t.message.toString())
            }
        })
        call3.enqueue(object : Callback<TOURPOS> {
            // 응답 성공 시
            override fun onResponse(call: Call<TOURPOS>, response: Response<TOURPOS>) {
                if (response.isSuccessful) {
                    Log.e("readytourss ", "ready")
                    val it: List<ITEM> = response.body()!!.response.body.items.item

                    TourArray = response.body()!!.response.body.items.item

                    val totalCount = response.body()!!.response.body.totalCount - 1

                    Log.e("bodyView", TourArray!![0].title+"and"+totalCount)

                    val dataSet3 = mutableListOf<Homecarddata>()


                    for (i in 0..totalCount){
                        //don't view no image card
                        if (TourArray!![i].firstimage != ""){
                            dataSet3.add(Homecarddata(TourArray!![i].title, TourArray!![i].firstimage, TourArray!![i].addr1, "행사", TourArray!![i].tel, TourArray!![i].mapx, TourArray!![i].mapy))}


                    }
                    layer.add((Homelayerdata("행사 장소 추천", dataSet3)))
                }
                binding.recoLayer.adapter = homelayeradapter(this@home, layer)
                binding.recoLayer.layoutManager = LinearLayoutManager(this@home, LinearLayoutManager.VERTICAL, false)
            }

            // 응답 실패 시
            override fun onFailure(call: Call<TOURPOS>, t: Throwable) {
                Log.d("tourapi fail", t.message.toString())
            }
        })

    }

    private fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        Looper.myLooper()?.let {
            mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        // 현재 위치의 위경도를 격자 좌표로 변환
        curPoint = Common().dfsXyConv(mLastLocation.latitude, mLastLocation.longitude)
        Log.e("testtest", "test" + curPoint!!.x + "a" + curPoint!!.y)
        // nx, ny지점의 날씨 가져와서 설정하기

        var g = Geocoder(this)

            var list: List<Address>? = null


            try {
                val d1: Double = mLastLocation.latitude
                val d2: Double = mLastLocation.longitude

                list = g.getFromLocation(d1, d2, 10) //10개의 데이터를 얻어오겠다.


                val log = Geocoder(this).getFromLocation(
                    mLastLocation.latitude,
                    mLastLocation.longitude,
                    10
                )

                if (log != null) {
                    binding.location.setText(log?.get(0)?.let { it.subLocality })
                }

            } catch (e: IOException) {
                Log.d("위도/경도", "입출력 오류")
            }



            setWeather(curPoint!!.x, curPoint!!.y)
            Log.e("pospos", location.longitude.toString() + " bbbb" + location.latitude.toString())
            getTourPos(location.longitude.toString(), location.latitude.toString(), "12")

        }


        fun setColor(color: String) {
            binding.temp.setTextColor(Color.parseColor(color))
        }

        fun navRoute() {
            //val intent = Intent(this, Dashboard::class.java)
            val intent = Intent(this, Ready::class.java)
            startActivity(intent)
        }

        fun navReport() {
            val intent = Intent(this, Notifications::class.java)
            startActivity(intent)
        }

        fun navmyp() {
            val intent = Intent(this, com.tts.yeojeong.ui.result.Result::class.java)
            startActivity(intent)
        }

        fun navhome() {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }

        override fun onBackPressed() {
            // 뒤로가기 버튼 클릭
            if (System.currentTimeMillis() - backTime >= 2000) {
                backTime = System.currentTimeMillis()
                Toast.makeText(this, "'뒤로'버튼은 한 번 더 누르면 종료됩니다", Toast.LENGTH_LONG).show()
            } else {
                ActivityCompat.finishAffinity(this);
                System.exit(0)
            }
        }
    }