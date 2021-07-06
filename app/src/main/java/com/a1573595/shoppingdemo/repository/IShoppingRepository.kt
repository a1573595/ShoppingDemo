package com.a1573595.shoppingdemo.repository

import com.a1573595.shoppingdemo.api.SubmitRes
import com.a1573595.shoppingdemo.database.Cart
import io.reactivex.Single

interface IShoppingRepository {
    fun buy(carts: List<Cart>): Single<SubmitRes>
}