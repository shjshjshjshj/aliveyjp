package com.tts.yeojeong.ui.join

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.tts.yeojeong.R
import com.tts.yeojeong.databinding.ActivityJoinStepsefirstBinding
import com.tts.yeojeong.login
import com.tts.yeojeong.yjterms

class JoinStepsefirst : AppCompatActivity() {

    private lateinit var binding: ActivityJoinStepsefirstBinding

    var checkb = false

    var backTime: Long = 0

    var nonagree = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinStepsefirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        binding.join1backbtn.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        binding.allBtn.setOnClickListener{
            if (nonagree == true) {
                binding.fstbottonbtn.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinfstBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.checkeJoinFst.setImageDrawable(resources.getDrawable(R.drawable.ic_checksel))
                binding.kid1Chkbox.setImageDrawable(resources.getDrawable(R.drawable.ic_checkboxsel))
                nonagree = false
            }
            else {
                binding.fstbottonbtn.setBackgroundColor(Color.parseColor("#DCDEE5"))
                binding.joinfstBottomtxt.setTextColor(Color.parseColor("#727996"))
                binding.checkeJoinFst.setImageDrawable(resources.getDrawable(R.drawable.ic_checke))
                binding.kid1Chkbox.setImageDrawable(resources.getDrawable(R.drawable.ic_checkerbox))
                nonagree = true
            }


        }
        binding.checkKid1.setOnClickListener{
            if (nonagree == true) {
                binding.fstbottonbtn.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinfstBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                binding.checkeJoinFst.setImageDrawable(resources.getDrawable(R.drawable.ic_checksel))
                binding.kid1Chkbox.setImageDrawable(resources.getDrawable(R.drawable.ic_checkboxsel))
                nonagree = false
            }
            else {
                binding.fstbottonbtn.setBackgroundColor(Color.parseColor("#DCDEE5"))
                binding.joinfstBottomtxt.setTextColor(Color.parseColor("#727996"))
                binding.checkeJoinFst.setImageDrawable(resources.getDrawable(R.drawable.ic_checke))
                binding.kid1Chkbox.setImageDrawable(resources.getDrawable(R.drawable.ic_checkerbox))
                nonagree = true
            }
        }



        binding.fstbottonbtn.setOnClickListener{

            if (nonagree == false){
                val intent = Intent(this, JoinStepSec::class.java)
                startActivity(intent)
            }
        }
        binding.seeterms.setOnClickListener{
            val intent = Intent(this, yjterms::class.java)
            startActivity(intent)
        }



    }
    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if(System.currentTimeMillis() - backTime >=2000 ) {
            backTime = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로'버튼은 한 번 더 누르면 종료됩니다", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.finishAffinity(this);
            System.exit(0)
        }
    }
}