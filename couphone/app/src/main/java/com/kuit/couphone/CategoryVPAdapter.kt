package com.kuit.couphone

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> NormalCafeFragment()
            1 -> KidsCafeFragment()
            else -> BoardGameCafeFragment()
        }
    }


}