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

    private val _refreshLoading = MutableLiveData<Boolean>().apply { value = false }
    val refreshLoading: LiveData<Boolean>
        get() = _refreshLoading

    private val _hasNextPage = MutableLiveData<Boolean>().apply { value = false }
    val hasNextPage: LiveData<Boolean>
        get() = _hasNextPage

    private val _movies = MutableLiveData<ArrayList<Movie>>().apply { value = ArrayList() }
    val movies: LiveData<ArrayList<Movie>>
        get() = _movies

    private val _error = MutableLiveData<Event.Error?>().apply { value = null }
    val error: LiveData<Event.Error?>
        get() = _error

    fun refresh() {
        if (_currentOrder.value != null) {
            _refreshLoading.value = true
            getMovies(_currentOrder.value!!)
        } else {
            _refreshLoading.value = false
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
                .observeOn(scheduler.mainThread)
                .subscribe {

                    _refreshLoading.value = it.isLoading()

                    when (it) {
                        is Event.Data -> {
                            totalPages = it.data.totalPages
                            currentPage = it.data.page
                            if (_error.value != null) {
                                _error.value = null
                            }
                            _hasNextPage.value = (currentPage < totalPages)
                            _movies.value = ArrayList(it.data.movies ?: emptyList())
                        }
                        is Event.Error -> _error.value = it
                    }
                    checkIfCanDispose(it)
                }
                .addTo(disposable)
    }

    fun loadMore() {
        if (currentPage < totalPages) {

            currentPage++
            getMoviesOrderedBy.execute(_currentOrder.value!!, currentPage)
                    .subscribeOn(scheduler.backgroundThread)
                    .map {
                        if (it is Event.Data) {
                            val list = movies.value.apply {
                                this?.addAll(it.data.movies ?: emptyList())
                            }
                            return@map Event.data(MovieList(it.data.page, it.data.totalResults, it.data.totalPages, list))
                        }
                        it
                    }
                    .observeOn(scheduler.mainThread)
                    .subscribe {
                        when (it) {
                            is Event.Data -> {
                                totalPages = it.data.totalPages
                                currentPage = it.data.page
                                if (_error.value != null) {
                                    _error.value = null
                                }
                                _hasNextPage.value = (currentPage < totalPages)
                                _movies.value = ArrayList(it.data.movies ?: emptyList())
                            }
                            is Event.Error -> _error.value = it
                        }
                        checkIfCanDispose(it)
                    }
                    .addTo(disposable)
        } else {
            _hasNextPage.value = false
        }
    }
}