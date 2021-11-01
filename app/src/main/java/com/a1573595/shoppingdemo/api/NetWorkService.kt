package com.a1573595.shoppingdemo.api

import com.a1573595.shoppingdemo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
        val logger = HttpLoggingInterceptor()
        logger.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConfig.WEB_HOST)
            .client(client)
            .build()

        memberAPI = retrofit.create(MemberApi::class.java)
        shoppingAPI = retrofit.create(ShoppingAPI::class.java)
    }
}