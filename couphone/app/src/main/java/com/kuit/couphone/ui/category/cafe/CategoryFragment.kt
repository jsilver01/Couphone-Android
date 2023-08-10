package com.kuit.couphone.ui.category.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kuit.couphone.BaseItemAdapter
import com.kuit.couphone.InformationActivity
import com.kuit.couphone.R
import com.kuit.couphone.data.ApiInterface
import com.kuit.couphone.data.BrandResponse
import com.kuit.couphone.data.BrandResult
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.data.getRetrofit
import com.kuit.couphone.data.user_token
import com.kuit.couphone.databinding.FragmentCategoryBinding
import com.kuit.couphone.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    var adapter : BaseItemAdapter?= null
    var storeList = ArrayList<BrandResult>()

    //    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://3.39.157.227:8080")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private val apiService = retrofit.create(ApiInterface::class.java)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        //initDummyData()


        fetchBrandData(1)
        binding.button1.setOnClickListener {
            fetchBrandData(1)  // Default (sorting 1)
        }

        binding.button2.setOnClickListener {
            fetchBrandData(2) // sorting 2
            adapter?.notifyDataSetChanged()
        }

        binding.button3.setOnClickListener {
            fetchBrandData(3) // sorting 3
            adapter?.notifyDataSetChanged()
        }

        binding.backIv.setOnClickListener {
            parentFragmentManager.findFragmentById(R.id.main_frm)
            parentFragmentManager.beginTransaction().apply{replace(R.id.main_frm, HomeFragment()).addToBackStack(null).commit()}
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BaseItemAdapter(storeList)
        binding.categoryListRv.adapter = adapter
        binding.categoryListRv.layoutManager = LinearLayoutManager(context)

        binding.categoryTv.text = "'카페'"
        adapter!!.setOnItemClickListener(object : BaseItemAdapter.OnItemClickListener{
            override fun onItemClick(itemList: StoreInfo) {
                val intent = Intent(requireContext(), InformationActivity::class.java)
                startActivity(intent)
            }
        })


    }

    private fun fetchBrandData(sortedBy: Int) {
        val service =  getRetrofit().create(ApiInterface::class.java)
        Log.d("token", "Bearer $user_token")
        service.getBrand("Bearer $user_token",1,null, sortedBy)
            .enqueue( object : retrofit2.Callback<BrandResponse>{
                override fun onResponse(
                    call: Call<BrandResponse>,
                    response: Response<BrandResponse>
                ) {
                    if(response.isSuccessful) {
                        val resp = response.body()

                        storeList.clear()
                        storeList = resp!!.result as ArrayList<BrandResult>
                        adapter = BaseItemAdapter(storeList)
                        binding.categoryListRv.adapter = adapter
                        binding.categoryListRv.layoutManager = LinearLayoutManager(context)
                        adapter!!.setOnItemClickListener(object : BaseItemAdapter.OnItemClickListener{
                            override fun onItemClick(itemList: StoreInfo) {
                                val intent = Intent(requireContext(), InformationActivity::class.java)
                                startActivity(intent)
                            }
                        })
                        adapter!!.notifyDataSetChanged()

                        //adapter?.updateData(resp?.result, sortedBy)
                        Log.d("BrandResponse", resp.toString())
                    }
                    else{
                        Log.d("BrandResponse", response.toString())
                    }
                }

                override fun onFailure(call: Call<BrandResponse>, t: Throwable) {
                    Log.d("BrandResponse",t.message.toString())
                }

            })
    }


    /*private fun initDummyData() {
        storeList.add(StoreInfo("test1", "test1111111"))
        storeList.add(StoreInfo("test2", "test22222222222"))
        storeList.add(StoreInfo("test3", "test333333333333333333"))
        storeList.add(StoreInfo("test4", "test4444444444444444444"))
    }*/

}