package com.kuit.couphone.data

import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("name") var name : String,
    @SerializedName("rewardDescription") var rewardDescription : String,
    @SerializedName("brandImageUrl") var brandImageUrl : String,
    @SerializedName("categoryId") var categoryId : Int,
)

data class BrandRegisterResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : BrandRegisterResult,
)

data class BrandRegisterResult(
    @SerializedName("id") var id : Int,
)

data class BrandResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : List<BrandResult>,
)

data class BrandResult(
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("rewardDescription") var rewardDescription : String,
    @SerializedName("brandImageUrl") var brandImageUrl : String,
    @SerializedName("stampCount") var stampCount : Int,
    @SerializedName("createdDate") var createdDate : String,
)

data class BrandDetailedResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : BrandDetailedResult,
)

data class BrandDetailedResult(
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("rewardDescription") var rewardDescription : String,
    @SerializedName("brandImageUrl") var brandImageUrl : String,
    @SerializedName("couponList") var couponList : BrandDetailedCouponList,
)

data class BrandDetailedCouponList(
    @SerializedName("id") var id : Int,
    @SerializedName("stampCount") var stampCount : Int,
    @SerializedName("status") var status : String,
    @SerializedName("createdDate") var createdDate : String,
    @SerializedName("modifiedDate") var modifiedDate : String,
)