package com.kuit.couphone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.databinding.FragmentSearchResultBinding
import com.kuit.couphone.ui.home.HomeFragment

class SearchResultFragment : Fragment() {
    lateinit var binding : FragmentSearchResultBinding
    var adapter : BaseItemAdapter?= null
    var storeList = ArrayList<StoreInfo>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchResultBinding.inflate(inflater,container,false)
        var result = requireArguments().getString("key")
        initDummyData()
        adapter = BaseItemAdapter(storeList)
        binding.categoryListRv.adapter = adapter
        binding.categoryListRv.layoutManager = LinearLayoutManager(context)
        Log.d("test1234",result.toString())
        binding.categoryTv.text = result
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIv.setOnClickListener {
            val homeFragment = com.kuit.couphone.ui.home.HomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, homeFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
    private fun initDummyData() {
        storeList.add(StoreInfo("test1", "test1111111"))
        storeList.add(StoreInfo("test2", "test22222222222"))
        storeList.add(StoreInfo("test3", "test333333333333333333"))
        storeList.add(StoreInfo("test4", "test4444444444444444444"))
    }
}