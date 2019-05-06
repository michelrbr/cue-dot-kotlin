package br.com.mxel.cuedot.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.SchedulerProvider
import br.com.mxel.cuedot.domain.detail.GetMovieDetail
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.presentation.base.BaseViewModel
import io.reactivex.rxkotlin.addTo

class DetailViewModel(
        schedulerProvider: SchedulerProvider,
        private val getMovieDetail: GetMovieDetail
) : BaseViewModel(schedulerProvider) {

    private val _movie = MutableLiveData<Event<Movie>>().apply { value = Event.idle() }
    val movie: LiveData<Event<Movie>>
        get() = _movie

    fun getMovieDetail(movieId: Long) {

        getMovieDetail.execute(movieId)
                .subscribeOn(scheduler.backgroundThread)
                .subscribe { _movie.postValue(it) }
                .addTo(disposable)
    }
}