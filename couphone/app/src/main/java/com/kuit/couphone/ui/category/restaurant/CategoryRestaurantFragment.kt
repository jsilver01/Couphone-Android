package com.kuit.couphone.ui.category.restaurant

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.*
import com.kuit.couphone.data.LocalSearchEntity
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.databinding.FragmentCategoryBinding
import com.kuit.couphone.ui.home.HomeFragment

class CategoryRestaurantFragment : Fragment() {

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
        binding.categoryTv.text = "'식당'"
        adapter!!.setOnItemClickListener(object : BaseItemAdapter.OnItemClickListener{
            override fun onItemClick(itemList: StoreInfo) {
                val intent = Intent(requireContext(), InformationActivity::class.java)
                startActivity(intent)        }

        })
    }
    private fun initDummyData() {
        storeList.add(StoreInfo("test1", "test1111111"))
        storeList.add(StoreInfo("test2", "test22222222222"))
        storeList.add(StoreInfo("test3", "test333333333333333333"))
        storeList.add(StoreInfo("test4", "test4444444444444444444"))
    }
}