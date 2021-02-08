package br.com.mxel.cuedot.dagger

import br.com.mxel.cuedot.detail.di.MovieDetailsModule
import br.com.mxel.cuedot.detail.presentation.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MovieDetailsModule::class])
    abstract fun contributeMovieDetailActivity(): MovieDetailActivity
}
