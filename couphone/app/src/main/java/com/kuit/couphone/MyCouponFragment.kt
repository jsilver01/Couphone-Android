package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kuit.couphone.databinding.FragmentMyCouponBinding
import com.kuit.couphone.ui.settings.SettingsFragment

class MyCouponFragment : Fragment() {
    private lateinit var binding : FragmentMyCouponBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCouponBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previousArrow1.setOnClickListener {
            val intent = Intent(requireContext(), SettingsFragment::class.java)
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, SettingsFragment()).commit()
        }
    }
}