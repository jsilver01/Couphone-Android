package com.kuit.couphone.ui.category.mart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.kuit.couphone.databinding.FragmentCategoryBinding

class CategoryMartFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CategoryMartVPAdapter(this)
        binding.searchListVp.adapter = adapter
        binding.categoryTv.text = "'마트'"
        binding.searchTb.visibility = View.GONE
    }
}