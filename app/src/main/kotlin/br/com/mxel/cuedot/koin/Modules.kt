package br.com.mxel.cuedot.koin

import android.content.Context
import br.com.mxel.cuedot.BuildConfig
import br.com.mxel.cuedot.data.detail.MovieDetailRepository
import br.com.mxel.cuedot.data.detail.remote.IMovieDetailClient
import br.com.mxel.cuedot.data.detail.remote.IMovieDetailRemoteData
import br.com.mxel.cuedot.data.detail.remote.MovieDetailRemoteData
import br.com.mxel.cuedot.data.orderby.OrderedByRepository
import br.com.mxel.cuedot.data.orderby.remote.IOrderedByClient
import br.com.mxel.cuedot.data.orderby.remote.IOrderedByRemoteData
import br.com.mxel.cuedot.data.orderby.remote.OrderedByRemoteData
import br.com.mxel.cuedot.data.remote.MovieInterceptor
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.detail.presentation.DetailViewModel
import br.com.mxel.cuedot.domain.SchedulerProvider
import br.com.mxel.cuedot.domain.detail.GetMovieDetail
import br.com.mxel.cuedot.domain.detail.IMovieDetailRepository
import br.com.mxel.cuedot.domain.orderby.GetMoviesOrderedBy
import br.com.mxel.cuedot.domain.orderby.IOrderedByRepository
import br.com.mxel.cuedot.presentation.orderby.OrderedByViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
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

val detailModule = module {

    single { get<RemoteClientFactory>().createClient(IMovieDetailClient::class) }

    singleBy<IMovieDetailRemoteData, MovieDetailRemoteData>()

    singleBy<IMovieDetailRepository, MovieDetailRepository>()

    factory<GetMovieDetail>()

    single { Dispatchers.IO }

    viewModel<DetailViewModel>()
}

private fun getCacheDir(context: Context?): File? = context?.cacheDir
