package com.a1573595.shoppingdemo.page.goodsDetail

import androidx.lifecycle.MutableLiveData
import com.a1573595.shoppingdemo.BaseViewModel
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.repository.GoodsRepository
import com.a1573595.shoppingdemo.repository.IGoodsRepository
import com.a1573595.shoppingdemo.tool.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GoodsDetailViewModel(private val repository: IGoodsRepository = GoodsRepository()) :
    BaseViewModel() {
    var goods: MutableLiveData<Event<Goods>> = MutableLiveData()

    var count: MutableLiveData<Int> = MutableLiveData()
//    var count: LiveData<Int>

    init {
//        val flowable = repository.fetchCartCount()
//        count = LiveDataReactiveStreams.fromPublisher(flowable)

        addDisposable(repository.fetchCartCount()
            .subscribeOn(Schedulers.io())
            .subscribe { count.postValue(it) })
    }

    fun addGoodsToCart(quantity: Int, complete: (Unit) -> Unit) {
        goods.value?.peekContent()?.let {
            addDisposable(
                repository.addGoodsToCart(it, quantity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { complete.invoke(Unit) }
                    .subscribe()
            )
        }
    }
}