package com.kuit.couphone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.SearchAdapter
import com.kuit.couphone.R
import com.kuit.couphone.SearchResultFragment
import com.kuit.couphone.data.SearchRoomDB.LocalSearchDB
import com.kuit.couphone.data.SearchRoomDB.LocalSearchEntity
import com.kuit.couphone.databinding.FragmentSearchBinding
import com.kuit.couphone.ui.home.HomeFragment
import java.util.Locale

class SearchFragment : Fragment() {

    private lateinit var searchItemList: ArrayList<LocalSearchEntity>
    private lateinit var filteredList: ArrayList<LocalSearchEntity>
    private lateinit var localSearchDB: LocalSearchDB
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        filteredList = ArrayList()
        searchItemList = ArrayList()
        localSearchDB = LocalSearchDB.getInstance(requireContext())!!
        searchItemList = localSearchDB.SearchKeywordDAO().getKeyWordList("temp",1) as ArrayList<LocalSearchEntity>
        adapter = SearchAdapter(searchItemList)

        binding.recyclerViewList.adapter = adapter
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(keyword: LocalSearchEntity) {
                val bundle = Bundle()
                bundle.putString("key", keyword.keyword)

                val passBundleBFragment = SearchResultFragment()
                passBundleBFragment.arguments = bundle
                parentFragmentManager.beginTransaction().replace(R.id.main_frm, passBundleBFragment).commit()
            }

            override fun onDeleteClick(keyword: LocalSearchEntity) {
                val localDao = LocalSearchDB.getInstance(requireContext())!!.SearchKeywordDAO()
                localDao.delete(keyword)
                searchItemList.remove(keyword)
                adapter.notifyDataSetChanged()
            }
        })

        binding.backIv.setOnClickListener {
            val homeFragment = com.kuit.couphone.ui.home.HomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, homeFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.submitBtn.setOnClickListener {
            val localDao = LocalSearchDB.getInstance(requireContext())!!.SearchKeywordDAO()
            val searchText = binding.searchEt.text.toString()

            if (localDao.getresultkeyword(searchText) == null) {
                localDao.insertSearchKeyword(
                    LocalSearchEntity(
                        1,
                        localDao.getCount(),
                        "temp",
                        searchText
                    )
                )
            }

            val bundle = Bundle()
            bundle.putString("key", searchText)
            val passBundleBFragment = SearchResultFragment()
            passBundleBFragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, passBundleBFragment).commit()
        }

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.submitBtn.callOnClick()
                true
            } else {
                false
            }
        }

        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                val searchText = binding.searchEt.text.toString()
                if (searchText.isEmpty()) {
                    adapter.filterList(searchItemList)
                } else {
                    searchFilter(searchText)
                }
            }

            override fun afterTextChanged(editable: Editable?) {}

        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = HomeFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, homeFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        })

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

        adapter.filterList(filteredList)
    }
}
