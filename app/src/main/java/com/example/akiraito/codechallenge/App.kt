package com.example.akiraito.codechallenge

import android.app.Application
import com.example.akiraito.codechallenge.di.NetModule
import com.example.akiraito.codechallenge.di.PreferenceModule
import com.example.akiraito.codechallenge.di.RepositoryModule
import com.example.akiraito.codechallenge.di.UiModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            applicationContext,
            listOf(
                NetModule.getNetModule(),
                UiModule.getViewModelModule(),
                PreferenceModule.preferencesModule,
                RepositoryModule.getRepositoryModule()
            )
        )
    }
}