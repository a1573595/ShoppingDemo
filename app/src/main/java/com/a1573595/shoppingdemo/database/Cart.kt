package com.a1573595.shoppingdemo.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.a1573595.shoppingdemo.data.Goods

@Entity(
    tableName = TABLE_CART,
    primaryKeys = ["id"],
    indices = [Index(value = ["id"], unique = true)]
)
data class Cart(
//    @PrimaryKey
//    val id: String,
    @Embedded
    val goods: Goods,
//    var name: String,
//    var price: Int,
//    var imgUrl: String,
    var quantity: Int
)