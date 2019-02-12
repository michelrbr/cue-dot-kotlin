package br.com.mxel.cuedot.presentation

import android.app.Application
import br.com.mxel.cuedot.BuildConfig
import timber.log.Timber

class CueDotApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}