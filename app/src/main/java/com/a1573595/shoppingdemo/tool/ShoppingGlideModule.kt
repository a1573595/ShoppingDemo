package com.a1573595.shoppingdemo.tool

import android.content.Context
import com.a1573595.shoppingdemo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import java.io.InputStream

@GlideModule
class ShoppingGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(2f)
            .build()

        builder
            .setDiskCache(ExternalPreferredCacheDiskCacheFactory(context))
            .setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
            .setDefaultRequestOptions(
                RequestOptions()
//                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .placeholder(R.drawable.progress_animation)
                    .error(android.R.drawable.ic_menu_report_image)
                    .dontAnimate()
                    .format(DecodeFormat.PREFER_RGB_565)
            )
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }

    override fun isManifestParsingEnabled(): Boolean = false
}