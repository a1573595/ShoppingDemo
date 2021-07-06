package com.a1573595.shoppingdemo.api

import com.google.gson.annotations.SerializedName

data class LoginRes(
    @SerializedName("isLoginSuccess")
    val isLoginSuccess:Boolean,
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("name")
    val name: String
)