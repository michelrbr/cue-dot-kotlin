package br.com.mxel.cuedot.detail.di

import androidx.lifecycle.ViewModelProvider
import br.com.mxel.cuedot.data.detail.MovieDetailRepository
import br.com.mxel.cuedot.data.detail.remote.IMovieDetailClient
import br.com.mxel.cuedot.data.detail.remote.IMovieDetailRemoteData
import br.com.mxel.cuedot.data.detail.remote.MovieDetailRemoteData
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import br.com.mxel.cuedot.detail.presentation.MovieDetailActivity
import br.com.mxel.cuedot.detail.presentation.MovieDetailViewModel
import br.com.mxel.cuedot.detail.presentation.MovieDetailViewModelFactory
import br.com.mxel.cuedot.domain.detail.GetMovieDetail
import br.com.mxel.cuedot.domain.detail.IMovieDetailRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object MovieDetailsModule {

    @Provides
    fun provideIMovieDetailClient(
        remoteClientFactory: RemoteClientFactory
    ): IMovieDetailClient = remoteClientFactory.createClient(IMovieDetailClient::class)

    @Provides
    fun provideIMovieDetailRemoteData(
        client: IMovieDetailClient
    ) : IMovieDetailRemoteData = MovieDetailRemoteData(client)

    @Provides
    fun provideIMovieDetailRepository(
        remote: IMovieDetailRemoteData
    ) : IMovieDetailRepository = MovieDetailRepository(remote)

    @Provides
    fun provideGetMovieDetail(
        repository: IMovieDetailRepository
    ) = GetMovieDetail(repository)

    @Provides
    fun provideMovieDetailViewModel(
        owner: MovieDetailActivity,
        factory: MovieDetailViewModelFactory
    ) = ViewModelProvider(owner, factory).get(MovieDetailViewModel::class.java)

    @Provides
    fun provideMovieDetailViewModelFactory(
        dispatcher: CoroutineDispatcher,
        getMovieDetail: GetMovieDetail
    ) = MovieDetailViewModelFactory(dispatcher, getMovieDetail)

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO
}


