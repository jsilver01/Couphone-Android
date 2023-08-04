package com.kuit.couphone

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuit.couphone.data.LocalSearchEntity
import com.kuit.couphone.databinding.ItemSearchBinding

class SearchAdapter(private val searchItemList: ArrayList<LocalSearchEntity>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(keyword: LocalSearchEntity)
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = searchItemList[position].keyword
        holder.bind(searchItemList[position])
    }

    override fun getItemCount(): Int {
        return searchItemList.size
    }

    fun filterList(filteredList: ArrayList<LocalSearchEntity>) {
        searchItemList.clear()
        searchItemList.addAll(filteredList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        val word: TextView = binding.word
        fun bind(keyword: LocalSearchEntity){
            binding.itemCl.setOnClickListener{
                itemClickListener.onItemClick(keyword)
            }

        }
    }


}
