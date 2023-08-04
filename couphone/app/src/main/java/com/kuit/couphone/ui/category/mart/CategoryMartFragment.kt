package com.kuit.couphone.ui.category.mart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.BaseItemAdapter
import com.kuit.couphone.InformationFragment
import com.kuit.couphone.MyCouponFragment
import com.kuit.couphone.R
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.databinding.FragmentCategoryBinding
import com.kuit.couphone.ui.home.HomeFragment

class CategoryMartFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    var adapter : BaseItemAdapter?= null
    var storeList = ArrayList<StoreInfo>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        initDummyData()
        binding.backIv.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BaseItemAdapter(storeList)
        binding.categoryListRv.adapter = adapter
        binding.categoryListRv.layoutManager = LinearLayoutManager(context)
        binding.categoryTv.text = "'마트'"
        adapter!!.setOnItemClickListener(object : BaseItemAdapter.OnItemClickListener{
            override fun onItemClick(itemList: StoreInfo) {
                parentFragmentManager.beginTransaction().apply{replace(R.id.main_frm, InformationFragment()).addToBackStack(null).commit()}
            }

        })
    }
    private fun initDummyData() {
        storeList.add(StoreInfo("test1", "test1111111"))
        storeList.add(StoreInfo("test2", "test22222222222"))
        storeList.add(StoreInfo("test3", "test333333333333333333"))
        storeList.add(StoreInfo("test4", "test4444444444444444444"))
    }
}