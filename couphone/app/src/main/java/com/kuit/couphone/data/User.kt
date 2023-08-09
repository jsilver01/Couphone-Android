package com.kuit.couphone.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email") var email : String,
    @SerializedName("name") var name : String,
    @SerializedName("role") var role : String,
)
data class UserResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : UserResult,
)

data class UserResult(
    @SerializedName("accessToken") var accessToken : String,
    @SerializedName("tokenType") var tokenType : String,
    @SerializedName("memberId") var memberId : Int,
    @SerializedName("grade") var grade : String,
    @SerializedName("memberLabel") var memberLabel : String,
)

data class UserInfoResponse(
    @SerializedName("code") var code : Int,
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("result") var result : UserInfoResult,
)
data class UserInfoResult(
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("email") var email : String,
    @SerializedName("memberGrade") var memberGrade : String,
    @SerializedName("memberStatus") var memberStatus : String,
)
data class UserForm(
    @SerializedName("phoneNumber") var phoneNumber : String,
    @SerializedName("pinNumber") var pinNumber : String,
)
data class UserFormResult(
    @SerializedName("id") var id : Int,
)
