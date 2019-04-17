import br.com.mxel.cuedot.data.orderby.remote.TestOrderByInterceptor
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.di.IS_DEBUG
import br.com.mxel.cuedot.domain.SchedulerProvider
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.module


val testAppModule = module {

    single { SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()) }

    single(IS_DEBUG) { true }
}

val testNetworkModule = module {

    single {
        RemoteClientFactory(
                null,
                TestOrderByInterceptor(),
                get(IS_DEBUG)
        )
    }
}