package com.example.akiraito.codechallenge.di

import com.example.akiraito.codechallenge.BuildConfig
import com.example.akiraito.codechallenge.data.api.DiscoverApi
import com.example.akiraito.codechallenge.data.api.GenresApi
import com.example.akiraito.codechallenge.data.api.MovieApi
import com.example.akiraito.codechallenge.data.api.SearchApi
import com.example.akiraito.codechallenge.data.api.interceptor.ClientInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetModule {
    private const val TIME_OUT: Long = 30
    private const val RETROFIT_NAME = "codechallenge"

    fun getNetModule(): Module {
        return module {
            single(name = RETROFIT_NAME) {
                Retrofit.Builder()
                    .baseUrl(BuildConfig.REQUEST_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            single { (get(name = RETROFIT_NAME) as Retrofit).create(MovieApi::class.java) }
            single { (get(name = RETROFIT_NAME) as Retrofit).create(GenresApi::class.java) }
            single { (get(name = RETROFIT_NAME) as Retrofit).create(DiscoverApi::class.java) }
            single { (get(name = RETROFIT_NAME) as Retrofit).create(SearchApi::class.java) }
        }
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().also {
            it.followRedirects(true)
            it.followSslRedirects(true)
            it.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            it.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            it.readTimeout(TIME_OUT, TimeUnit.SECONDS)
            it.addInterceptor(ClientInterceptor())
            it.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }
}