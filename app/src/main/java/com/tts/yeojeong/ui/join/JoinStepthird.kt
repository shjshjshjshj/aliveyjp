package com.tts.yeojeong.ui.join

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.SetOptions
import com.tts.yeojeong.R
import com.tts.yeojeong.UserDB
import com.tts.yeojeong.databinding.ActivityJoinStepthirdBinding
import com.tts.yeojeong.ui.home.home

class JoinStepthird : AppCompatActivity() {

    private lateinit var binding: ActivityJoinStepthirdBinding

    var settb = false

    var gender : String = ""
    var mb1 : String = ""
    var mb2 : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinStepthirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        binding.join3backbtn.setOnClickListener{
            val intent = Intent(this, JoinStepthird::class.java)
            startActivity(intent)
        }


        if (gender != "" && mb1 != "" && mb2 != "" ){
            settb = true
        }
        binding.bottombtnthired.setOnClickListener{
            if (settb == true) {
                val infodata = hashMapOf( "gender" to gender, "mb1" to mb1, "mb2" to mb2, "isinputinfo" to true)

                UserDB.getRef().document("info").set(infodata, SetOptions.merge())

                val intent = Intent (this, tutorial::class.java)
                intent.putExtra("gender", gender)
                intent.putExtra("mb1", mb1)
                intent.putExtra("mb2", mb2)
                startActivity(intent)
            }

        }

        if (settb) {
            binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
            binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))

        }

        var bt1 = false
        var bt2 = false
        var bt3 = false
        var bt4 = false

        binding.btnmale.setOnClickListener{
            binding.btnmale.setBackgroundResource(R.drawable.bg_innercardsel)
            binding.maletxt.setTextColor(Color.parseColor("#0D70FF"))
            binding.womantxt.setTextColor(Color.parseColor("#DCDEE5"))
            binding.btnfem.setBackgroundResource(R.drawable.bg_innercard)

            binding.mbtiarea.visibility = View.VISIBLE

            gender = "male"
        }
        binding.btnfem.setOnClickListener{
            binding.btnfem.setBackgroundResource(R.drawable.bg_innercardsel)
            binding.maletxt.setTextColor(Color.parseColor("#DCDEE5"))
            binding.btnmale.setBackgroundResource(R.drawable.bg_innercard)
            binding.womantxt.setTextColor(Color.parseColor("#0D70FF"))
            gender = "female"
            binding.mbtiarea.visibility = View.VISIBLE
        }

        binding.btnE.setOnClickListener{
            binding.btnE.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnI.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txte.setTextColor(Color.parseColor("#0D70FF"))
            binding.txti.setTextColor(Color.parseColor("#DCDEE5"))
            mb1 = "E"
            bt1 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }
        binding.btnI.setOnClickListener{
            binding.btnI.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnE.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txti.setTextColor(Color.parseColor("#0D70FF"))
            binding.txte.setTextColor(Color.parseColor("#DCDEE5"))
            mb1 = "I"
            bt1 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }
        binding.btnS.setOnClickListener{
            binding.btnS.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnN.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txts.setTextColor(Color.parseColor("#0D70FF"))
            binding.txtn.setTextColor(Color.parseColor("#DCDEE5"))
            bt2 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }
        binding.btnN.setOnClickListener{
            binding.btnN.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnS.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txtn.setTextColor(Color.parseColor("#0D70FF"))
            binding.txts.setTextColor(Color.parseColor("#DCDEE5"))
            bt2 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }

        binding.btnF.setOnClickListener{
            binding.btnF.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnt.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txtf.setTextColor(Color.parseColor("#0D70FF"))
            binding.txtt.setTextColor(Color.parseColor("#DCDEE5"))
            bt3 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }
        binding.btnt.setOnClickListener{
            binding.btnt.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnF.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txtt.setTextColor(Color.parseColor("#0D70FF"))
            binding.txtf.setTextColor(Color.parseColor("#DCDEE5"))
            bt3 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }

        binding.btnj.setOnClickListener{
            binding.btnj.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnP.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txtj.setTextColor(Color.parseColor("#0D70FF"))
            binding.txtp.setTextColor(Color.parseColor("#DCDEE5"))
            mb2 = "J"
            bt4 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }
        binding.btnP.setOnClickListener{
            binding.btnP.setBackgroundResource(R.drawable.bg_circlecardsel)
            binding.btnj.setBackgroundResource(R.drawable.bg_circlecard)
            binding.txtp.setTextColor(Color.parseColor("#0D70FF"))
            binding.txtj.setTextColor(Color.parseColor("#DCDEE5"))
            mb2 = "P"
            bt4 = true
            if (bt1 == true && bt2 == true && bt3 == true && bt4 == true) {
                binding.bottombtnthired.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                settb = true
            }
        }


    }
}