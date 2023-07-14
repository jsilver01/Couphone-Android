package com.kuit.couphone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.kuit.couphone.CategoryFragment
import com.kuit.couphone.R
import com.kuit.couphone.databinding.FragmentHomeBinding
import com.kuit.couphone.databinding.FragmentSettingsBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.cafeBtn2.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm,CategoryFragment()).commit()
        }
        return binding.root
    }
}