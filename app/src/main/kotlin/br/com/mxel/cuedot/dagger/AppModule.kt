package br.com.mxel.cuedot.dagger

import android.app.Application
import br.com.mxel.cuedot.BuildConfig
import br.com.mxel.cuedot.data.remote.MovieInterceptor
import br.com.mxel.cuedot.data.remote.RemoteClientFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import java.io.File

@Module
object AppModule {

    @Provides
    fun provideCacheDir(application: Application): File = application.cacheDir

    @Provides
    fun provideInterceptor(): Interceptor = MovieInterceptor(BuildConfig.API_KEY)

    @Provides
    fun provideRemoteClientFactory(
        cacheDir: File?,
        interceptor: Interceptor?,
    ) = RemoteClientFactory(
        cacheDir,
        interceptor,
        true
    )
}
