package com.tts.yeojeong

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.util.maps.helper.Utility
import com.tts.yeojeong.databinding.ActivityLoginBinding
import com.tts.yeojeong.ui.home.home
import com.tts.yeojeong.ui.join.JoinStepsefirst
import com.tts.yeojeong.ui.join.tutorial
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.math.*


class login : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) } //binding biew


    private  lateinit var firebaseAuth: FirebaseAuth
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null

    //ablove about location
    val LOCATION_PERMISSION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val LOCATION_PERMISSION_RESULT = 100

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10
   // lateinit var button: Button
    //lateinit var text1: TextView
    //lateinit var text2: TextView



    var nowLocationX: Double = 0.0
    var nowLocationY: Double = 0.0

    private val RC_SIGN_IN = 99

    object testMJL{
        var latitude = 37.57995662342976
        var longitude = 126.92189838327057
    }

    lateinit var  mGoogleSignInClient: GoogleSignInClient
    lateinit var  resultLauncher: ActivityResultLauncher<Intent>
    //lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var database: DatabaseReference


    override fun onStart() {
        super.onStart()

        database = Firebase.database.reference

        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let{
            Toast.makeText(this, "여정에 오신 것을 환영합니다", Toast.LENGTH_SHORT).show()
            var uid = FirebaseAuth.getInstance().currentUser?.getUid()

            UserDB.getRef().document("info").get().addOnSuccessListener{ result ->
                val checkinput = result["istutorial"]
                val checktuto = result["istutorial"]

                val nickname = result["name"]

                UserDB.Companion.username.name = nickname.toString()

                Log.e("fireworking", "working")

                if (checktuto == true) {
                    val intent = Intent(this, home::class.java)
                    startActivity(intent)
                }
                else if (checkinput == true){
                    val intent = Intent(this, tutorial::class.java)
                    startActivity(intent)
                } else{
                    val intent = Intent(this, JoinStepsefirst::class.java)
                    Log.e("gogo", "gotojoin1")
                    startActivity(intent)
                }
            }


        } ?: Toast.makeText(this, "서비스 이용을 위해 로그인이 필요합니다", Toast.LENGTH_SHORT).show()

        if (account != null) {
            toMainActivity(firebaseAuth.currentUser)
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = Firebase.database.reference

        val intent = Intent(this, JoinStepsefirst::class.java)//login to mainactivity인텐트 설정

        val account = GoogleSignIn.getLastSignedInAccount(this)

        val buttonView = findViewById<Button>(R.id.turnButton)//this is login xml button
        val turnButton = findViewById<Button>(R.id.button)

        val KaKaobtn = findViewById<ImageView>(R.id.btn_kakao)
        val googlebtn = findViewById<ImageView>(R.id.google_login)

        var keyHash = Utility.getKeyHash(this)
        Log.e("Hash", keyHash )

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    Log.e("gogo", "gotojoin2")
                    UserDB.getRef().document("info").get().addOnSuccessListener{ result ->
                        val checkinput = result["istutorial"]
                        val checktuto = result["istutorial"]

                        if (checktuto == true) {
                            val intent = Intent(this, home::class.java)
                            startActivity(intent)
                        }
                        else if (checkinput == true){
                            val intent = Intent(this, tutorial::class.java)
                            startActivity(intent)
                        } else{
                            val intent = Intent(this, JoinStepsefirst::class.java)
                            Log.e("gogo", "gotojoin1")
                            startActivity(intent)
                        }
                    }
                    val intent = Intent(this, JoinStepsefirst::class.java)
                    startActivity(intent)
                }
            }
        }



        buttonView.setOnClickListener {
            var targetDistance = DistanceManager.getDistance(mLastLocation.latitude, mLastLocation.longitude, testMJL.latitude, testMJL.longitude)
            if (targetDistance > 5000){
                startActivity(intent)
            }
            else{
            }
        }//Do click button, exchange activity
        turnButton.setOnClickListener{
            startActivity(intent)
        }

        supportActionBar?.hide();



        mLocationRequest =  LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        // 버튼 이벤트를 통해 현재 위치 찾기
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }
        val context = this

        KaKaobtn.setOnClickListener{
            Log.e("btnbtn", "btnn")

            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }


                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        Log.e("gogo", "gotojoin3")
                        UserDB.getRef().document("info").get().addOnSuccessListener{ result ->
                            val checkinput = result["istutorial"]
                            val checktuto = result["istutorial"]

                            if (checktuto == true) {
                                val intent = Intent(this, home::class.java)
                                startActivity(intent)
                            }
                            else if (checkinput == true){
                                val intent = Intent(this, tutorial::class.java)
                                startActivity(intent)
                            } else{
                                val intent = Intent(this, JoinStepsefirst::class.java)
                                Log.e("gogo", "gotojoin1")
                                startActivity(intent)
                            }
                        }

                        val intent = Intent(this, JoinStepsefirst::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }

        setResultSignUp()

        var gso = GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance()

        googlebtn.setOnClickListener{
            signIn()
            Log.d("logggggggggggggggg", "login call")
        }


    }


    public override fun onActivityResult(requestCode: Int, resultCode : Int, data  : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("LoginActivity", "google sign in failed")
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)


        try { val email = acct?.email.toString()
            val token = acct?.idToken.toString()
            val id = acct?.id.toString()
            Log.d("사용자 정보", email + "MMMMMMM" + token + "######" + FirebaseAuth.getInstance().currentUser?.getUid())
        } catch ( e : ApiException){
            Log.w("Failed logoole", "signInResult:faledCode" + e.statusCode)
        }
        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                    toMainActivity(firebaseAuth?.currentUser)
                } else {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                }
            }
    }

    fun toMainActivity(user: FirebaseUser?) {
        if (user != null) {

            Log.e("settting", "setfire")
            UserDB.getRef().document("info").get().addOnSuccessListener{ result ->
                val checkinput = result["istutorial"]
                val checktuto = result["istutorial"]
                UserDB.Companion.username.name = result["name"] as String
                Log.e("fireworking", "working")

                if (checktuto == true) {
                    val intent = Intent(this, home::class.java)
                    startActivity(intent)
                }
                else if (checkinput == true){
                    val intent = Intent(this, tutorial::class.java)
                    startActivity(intent)
                } else{
                    val intent = Intent(this, JoinStepsefirst::class.java)
                    Log.e("gogo", "gotojoin1")
                    startActivity(intent)
                }
            }
            Log.e("gogo", "gotojoin4")
            startActivity(Intent(this, JoinStepsefirst::class.java))
            finish()
        }
    }
    // signIn
    private fun setResultSignUp() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                
                handleSignInResult(task)
            }
        }
    }

    private fun handleSignInResult(completeTask: Task<GoogleSignInAccount>) {
        try{
            val account = completeTask.getResult((ApiException::class.java))
            val email = account?.email.toString()
            Log.e("정보정보", account.toString() + email)
        } catch (e: ApiException){
            Log.w("failed", "signInResult:failed Code =" + e.statusCode)
        }

    }
    // signIn End

    private fun signIn() {
        /*
        val signInIntent : Intent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)*/

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }



    private fun signOut() { // 로그아웃
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            //updateUI(null)
        }
    }

    private fun revokeAccess() { //회원탈퇴
        // Firebase sign out
        firebaseAuth.signOut()
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this) {

        }
    }









    private fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        binding.text2.text = "위도 : " + mLastLocation.latitude // 갱신 된 위도
        binding.text1.text = "경도 : " + mLastLocation.longitude // 갱신 된 경도

        nowLocationX = mLastLocation.latitude
        nowLocationY = mLastLocation.longitude
        Log.e("pos", "pospospos"+nowLocationX+"a"+nowLocationY)

    }
    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()

            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    object DistanceManager {

        private const val R = 6372.8 * 1000

        fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)
            val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
            val c = 2 * asin(sqrt(a))
            return (R * c).toInt()
        }
    }

}