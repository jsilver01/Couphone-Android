package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kuit.couphone.data.User
import com.kuit.couphone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            registerUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        val username = binding.usernameEt.text.toString()
        val phonenum = binding.phonenumberEt.text.toString()
        val userpw = binding.passwordEt.text.toString()
        val user = User(username, phonenum, userpw)
        // 여기서 user 객체를 활용하여 사용자 정보를 저장하거나 처리하는 로직을 추가합니다.
    }
}
