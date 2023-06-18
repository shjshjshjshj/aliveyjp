package com.tts.yeojeong.ui.dashboard

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.tts.yeojeong.`class`.linelistendadapter
import com.tts.yeojeong.databinding.ActivitySelectendBinding
import com.tts.yeojeong.dataset.linelistdata
import com.tts.yeojeong.dataset.linelistenddata
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class activitySelectend : AppCompatActivity(){

    val data1: JSONArray? = null
    val line1son : JSONArray? = null
    var checkst = false
    var checkend = false
    var checkstp : String = ""
    var checkendp : String = ""

    val routename : ArrayList<String> = arrayListOf()
    val routecd : ArrayList<String> = arrayListOf()


    val dataset = mutableListOf<linelistdata>()

    var stname : String = ""
    var stcd : String = ""




    private lateinit var binding: ActivitySelectendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide();

        stname = intent.getStringExtra("stname").toString()
        stcd = intent.getStringExtra("stcd").toString()

        Log.e("texxxxt", stname + stcd )







        binding.stbtn.setOnClickListener{
            binding.welintro.setText("출발역을 선택해주세요")
            binding.endbtn.setBackgroundColor(Color.parseColor("#DCDEE5"))
            binding.stbtn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.stbtn.setTypeface(null, Typeface.BOLD)
            binding.endbtn.setTypeface(null, Typeface.NORMAL)
            val intent = Intent(this, activitySelect::class.java)
            startActivity(intent)
        }


        binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(1, stname, stcd) )
        binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.line1btn.setOnClickListener{
            btnreset()
            binding.line1btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line1txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(1, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line2btn.setOnClickListener{
            btnreset()
            binding.line2btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line2txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(2, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line3btn.setOnClickListener{
            btnreset()
            binding.line3btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line3txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(3, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line4btn.setOnClickListener{
            btnreset()
            binding.line4btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line4txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(4, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line5btn.setOnClickListener{
            btnreset()
            binding.line5btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line5txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(5, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line6btn.setOnClickListener{
            btnreset()
            binding.line6btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line6txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(6, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line7btn.setOnClickListener{
            btnreset()
            binding.line7btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line7txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(7, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line8btn.setOnClickListener{
            btnreset()
            binding.line8btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line8txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(8, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.line9btn.setOnClickListener{
            btnreset()
            binding.line9btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.line9txt.setTextColor(Color.parseColor("#FFD12E"))
            binding.endlinelist.adapter = linelistendadapter(this, readjsonfile(9, stname, stcd) )
            binding.endlinelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }










    }





    fun readjsonfile ( i : Int, staname : String, stacd : String): MutableList<linelistenddata> {
        var inputStream: InputStream




        when (i) {
            1 -> inputStream = applicationContext.assets.open("line1.json")
            2 -> inputStream = applicationContext.assets.open("line2.json")
            3 -> inputStream = applicationContext.assets.open("line3.json")
            4 -> inputStream = applicationContext.assets.open("line4.json")
            5 -> inputStream = applicationContext.assets.open("line5.json")
            6 -> inputStream = applicationContext.assets.open("line6.json")
            7 -> inputStream = applicationContext.assets.open("line7.json")
            8-> inputStream = applicationContext.assets.open("line8.json")
            else -> inputStream = applicationContext.assets.open("line9.json")
        }
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        // inputStream으로부터 문자열로 변환

        var lineson : JSONArray



        when (i) {
            1 -> lineson = jsonObject.getJSONObject("line1").getJSONArray("route")
            2 -> lineson = jsonObject.getJSONObject("line2").getJSONArray("route")
            3 -> lineson = jsonObject.getJSONObject("line3").getJSONArray("route")
            4 -> lineson = jsonObject.getJSONObject("line4").getJSONArray("route")
            5 -> lineson = jsonObject.getJSONObject("line5").getJSONArray("route")
            6 -> lineson = jsonObject.getJSONObject("line6").getJSONArray("route")
            7 -> lineson = jsonObject.getJSONObject("line7").getJSONArray("route")
            8-> lineson = jsonObject.getJSONObject("line8").getJSONArray("route")
            else -> lineson = jsonObject.getJSONObject("line9").getJSONArray("route")
        }

        Log.e("line1 test", lineson.toString())

        val result = mutableListOf<linelistenddata>()

        for(i in 0 until lineson.length()){

            // 쪽수 별로 데이터를 읽는다.
            val jObject = lineson.getJSONObject(i)

            Log.e("des","${JSON_Parse(jObject, "station_nm")}\n")

            result.add(linelistenddata(staname, stacd, JSON_Parse(jObject,"station_cd"), JSON_Parse(jObject, "station_nm")))

        }

        Log.e("tessssst", result.toString())

        return result
    }
    private fun btnreset() {

        binding.line1btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line1txt.setTextColor(Color.parseColor("#727996"))

        binding.line2btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line2txt.setTextColor(Color.parseColor("#727996"))

        binding.line3btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line3txt.setTextColor(Color.parseColor("#727996"))

        binding.line4btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line4txt.setTextColor(Color.parseColor("#727996"))

        binding.line5btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line5txt.setTextColor(Color.parseColor("#727996"))

        binding.line6btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line6txt.setTextColor(Color.parseColor("#727996"))

        binding.line7btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line7txt.setTextColor(Color.parseColor("#727996"))

        binding.line8btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line8txt.setTextColor(Color.parseColor("#727996"))

        binding.line9btn.setBackgroundColor(Color.parseColor("#DCDEE5"))
        binding.line9txt.setTextColor(Color.parseColor("#727996"))
    }
    fun JSON_Parse(obj: JSONObject, data : String): String {

        // 원하는 정보를 불러와 리턴받고 없는 정보는 캐치하여 "없습니다."로 리턴받는다.
        return try {
            obj.getString(data)
        } catch (e: Exception) {
            "없습니다."
        }
    }

}
