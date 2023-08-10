package com.kuit.couphone.data

import com.google.gson.annotations.SerializedName

data class StoreInfo(
    @SerializedName("store_name")val store_name : String,
    @SerializedName("store_couphone_info")val store_couphone_info : String
)
data class StoreResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : List<StoreResult>,
)
data class StoreResult(
    @SerializedName("store_id") var store_id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("brand_id") var brand_id : Int,
    @SerializedName("getBrandResponse") var getBrandResponse : GetBrandResponse,
    @SerializedName("distance") var distance : Number,
    @SerializedName("address") var address : String,
    @SerializedName("longitude") var longitude : Number,
    @SerializedName("latitude") var latitude : Number,
)
data class GetBrandResponse(
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("rewardDescription") var rewardDescription : String,
    @SerializedName("brandImageUrl") var brandImageUrl : String,
    @SerializedName("stampCount") var stampCount : Int,
    @SerializedName("createdDate") var createdDate : String,
)
