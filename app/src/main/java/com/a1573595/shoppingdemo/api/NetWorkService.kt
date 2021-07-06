package com.a1573595.shoppingdemo.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    const val WEB_HOST = "https://run.mocky.io/v3/"
}

class NetWorkService private constructor() {
    companion object {
        val instance: NetWorkService by lazy { NetWorkService() }
    }

    var memberAPI: MemberApi
    var shoppingAPI: ShoppingAPI

    init {
        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConfig.WEB_HOST)
            .client(client)
            .build()

        memberAPI = retrofit.create(MemberApi::class.java)
        shoppingAPI = retrofit.create(ShoppingAPI::class.java)
    }
}