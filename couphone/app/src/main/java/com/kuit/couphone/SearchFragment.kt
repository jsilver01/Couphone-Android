package com.example.stickode4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.SearchAdapter
import com.kuit.couphone.R
import com.kuit.couphone.SearchResultFragment
import com.kuit.couphone.data.LocalSearchDB
import com.kuit.couphone.data.LocalSearchEntity
import com.kuit.couphone.databinding.FragmentSearchBinding
import java.util.Locale

class SearchFragment : Fragment() {

    private lateinit var searchItemList: ArrayList<LocalSearchEntity>
    private lateinit var filteredList: ArrayList<LocalSearchEntity>
    var adapter : SearchAdapter?= null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var localSearchDB: LocalSearchDB
    lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        filteredList = ArrayList()
        searchItemList = ArrayList()
        linearLayoutManager = LinearLayoutManager(requireContext())
        localSearchDB = LocalSearchDB.getInstance(requireContext())!!
        searchItemList = localSearchDB.SearchKeywordDAO().getKeyWordList("temp") as ArrayList<LocalSearchEntity>
        adapter = SearchAdapter(searchItemList)
        binding.recyclerViewList.adapter = adapter
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        Log.d("dbupdateeeeeeeeeee",searchItemList.toString())
        adapter!!.setOnItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onItemClick(keyword: LocalSearchEntity) {
                val bundle = Bundle()
                bundle.putString("key", keyword.keyword)

                val passBundleBFragment = SearchResultFragment()
                passBundleBFragment.arguments = bundle
                parentFragmentManager.beginTransaction().replace(R.id.main_frm, passBundleBFragment).commit()
            }

        })
        binding.submitBtn.setOnClickListener {
            //유효성검사

            val localDao = LocalSearchDB.getInstance(requireContext())!!.SearchKeywordDAO()
            if(localDao.getresultkeyword(binding.searchBar.text.toString())==null) {
                localDao.insertSearchKeyword(
                    LocalSearchEntity(
                        localDao.getCount(),
                        "temp",
                        binding.searchBar.text.toString()
                    )
                )
            }
            //검색화면 이동
            val bundle = Bundle()
            bundle.putString("key", binding.searchBar.text.toString())
            val passBundleBFragment = SearchResultFragment()
            passBundleBFragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, passBundleBFragment).commit()
            Log.d("dbupdateeeeeeeeeee","업데이트완룓ㄷㄷㄷㄷㄷㄷㄷㄷㄷ")
        }
        binding.searchBar.setOnEditorActionListener(getEditorActionListener(binding.submitBtn))
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                val searchText = binding.searchBar.text.toString()
                if (searchText.isEmpty()) {
                    adapter!!.filterList(searchItemList)
                } else {
                    searchFilter(searchText)
                }
            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })
        adapter!!.notifyDataSetChanged()
        return binding.root
    }

    private fun searchFilter(searchText: String) {
        filteredList.clear()

        if (searchText.isEmpty()) {
            filteredList.addAll(searchItemList)
        } else {
            for (item in searchItemList) {
                if (item.keyword.toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(Locale.getDefault()))) {
                    filteredList.add(item)
                }
            }
        }

        adapter!!.filterList(filteredList)
    }
    fun getEditorActionListener(view: View): TextView.OnEditorActionListener { // 키보드에서 done(완료) 클릭 시 , 원하는 뷰 클릭되게 하는 메소드
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.callOnClick()
            }
            false
        }
    }
}
