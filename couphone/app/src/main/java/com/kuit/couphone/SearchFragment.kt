package com.example.stickode4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.SearchAdapter
import com.kuit.couphone.R
import com.kuit.couphone.SearchItem
import java.util.Locale

class SearchFragment : Fragment() {

    private lateinit var searchItemList: ArrayList<SearchItem>
    private lateinit var filteredList: ArrayList<SearchItem>
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchET: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recyclerView_list)
        searchET = view.findViewById(R.id.searchBar)

        filteredList = ArrayList()
        searchItemList = ArrayList()

        searchAdapter = SearchAdapter(searchItemList)
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = searchAdapter

        searchItemList.add(SearchItem("apple"))
        searchItemList.add(SearchItem("banana"))
        searchItemList.add(SearchItem("candy"))
        searchItemList.add(SearchItem("hello"))
        searchItemList.add(SearchItem("line"))
        searchItemList.add(SearchItem("cup"))
        searchItemList.add(SearchItem("pumpkin"))
        searchItemList.add(SearchItem("king"))
        searchItemList.add(SearchItem("coffee"))
        searchItemList.add(SearchItem("fight"))
        searchItemList.add(SearchItem("lemon"))
        searchItemList.add(SearchItem("dance"))
        searchItemList.add(SearchItem("xml"))
        searchItemList.add(SearchItem("queen"))
        searchItemList.add(SearchItem("zebra"))
        searchItemList.add(SearchItem("captain"))
        searchItemList.add(SearchItem("summer"))
        searchItemList.add(SearchItem("meeting"))
        searchItemList.add(SearchItem("notebook"))
        searchItemList.add(SearchItem("condition"))

        searchAdapter.notifyDataSetChanged()

        searchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                val searchText = searchET.text.toString()
                if (searchText.isEmpty()) {
                    searchAdapter.filterList(searchItemList)
                } else {
                    searchFilter(searchText)
                }
            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })

        return view
    }

    private fun searchFilter(searchText: String) {
        filteredList.clear()

        if (searchText.isEmpty()) {
            filteredList.addAll(searchItemList)
        } else {
            for (item in searchItemList) {
                if (item.word.toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(Locale.getDefault()))) {
                    filteredList.add(item)
                }
            }
        }

        searchAdapter.filterList(filteredList)
    }
}
