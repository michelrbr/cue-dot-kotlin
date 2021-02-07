import br.com.mxel.cuedot.data.detail.remote.TestMovieDetailInterceptor
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.domain.SchedulerProvider
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.module

const val IS_DEBUG = "isDebug"

val testAppModule = module {

    single { SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()) }

    single(IS_DEBUG) { true }
}

val testDetailNetworkModule = module {

    single {
        RemoteClientFactory(
                null,
                TestMovieDetailInterceptor(),
                get(IS_DEBUG)
        )
    }
}
