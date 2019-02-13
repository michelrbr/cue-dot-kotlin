package br.com.mxel.cuedot

import android.app.Application
import br.com.mxel.cuedot.di.appModule
import br.com.mxel.cuedot.di.networkModule
import br.com.mxel.cuedot.di.orderByModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class CueDotApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin(
                applicationContext,
                listOf(
                        appModule,
                        networkModule,
                        orderByModule
                )
        )
    }
}