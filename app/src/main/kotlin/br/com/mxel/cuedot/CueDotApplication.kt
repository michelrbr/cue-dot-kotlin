package br.com.mxel.cuedot

import android.app.Application
import br.com.mxel.cuedot.dagger.DaggerAppComponent
import br.com.mxel.cuedot.koin.appModule
import br.com.mxel.cuedot.koin.networkModule
import br.com.mxel.cuedot.koin.orderByModule
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import javax.inject.Inject

class CueDotApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

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
                        orderByModule
                )
        )

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}
