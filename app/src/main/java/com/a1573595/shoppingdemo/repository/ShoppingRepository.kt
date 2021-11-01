package com.a1573595.shoppingdemo.repository

import com.a1573595.shoppingdemo.api.NetWorkService
import com.a1573595.shoppingdemo.api.SubmitReq
import com.a1573595.shoppingdemo.api.SubmitRes
import com.a1573595.shoppingdemo.database.Cart
import io.reactivex.rxjava3.core.Single

class ShoppingRepository : IShoppingRepository {
    override fun buy(carts: List<Cart>): Single<SubmitRes> {
        val req = SubmitReq(carts = carts)

        return NetWorkService.instance.shoppingAPI.submit(req)
            .map { it.body() }
    }
}