package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kuit.couphone.databinding.ActivityLoginBinding
import com.kuit.couphone.ui.home.HomeFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {

            login()

        }

        binding.buttonForgotPassword.setOnClickListener {
            val intent = Intent(this, PasswordResetActivity::class.java)
            startActivity(intent)
        }

        binding.buttonJoinMember.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun login() {
        binding.phoneNumberEt.text
        binding.passwordEt.text
        if(checkValidUser(binding.phoneNumberEt.text.toString(),binding.passwordEt.text.toString())){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            //다이얼로그 띄우기
        }
    }

    private fun checkValidUser(phonenum: String, pw: String): Boolean {
            //서버에서 해당 phonenum 와 pw로 사용자 찾기
            return true //임시
    }
}
