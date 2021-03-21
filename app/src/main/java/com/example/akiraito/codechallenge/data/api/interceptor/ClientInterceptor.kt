package com.example.akiraito.codechallenge.data.api.interceptor

import com.example.akiraito.codechallenge.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class ClientInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url: HttpUrl =
            request.url.newBuilder().addQueryParameter(API_KEY, BuildConfig.API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY = "api_key"
    }
}