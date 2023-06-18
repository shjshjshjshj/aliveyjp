package com.tts.yeojeong.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.opencsv.CSVReader
import com.tts.yeojeong.BuildConfig
import com.tts.yeojeong.R
import com.tts.yeojeong.`class`.Subwaylayeradapter
import com.tts.yeojeong.databinding.ActivityDashboardBinding
import com.tts.yeojeong.dataset.Subwaycarddata
import com.tts.yeojeong.dataset.Subwaylayerdata
import com.tts.yeojeong.ui.home.home
import com.tts.yeojeong.ui.notifications.Notifications
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    var startpoint: String = "0150"
    var endpoint: String = "0200"

    val routename: ArrayList<String> = arrayListOf()
    val routeline: ArrayList<String> = arrayListOf()
    val routetrans: ArrayList<String> = arrayListOf()
    val savetran: ArrayList<Int> = arrayListOf()

    val nontran: ArrayList<Int> = arrayListOf()

    var stname : String = ""
    var stcd : String = ""
    var edname : String = ""
    var edcd : String = ""

    var layerdetail = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide();

        binding = ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(binding.root)




        if (intent.hasExtra("stcd")){
            stcd = intent.getStringExtra("stcd").toString()
            stname = intent.getStringExtra("stname").toString()
            edname = intent.getStringExtra("edname").toString()
            edcd = intent.getStringExtra("edcd").toString()
            Log.e("testval", stcd +  "###" +edcd)
            if (stcd != "" && edcd != ""){
                binding.fakeScreen.visibility = View.GONE
                startpoint = stcd
                endpoint = edcd
                val thread = NetworkThread()
                thread.start()
            }
        }
        else {
            if (stcd == ""){
                val intent = Intent(this, activitySelect::class.java)
                startActivity(intent)
            }
        }



        val pos: InputStream
        val assetManager = resources.assets
        pos = assetManager.open("subwayPos.csv")
        val posreader: CSVReader = CSVReader(InputStreamReader(pos, "EUC-KR"))

        val allContent = posreader.readAll()
        for (content in allContent) {
            val sb = StringBuilder("")
            Log.d(
                "csv",
                content[0] + " X: " + content[1] + " Y: " + content[2]
            )
        }

        binding.dashHome.setOnClickListener(){
            navhome()
        }

        binding.dashResult.setOnClickListener(){
            navmyp()
        }
        binding.dashNotmy.setOnClickListener(){
            navReport()
        }

        binding.fakeHome.setOnClickListener(){
            navhome()
        }

        binding.fakeResult.setOnClickListener(){
            navmyp()
        }
        binding.fakeMy.setOnClickListener(){
            navReport()
        }



        binding.topTouchArea.setOnClickListener(View.OnClickListener {
            val selentent = Intent(this, activitySelect::class.java)
            startActivity(selentent)
        })


    }

    inner class NetworkThread : Thread() {
        override fun run() {
            var site = "http://apis.data.go.kr/B553766/smt-path"
            var apiKey = "?serviceKey=" + BuildConfig.API_KEY
            val another =
                "&pageNo=3&numOfRows=100&week=DAY&search_type=MINTRF&dept_station_code=" + startpoint + "&dest_station_code=" + endpoint
            val finalsite: String = site + apiKey + another
            Log.e("htmlsite", finalsite)
            val url = URL(finalsite)
            Log.e("URL", "URL die")
            val conn = url.openConnection() //딜레이 or 다른 방법 선택해볼 것
            Log.e("URL", "conn die")
            val input = conn.getInputStream()
            Log.e("URL", "input die")
            val isr = InputStreamReader(input)
            Log.e("URL", "isr die")

            val br = BufferedReader(isr)
            Log.e("URL", "br die")

            var str: String? = null
            val buf = StringBuffer()

            do {
                str = br.readLine()

                if (str != null) {
                    buf.append(str)
                }
            } while (str != null)

            val root = JSONObject(buf.toString())
            val data = root.getJSONObject("data").getJSONArray("route")
            val tyti = data.getJSONObject(0)

            Log.e("subbbb", " ${JSON_Parse(tyti, "station_nm")}\n")
            val dataset = mutableListOf<Subwaycarddata>()
            val layer = mutableListOf<Subwaylayerdata>()
            dataset.add(Subwaycarddata("명지대학교", "학교"))
            dataset.add(Subwaycarddata("명지대도서관", "도서관"))


            for (i in 0 until data.length()) {

                // 쪽수 별로 데이터를 읽는다.
                val jObject = data.getJSONObject(i)

                Log.e("des", "${JSON_Parse(jObject, "station_nm")}\n")
                routename.add(JSON_Parse(jObject, "station_nm"))
                routeline.add(JSON_Parse(jObject, "line_num"))
                routetrans.add(JSON_Parse(jObject, "transfer_loc"))

                if (routetrans[i] != "null") {
                    savetran.add(i)
                } else {
                    nontran.add(i)
                }


                Log.e("bes", "역이름: ${JSON_Parse(jObject, "station_nm")}\n")
                Log.e("tes", "호선정보: ${JSON_Parse(jObject, "line_num")}\n")
                Log.e("ses", "환승여부 ${JSON_Parse(jObject, "transfer_loc")}\n")
                layer.add(Subwaylayerdata(routename[i], routeline[i], routetrans[i], dataset))

            }



            runOnUiThread {
                binding.routelayer.adapter = Subwaylayeradapter(this@Dashboard, layer)
                binding.routelayer.layoutManager =
                    LinearLayoutManager(this@Dashboard, LinearLayoutManager.VERTICAL, false)
            }
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


    fun JSON_Parse(obj: JSONObject, data: String): String {

        // 원하는 정보를 불러와 리턴받고 없는 정보는 캐치하여 "없습니다."로 리턴받는다.
        return try {
            obj.getString(data)
        } catch (e: Exception) {
            "없습니다."
        }


    }
}
