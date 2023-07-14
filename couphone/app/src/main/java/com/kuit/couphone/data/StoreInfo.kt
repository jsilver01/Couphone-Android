package com.kuit.couphone.data

import com.google.gson.annotations.SerializedName

data class StoreInfo(
    @SerializedName("store_name")val store_name : String,
    @SerializedName("store_couphone_info")val store_couphone_info : String
)
