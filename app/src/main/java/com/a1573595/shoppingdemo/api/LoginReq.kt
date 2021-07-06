package com.a1573595.shoppingdemo.api

import com.google.gson.annotations.SerializedName

data class LoginReq(
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String
)