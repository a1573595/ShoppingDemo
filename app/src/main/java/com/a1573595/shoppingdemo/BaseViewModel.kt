package com.a1573595.shoppingdemo

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a1573595.shoppingdemo.tool.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    private val disposable = CompositeDisposable()

    var toastResource: MutableLiveData<Event<Int>> = MutableLiveData()
    var toastText: MutableLiveData<Event<String>> = MutableLiveData()

    var isLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()

    protected open fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

    protected open fun deleteDisposable(d: Disposable) {
        disposable.delete(d)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}