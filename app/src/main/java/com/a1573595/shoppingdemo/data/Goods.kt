package com.a1573595.shoppingdemo.data

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Goods(
//    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("imgUrl")
    var imgUrl: String,
    @SerializedName("description")
    var description: String
) : Serializable