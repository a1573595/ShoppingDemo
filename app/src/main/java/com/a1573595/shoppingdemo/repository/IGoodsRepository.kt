package com.a1573595.shoppingdemo.repository

import androidx.collection.SparseArrayCompat
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.database.Cart
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface IGoodsRepository {
    fun fetchGoods(complete: (SparseArrayCompat<List<Goods>>) -> Unit)

    fun addGoodsToCart(goods: Goods, quantity: Int): Completable

    fun fetchCartCount(): Flowable<Int>

    fun fetchCart(): Flowable<List<Cart>>

    fun deleteAllGoods(): Completable

    fun deleteGoods(id: String): Completable

    fun updateGoods(cart: Cart): Completable
}