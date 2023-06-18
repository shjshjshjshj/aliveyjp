package com.tts.yeojeong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.tts.yeojeong.databinding.ActivityPlaceDetailBinding
import com.tts.yeojeong.dataset.PlaceDetailData
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class placeDetail : AppCompatActivity() {
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null


    private lateinit var binding: ActivityPlaceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        callKaKaoM()
    }

    fun callKaKaoM() { //call kakao map api reference
        val mapView = MapView(this)//make kakao map view
        binding.KakaoView.addView(mapView)

        // 줌 인
        mapView.zoomIn(true)

        // 줌 아웃
        mapView.zoomOut(true)

        // sample location
        val mapPoint = MapPoint.mapPointWithGeoCoord(37.554668024641515,
            126.97059786786713)

        // 지도의 중심을 서울역으로 변경 후 줌 레벨도 변경 해줌.
        mapView.setMapCenterPoint(mapPoint, true)
        mapView.setZoomLevel(2, true)


        // 마커 생성
        val marker = MapPOIItem()
        marker.itemName = "가나다라마바사아자차카타파하가나다라마바사아자파카타파하"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

        // 설정한 메소드를 marker에 적용함.
        mapView.addPOIItem(marker)

        val pname = intent.getStringExtra("name")
        val pdes = intent.getStringExtra("des")
        val pimg = intent.getStringExtra("image")
        val paddr = intent.getStringExtra("addr")

        val detailList = mutableListOf<PlaceDetailData>()

        detailList.add(PlaceDetailData(pname.toString(),pdes.toString(),pimg.toString(),paddr.toString()))
        binding.pname.setText(pname)
    }
}