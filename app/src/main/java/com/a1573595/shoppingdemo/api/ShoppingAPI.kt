package com.a1573595.shoppingdemo.api

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ShoppingAPI {
    @POST("2c9fcbc6-440b-425e-8b96-e470e42962cf")
    fun submit(@Body request: SubmitReq): Single<Response<SubmitRes>>
}