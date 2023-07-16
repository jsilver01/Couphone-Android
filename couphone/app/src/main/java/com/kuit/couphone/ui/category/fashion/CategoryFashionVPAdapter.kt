package com.kuit.couphone.ui.category.fashion

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryFashionVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> InnerWearFragment()
            1 -> NormalWearFragment()
            2 -> SportsWearFragment()
            3 -> accesoryFragment()
            else -> ShoesFragment()
        }
    }


}