package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kuit.couphone.data.User
import com.kuit.couphone.databinding.ActivityRegisterBinding

class RegisterActivity :AppCompatActivity(){
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            registerUser()
            startActivity(intent)
        }
    }
    private fun registerUser() {
        val username = binding.usernameEt.text
        val phonenum = binding.userPhoneNumEt.text
        val userpw = binding.userpwEt.text
        val user  = User(username.toString(),phonenum.toString(),userpw.toString())
    }
}