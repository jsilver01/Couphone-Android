package com.kuit.couphone

import KakaoAPI
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kuit.couphone.MyLocationFragment.Companion.API_KEY
import com.kuit.couphone.MyLocationFragment.Companion.BASE_URL
import com.kuit.couphone.data.*
import com.kuit.couphone.data.kakaoInfo.GPSInfo
import com.kuit.couphone.databinding.ActivityLoginBinding
import com.kuit.couphone.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                }
                else -> { // Unknown
                    Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if (token != null) {
            var userinfo : User
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e(TAG, "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    Log.d(
                        TAG,
                        "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                    )
                    userinfo = user.kakaoAccount?.email?.let { user!!.kakaoAccount?.profile?.nickname?.let { it1 ->
                        User(it,
                            it1,"admin")
                    } }!!
                    post_user_info(userinfo)
                }

            }

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLogin.setOnClickListener {
            //회원가입이면 --> 서버로부터 회원가입인지 로그인인지 판별

            //회원가입이면
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//            finish()
            //로그인이면
            loginWithKakao()
        }
        transGPS(127.069226866831,37.5436711037005)
    }

    private fun transGPS(x:Double,y:Double){
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getWTMGPS(API_KEY,x,y,"WGS84","WTM")   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<GPSInfo> {
            override fun onResponse(
                call: Call<GPSInfo>,
                response: Response<GPSInfo>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("Test123", response.body()!!.documents[0].x.toString())
                Log.d("Test123", response.body()!!.documents[0].y.toString())
                //initNearbyInfo(response.body()!!.documents[0].x,response.body()!!.documents[0].y)
            }

            override fun onFailure(call: Call<GPSInfo>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }
    private fun loginWithKakao() {
        if (AuthApiClient.instance.hasToken()) {
            // accessToken 정보 제공(만료된 경우 갱신된 accessToken 제공)
            UserApiClient.instance.accessTokenInfo { accessToken, error ->
                if (error == null) {
                    Log.d(TAG, "accessTokenInfo 유효성 체크 성공, 회원번호 >> ${accessToken?.id}")
                    getKakaoUser()
                    var userinfo : User
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.d(
                                TAG,
                                "사용자 정보 요청 성공" +
                                        "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                            )
                            userinfo = user.kakaoAccount?.email?.let { user!!.kakaoAccount?.profile?.nickname?.let { it1 ->
                                User(it,
                                    it1,"admin")
                            } }!!
                            post_user_info(userinfo)
                        }

                    }
//                    var user : User = User()
//                    post_user_info(user)

                } else {
                    Log.d(TAG, "accessTokenInfo 유효성 체크 실패")

                    if (error is KakaoSdkError && error.isInvalidTokenError()) { // 로그인 필요
                        requestKakaoLogin()
                    } else { // 기타 에러
                        Log.e(TAG, "accessToken 기타에러", error)
                    }
                }
            }
        } else {
            requestKakaoLogin()
        }
    }
    private fun requestKakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            Log.d(TAG, "앱으로 로그인")
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            Log.d(TAG, "계정으로 로그인")
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
    private fun getKakaoUser() {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.d(
                    TAG,
                    "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )
                val userinfo =  user.kakaoAccount?.email?.let { user.kakaoAccount?.profile?.nickname?.let { it1 ->
                    User(it, it1,"admin")
                } }
            }

        }
    }
    private fun post_user_info(user : User){
        val service =  getRetrofit().create(ApiInterface::class.java)
        service.postUserInfo(user)
            .enqueue( object : retrofit2.Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful) {
                        val resp = response.body()
                        user_token = resp!!.result.accessToken
                        Log.d("Postuserinfo", resp.toString())
                        if(resp.result.memberLabel == "new"){
                            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
                    else{
                        Log.d("Postuserinfo", response.toString())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Postuserinfo",t.message.toString())
                }

            })
    }
}
