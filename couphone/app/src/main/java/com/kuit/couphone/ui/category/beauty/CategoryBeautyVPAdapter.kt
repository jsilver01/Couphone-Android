package com.kuit.couphone.ui.category.beauty

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryBeautyVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HairSalonFragment()
            1 -> CosmeticFragment()
            else -> LensFragment()
        }
    }


}