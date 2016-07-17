package com.backkoms.stock.util

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by test on 2016/7/16.
 */

object HttpUtil {
    private val client: OkHttpClient

    init {
        client = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(4, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool())
                .build()
    }

    @Throws(IOException::class)
    fun getSync(url: String): Response {
        var request = Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }

    @Throws(IOException::class)
    fun getAsync(url: String, callback: Callback) {
        var request = Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}


