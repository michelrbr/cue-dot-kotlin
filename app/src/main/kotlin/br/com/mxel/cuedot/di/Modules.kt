package br.com.mxel.cuedot.di

import android.content.Context
import br.com.mxel.cuedot.BuildConfig
import br.com.mxel.cuedot.data.orderby.OrderedByRepository
import br.com.mxel.cuedot.data.orderby.remote.IOrderedByClient
import br.com.mxel.cuedot.data.orderby.remote.IOrderedByRemoteData
import br.com.mxel.cuedot.data.orderby.remote.OrderedByRemoteData
import br.com.mxel.cuedot.data.remote.MovieInterceptor
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.domain.SchedulerProvider
import br.com.mxel.cuedot.domain.orderby.GetMoviesOrderedBy
import br.com.mxel.cuedot.domain.orderby.IOrderedByRepository
import br.com.mxel.cuedot.presentation.orderby.OrderedByViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.singleBy
import java.io.File

const val IS_DEBUG = "isDebug"
const val API_KEY = "apiKey"

val appModule = module {

    single { SchedulerProvider(AndroidSchedulers.mainThread(), Schedulers.io()) }

    single(IS_DEBUG) { BuildConfig.DEBUG }

    single(API_KEY) { BuildConfig.API_KEY }
}

val networkModule = module {

    single {
        RemoteClientFactory(
                getCacheDir(androidContext()),
                MovieInterceptor(get(API_KEY)),
                get(IS_DEBUG)
        )
    }
}

val orderByModule = module {

    single { get<RemoteClientFactory>().createClient(IOrderedByClient::class) }

    singleBy<IOrderedByRemoteData, OrderedByRemoteData>()

    singleBy<IOrderedByRepository, OrderedByRepository>()

    factory<GetMoviesOrderedBy>()

    viewModel<OrderedByViewModel>()
}

private fun getCacheDir(context: Context?): File? = context?.cacheDir