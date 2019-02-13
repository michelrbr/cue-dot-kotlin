package br.com.mxel.cuedot.presentation.orderby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.SchedulerProvider
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.domain.orderby.GetMoviesOrderedBy
import br.com.mxel.cuedot.domain.orderby.Order
import br.com.mxel.cuedot.presentation.base.BaseViewModel
import io.reactivex.rxkotlin.addTo

class OrderedByViewModel(
    schedulerProvider: SchedulerProvider,
    private val getMoviesOrderedBy: GetMoviesOrderedBy
): BaseViewModel(schedulerProvider) {

    private var currentOrder: Order = Order.POPULAR
    private var currentPage: Int = 1
    private var totalPages: Int = 0
    private var totalResults: Int = 0

    private val _movies = MutableLiveData<Event<List<Movie>>>()
            .apply { value = Event.idle() }
    val movies: LiveData<Event<List<Movie>>>
        get() = _movies

    fun getMovies(order: Order, page: Int) {

        currentOrder = order
        currentPage = page
        getMoviesOrderedBy.execute(order, page)
                .subscribeOn(scheduler.backgroundThread)
                .map {
                    when (it) {
                        is Event.Data -> Event.data(it.data.movies ?: emptyList())
                        is Event.Idle -> Event.idle()
                        is Event.Loading -> Event.loading()
                        is Event.Error -> Event.error(it.error)
                    }
                }
                .observeOn(scheduler.mainThread)
                .subscribe {
                    _movies.value = it
                }
                .addTo(disposable)
    }
}