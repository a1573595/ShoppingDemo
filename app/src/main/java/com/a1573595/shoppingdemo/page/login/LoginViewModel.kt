package com.a1573595.shoppingdemo.page.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.a1573595.shoppingdemo.BaseViewModel
import com.a1573595.shoppingdemo.R
import com.a1573595.shoppingdemo.repository.IMemberRepository
import com.a1573595.shoppingdemo.repository.MemberRepository
import com.a1573595.shoppingdemo.tool.Event
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val repository: IMemberRepository = MemberRepository()) :
    BaseViewModel() {
    var accountError: MutableLiveData<Event<Int>> = MutableLiveData()
    var passwordError: MutableLiveData<Event<Int>> = MutableLiveData()

    val loginSuccess: LiveData<Boolean> = Transformations.map(toastResource) {
        it.peekContent() == R.string.login_success
    }

    fun login(account: String, password: String) {
        when {
            account.isBlank() -> {
                accountError.postValue(Event(R.string.account_cannot_be_empty))
                toastResource.postValue(Event(R.string.account_cannot_be_empty))
            }
            password.isBlank() -> {
                passwordError.postValue(Event(R.string.account_cannot_be_empty))
                toastResource.postValue(Event(R.string.password_cannot_be_empty))
            }
            else -> {
                isLoading.postValue(Event(true))

                addDisposable(repository.login(account, password)
                    .subscribeOn(Schedulers.io())
                    .doFinally { isLoading.postValue(Event(false)) }
                    .subscribe({
                        if (it.isLoginSuccess) {
                            toastResource.postValue(Event(R.string.login_success))
                        } else {
                            toastResource.postValue(Event(R.string.login_failed))
                        }
                    }, {
                        toastResource.postValue(Event(R.string.server_error))
                    }))
            }
        }
    }
}