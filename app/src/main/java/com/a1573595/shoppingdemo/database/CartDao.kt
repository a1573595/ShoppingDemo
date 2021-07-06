package com.a1573595.shoppingdemo.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart): Completable

    @Query("SELECT * FROM TABLE_CART")
    fun getAll(): Flowable<List<Cart>>

    @Query("SELECT * FROM TABLE_CART WHERE id LIKE :id")
    fun getByID(id: String): Single<List<Cart>>

    @Query("DELETE FROM TABLE_CART")
    fun deleteAll(): Completable

    @Query("DELETE FROM TABLE_CART WHERE id LIKE :id")
    fun deleteByID(id: String): Completable

    // in SQL since sum() of an empty table will return NULL and not 0
//    @Query("SELECT SUM(quantity) FROM TABLE_CART")
    @Query("SELECT TOTAL(quantity) FROM TABLE_CART")
    fun getSumOfQuantity(): Flowable<Int>

    @Update
    fun update(cart: Cart): Completable

    @Query("UPDATE TABLE_CART SET quantity = quantity + :quantity WHERE id = :id")
    fun update(id: String, quantity: Int): Completable

    fun insertOrUpdate(cart: Cart, quantity: Int): Completable {
        return getByID(cart.goods.id)
            .flatMapCompletable {
                if (it.isEmpty()) {
                    insert(cart)
                } else {
                    update(cart.goods.id, quantity)
                }
            }
    }
}