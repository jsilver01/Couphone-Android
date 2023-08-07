package com.kuit.couphone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kuit.couphone.databinding.ActivityInfomationBinding
import com.kuit.couphone.databinding.ActivitySplashBinding

class InformationActivity: AppCompatActivity() {
    lateinit var binding: ActivityInfomationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.backIv.setOnClickListener {
            finish()
        }
    }

}