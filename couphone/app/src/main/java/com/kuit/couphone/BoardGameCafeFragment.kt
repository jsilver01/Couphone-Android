package com.kuit.couphone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.databinding.FragmentBaseBinding

class BoardGameCafeFragment : Fragment() {
    lateinit var binding : FragmentBaseBinding
    var adapter : BaseItemAdapter ?= null
    var storeList = ArrayList<StoreInfo>()
    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDummyData()
        adapter = BaseItemAdapter(storeList)
        binding.categoryListRv.adapter = adapter
        binding.categoryListRv.layoutManager = LinearLayoutManager(context)

    }

    private fun initDummyData() {
        storeList.add(StoreInfo("test1","test1111111"))
        storeList.add(StoreInfo("test2","test22222222222"))
        storeList.add(StoreInfo("test3","test333333333333333333"))
        storeList.add(StoreInfo("test4","test4444444444444444444"))
    }
}