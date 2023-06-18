package com.tts.yeojeong.ui.join

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tts.yeojeong.UserDB
import com.tts.yeojeong.databinding.ActivityJoinStepSecBinding
import java.io.ByteArrayOutputStream
import java.net.URI

class JoinStepSec : AppCompatActivity() {

    private lateinit var binding: ActivityJoinStepSecBinding


    var inname : String = ""
    var  setb = false

    var pimage : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinStepSecBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        val fdb = Firebase.database.reference

        var editText = binding.inputname

        binding.join2backbtn.setOnClickListener{
            val intent = Intent(this, JoinStepsefirst::class.java)
            startActivity(intent)
        }
        val pickMultipleMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uris ->
                // Callback is invoked after th user selects a media item or closes the photo picker.
                if (uris != null) {
                    binding.btnProfile.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, uris))
                    pimage = uris
                }
                else {
                    Log.d("PhotoPicker", "No media selected")

                }
            }

        binding.btnProfile.setOnClickListener{
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        binding.secbottombtn.setOnClickListener{

            if ( setb){

                inname = editText.getText().toString()



                val infodata = hashMapOf( "name" to inname,
                "profile" to pimage)

                UserDB.getRef().document("info").set(infodata, SetOptions.merge())
                pimage?.let { it1 ->  Firebase.storage.reference.child(inname + "profile").putFile(it1) }

                Log.d("testinname", inname.toString())
                val intent = Intent(this, JoinStepthird::class.java)
                startActivity(intent)
            }

        }



        binding.inputname.setOnClickListener(View.OnClickListener  {
            editText = binding.inputname
            if (editText.getText().toString().isNotEmpty()) {
                binding.secbottombtn.setBackgroundColor(Color.parseColor("#0D70FF"))
                binding.joinsecBottomtxt.setTextColor(Color.parseColor("#0D70FF"))
                setb = true
            } else {
                setb = false
//공백이 아닐 때 처리할 내용

            }
        })


        binding.inputname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                editText = binding.inputname
                if (editText.getText().toString().isNotEmpty()) {
                    binding.secbottombtn.setBackgroundColor(Color.parseColor("#0D70FF"))
                    binding.joinsecBottomtxt.setTextColor(Color.parseColor("#FFFFFF"))
                    setb = true
                } else {
                    setb = false
//공백이 아닐 때 처리할 내용

                }
            }
        })

    }



}