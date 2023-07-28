package com.kuit.couphone.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kuit.couphone.LoginActivity
import com.kuit.couphone.MyCouponFragment
import com.kuit.couphone.R
import com.kuit.couphone.databinding.FragmentSettingsBinding
import com.kuit.couphone.ui.home.HomeFragment

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.leftArrowIv.setOnClickListener {
            //백버튼 로직 구현 부분
        }

        binding.logoutTv.setOnClickListener {
            val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(logoutIntent)
            requireActivity().finish()
        }

        binding.rightArrowIv2.setOnClickListener {
            val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(logoutIntent)
            requireActivity().finish()
        }

        binding.leftArrowIv.setOnClickListener {
            val intent = Intent(requireContext(), HomeFragment::class.java)
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commit()
        }

        binding.couphoneTv.setOnClickListener {
            val intent = Intent(requireContext(), MyCouponFragment::class.java)
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, MyCouponFragment()).commit()
        }

        binding.rightArrowIv.setOnClickListener {
            val intent = Intent(requireContext(), MyCouponFragment::class.java)
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, MyCouponFragment()).commit()
        }

    }
}
