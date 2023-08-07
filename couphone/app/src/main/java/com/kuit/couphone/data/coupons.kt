package com.kuit.couphone.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Coupon(
    @SerializedName("memberId") var memberId : Int,
    @SerializedName("brandId") var brandId : Int,
)

data class CouponResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : CouponResult,
)

data class CouponResult(
    @SerializedName("couponId") var couponId : Int,
    @SerializedName("createdDate") var createdDate : LocalDateTime,
)

data class CouponUseResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : CouponUseResult,
)

data class CouponUseResult(
    @SerializedName("couponId") var couponId : Int,
    @SerializedName("couponItemStatus") var couponItemStatus : String,
)

data class CouponGetResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : CouponGetResult,
)

data class CouponGetResult(
    @SerializedName("couponId") var couponId : Int,
    @SerializedName("stampCount") var stampCount : Int,
    @SerializedName("couponItemStatus") var couponItemStatus : String,
)