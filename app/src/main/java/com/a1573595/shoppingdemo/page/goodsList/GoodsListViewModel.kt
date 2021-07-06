package com.a1573595.shoppingdemo.page.goodsList

import androidx.collection.SparseArrayCompat
import androidx.lifecycle.*
import com.a1573595.shoppingdemo.BaseViewModel
import com.a1573595.shoppingdemo.data.Goods
import com.a1573595.shoppingdemo.database.DBHelper
import com.a1573595.shoppingdemo.repository.GoodsRepository
import com.a1573595.shoppingdemo.repository.IGoodsRepository
import com.a1573595.shoppingdemo.tool.Event
import io.reactivex.schedulers.Schedulers

class GoodsListViewModel(val repository: IGoodsRepository = GoodsRepository()) : BaseViewModel() {
    var count: MutableLiveData<Int> = MutableLiveData()
//    lateinit var count: LiveData<Int>

    var goodsListArray: MutableLiveData<Event<SparseArrayCompat<List<Goods>>>> = MutableLiveData()

    init {
//        val flowable = repository.fetchCartCount()
//        count = LiveDataReactiveStreams.fromPublisher(flowable)

        addDisposable(repository.fetchCartCount()
            .subscribeOn(Schedulers.io())
            .subscribe { count.postValue(it) })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun fetchGoods() {
        isLoading.postValue(Event(true))

        repository.fetchGoods {
            goodsListArray.postValue(Event(it))
            isLoading.postValue(Event(false))
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun closeDataBase() {
        DBHelper.instance.close()
    }
}