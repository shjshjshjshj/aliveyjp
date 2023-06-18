package com.tts.yeojeong

import android.graphics.Bitmap
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class UserDB {





    companion object {


        private lateinit var auth: FirebaseAuth
        val fdb = Firebase.database.reference

        val db = Firebase.firestore

        var storageref = Firebase.storage.reference

        fun getUid(): String {

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }
        fun getRef() : CollectionReference {
            auth = FirebaseAuth.getInstance()

            val fc = FirebaseFirestore.getInstance().collection("userDB" + auth.currentUser?.uid.toString())


            return fc
        }

        fun uploadimg( usefor : String): StorageReference {
            var result = storageref.child(username.name + usefor)

            return result
        }
        object username {
            var name : String = ""
        }

    }
}