package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kuit.couphone.databinding.ActivityPasswordResetBinding

class PasswordResetActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPasswordResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordResetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerResetButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}