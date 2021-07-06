package com.a1573595.shoppingdemo.api

import com.a1573595.shoppingdemo.database.Cart
import com.google.gson.annotations.SerializedName

data class SubmitReq(
    @SerializedName("id")
    val id: String = "aaa",
    @SerializedName("password")
    val password: String = "bbb",
    @SerializedName("carts")
    val carts: List<Cart>,
)