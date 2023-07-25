package com.kuit.couphone.ui.category.fashion

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryFashionVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> NormalWearFragment()
            1 -> accesoryFragment()
            else -> ShoesFragment()
        }
    }


}