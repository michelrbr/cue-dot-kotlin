package br.com.mxel.cuedot.presentation.orderby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.SchedulerProvider
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.GetMoviesOrderedBy
import br.com.mxel.cuedot.domain.orderby.Order
import br.com.mxel.cuedot.domain.orderby.OrderByError
import br.com.mxel.cuedot.presentation.base.BaseViewModel
import io.reactivex.rxkotlin.addTo

class OrderedByViewModel(
        schedulerProvider: SchedulerProvider,
        private val getMoviesOrderedBy: GetMoviesOrderedBy
) : BaseViewModel(schedulerProvider) {

    private var currentPage: Int = 1
    private var totalPages: Int = 1

    private val _currentOrder = MutableLiveData<Order>()
    val currentOrder: LiveData<Order>
        get() = _currentOrder

    private val _hasNextPage = MutableLiveData<Boolean>().apply { value = false }
    val hasNextPage: LiveData<Boolean>
        get() = _hasNextPage

    private val _movies = MutableLiveData<Event<List<Movie>>>().apply { value = Event.idle() }
    val movies: LiveData<Event<List<Movie>>>
        get() = _movies

    private val _error = MutableLiveData<Event.Error?>().apply { value = null }
    val error: LiveData<Event.Error?>
        get() = _error

    fun refresh() {
        if (_currentOrder.value != null) {
            getMovies(_currentOrder.value!!)
        } else {
            _error.value = Event.Error(OrderByError.EMPTY_ORDER)
        }
    }

    fun getMovies(order: Order) {

        if (_currentOrder.value != order) {
            _currentOrder.value = order
        }

        currentPage = 1
        getMoviesOrderedBy.execute(order, currentPage)
                .subscribeOn(scheduler.backgroundThread)
                .subscribe {
                    when (it) {
                        is Event.Data -> setupProperties(it)
                        is Event.Loading -> _movies.postValue(it)
                        is Event.Error -> _movies.postValue(it)
                    }
                    checkIfCanDispose(it)
                }.addTo(disposable)
    }

    fun loadMore() {
        if (currentPage < totalPages) {

            getMoviesOrderedBy.execute(_currentOrder.value!!, currentPage + 1)
                    .subscribeOn(scheduler.backgroundThread)
                    .map { event ->
                        (event as? Event.Data)?.data?.let {
                            val cList = (_movies.value as? Event.Data)?.data ?: emptyList()
                            val nList = it.movies ?: emptyList()

                            Event.data(it.copy(movies = cList + nList))
                        } ?: event
                    }.subscribe {
                        when (it) {
                            is Event.Data -> setupProperties(it)
                            is Event.Error -> _error.postValue(it)
                        }
                        checkIfCanDispose(it)
                    }.addTo(disposable)
        } else {
            _hasNextPage.value = false
        }
    }

    private fun setupProperties(event: Event.Data<MovieList>) {
        totalPages = event.data.totalPages
        currentPage = event.data.page
        _hasNextPage.postValue(currentPage < totalPages)
        _movies.postValue(Event.data(event.data.movies ?: emptyList()))
    }
}