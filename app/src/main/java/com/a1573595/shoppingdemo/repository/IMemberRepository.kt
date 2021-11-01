package com.a1573595.shoppingdemo.repository

import com.a1573595.shoppingdemo.api.LoginRes
import io.reactivex.rxjava3.core.Single

interface IMemberRepository {
    fun login(account: String, password: String) : Single<LoginRes>
}