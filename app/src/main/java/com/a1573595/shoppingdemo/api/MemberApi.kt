package com.a1573595.shoppingdemo.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberApi {
    @POST("2c9fcbc6-440b-425e-8b96-e470e42962cf")
    fun login(@Body request: LoginReq): Single<Response<LoginRes>>
}
