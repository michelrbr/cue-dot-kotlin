package br.com.mxel.cuedot.dagger

import android.app.Application
import br.com.mxel.cuedot.CueDotApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<CueDotApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

}
