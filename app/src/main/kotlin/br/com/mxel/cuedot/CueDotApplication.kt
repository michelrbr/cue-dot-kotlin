package br.com.mxel.cuedot

import android.app.Application
import br.com.mxel.cuedot.di.appModule
import br.com.mxel.cuedot.di.detailModule
import br.com.mxel.cuedot.di.networkModule
import br.com.mxel.cuedot.di.orderByModule
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class CueDotApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin(
                applicationContext,
                listOf(
                        appModule,
                        networkModule,
                        orderByModule,
                        detailModule
                )
        )
    }
}