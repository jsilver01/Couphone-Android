package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuit.couphone.R
import com.kuit.couphone.SearchItem

class SearchAdapter(private val searchItemList: ArrayList<SearchItem>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = searchItemList[position].word
    }

    override fun getItemCount(): Int {
        return searchItemList.size
    }

    fun filterList(filteredList: ArrayList<SearchItem>) {
        searchItemList.clear()
        searchItemList.addAll(filteredList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val word: TextView = itemView.findViewById(R.id.word)
    }


}
