package com.a1573595.shoppingdemo.api

import com.google.gson.annotations.SerializedName

data class SubmitRes(
    @SerializedName("isLoginSuccess")
    val isSubmitSuccess:Boolean,
)