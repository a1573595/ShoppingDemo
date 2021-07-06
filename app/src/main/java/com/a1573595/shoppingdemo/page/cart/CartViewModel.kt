package com.a1573595.shoppingdemo.page.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.a1573595.shoppingdemo.BaseViewModel
import com.a1573595.shoppingdemo.R
import com.a1573595.shoppingdemo.database.Cart
import com.a1573595.shoppingdemo.repository.GoodsRepository
import com.a1573595.shoppingdemo.repository.IGoodsRepository
import com.a1573595.shoppingdemo.repository.IShoppingRepository
import com.a1573595.shoppingdemo.repository.ShoppingRepository
import com.a1573595.shoppingdemo.tool.Event
import io.reactivex.schedulers.Schedulers

class CartViewModel(
    private val goodsRepository: IGoodsRepository = GoodsRepository(),
    private val shoppingRepository: IShoppingRepository = ShoppingRepository()
) : BaseViewModel() {
    var carts: MutableLiveData<List<Cart>> = MutableLiveData()

    var hasData: LiveData<Boolean> = Transformations.map(carts) {
        it.isNotEmpty()
    }

    init {
        addDisposable(goodsRepository.fetchCart()
            .subscribeOn(Schedulers.io())
            .subscribe { carts.postValue(it) })
    }

    fun reduceQuantity(index: Int) {
        carts.value?.get(index)?.let {
            it.quantity--

            if (it.quantity > 0) {
                updateGoods(it)
            } else {
                deleteGoods(it.goods.id)
            }
        }
    }

    fun increaseQuantity(index: Int) {
        carts.value?.get(index)?.let {
            it.quantity++

            updateGoods(it)
        }
    }

    fun submitCart() {
        carts.value?.let { it ->
            isLoading.postValue(Event(true))
            shoppingRepository.buy(it)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.isSubmitSuccess) {
                        clearCart()
                    } else {
                        toastResource.postValue(Event(R.string.server_error))
                    }
                }, {
                    Log.e("test", it.toString())
                    isLoading.postValue(Event(false))
                    toastResource.postValue(Event(R.string.server_error))
                })
        }
    }

    private fun updateGoods(cart: Cart) {
        addDisposable(
            goodsRepository.updateGoods(cart)
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    private fun deleteGoods(id: String) {
        addDisposable(goodsRepository.deleteGoods(id)
            .subscribeOn(Schedulers.io())
            .doOnComplete { toastResource.postValue(Event(R.string.delete_success)) }
            .subscribe())
    }

    private fun clearCart() {
        addDisposable(goodsRepository.deleteAllGoods()
            .subscribeOn(Schedulers.io())
            .doFinally { isLoading.postValue(Event(false)) }
            .doOnComplete { toastResource.postValue(Event(R.string.submit_success)) }
            .subscribe())
    }
}