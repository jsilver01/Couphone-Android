package com.kuit.couphone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stickode4.SearchFragment
import com.kuit.couphone.R
import com.kuit.couphone.databinding.FragmentHomeBinding
import com.kuit.couphone.ui.category.beauty.CategoryBeautyFragment
import com.kuit.couphone.ui.category.cafe.CategoryFragment
import com.kuit.couphone.ui.category.culture.CategoryCultureFragment
import com.kuit.couphone.ui.category.fashion.CategoryFashionFragment
import com.kuit.couphone.ui.category.mart.CategoryMartFragment
import com.kuit.couphone.ui.category.restaurant.CategoryRestaurantFragment

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.cafeBtn2.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, CategoryFragment()).commit()
        }
        binding.entertainmentBtn1.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, CategoryCultureFragment()).commit()
        }
        binding.martBtn3.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, CategoryMartFragment()).commit()
        }
        binding.beautyBtn4.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, CategoryBeautyFragment()).commit()
        }
        binding.restaurantBtn5.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, CategoryRestaurantFragment()).commit()
        }
        binding.elseBtn6.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, CategoryFashionFragment()).commit()
        }


        binding.arrowupwardBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, SearchFragment()).commit()
        }

        return binding.root
    }
}