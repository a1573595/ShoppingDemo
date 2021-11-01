package com.a1573595.shoppingdemo.repository

import com.a1573595.shoppingdemo.api.NetWorkService
import com.a1573595.shoppingdemo.api.LoginReq
import com.a1573595.shoppingdemo.api.LoginRes
import io.reactivex.rxjava3.core.Single

class MemberRepository : IMemberRepository {
    override fun login(account: String, password: String): Single<LoginRes> {
        val req = LoginReq(account, password)

        return NetWorkService.instance.memberAPI.login(req)
            .map { it.body() }
    }
}