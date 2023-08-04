package com.kuit.couphone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.data.LocalSearchDB
import com.kuit.couphone.data.LocalSearchEntity
import com.kuit.couphone.databinding.FragmentInfomationBinding
import com.kuit.couphone.databinding.FragmentSearchBinding
import com.kuit.couphone.ui.home.HomeFragment
import java.util.*
import kotlin.collections.ArrayList

class InformationFragment: Fragment() {
    lateinit var binding: FragmentInfomationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfomationBinding.inflate(inflater, container, false)
        binding.backIv.setOnClickListener {

        }
        return binding.root
    }

}