package br.com.mxel.cuedot.presentation.orderby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.SchedulerProvider
import br.com.mxel.cuedot.domain.entity.Movie
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.GetMoviesOrderedBy
import br.com.mxel.cuedot.domain.orderby.Order
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

    val refreshLoading = MutableLiveData<Boolean>().apply { value = false }

    val hasNextPage = MutableLiveData<Boolean>().apply { value = true }

    val movies = MutableLiveData<ArrayList<Movie>>().apply { value = ArrayList() }

    val error = MutableLiveData<Event.Error?>().apply { value = null }

    fun getMovies(order: Order) {

        _currentOrder.value = order
        currentPage = 1
        getMoviesOrderedBy.execute(order, currentPage)
                .subscribeOn(scheduler.backgroundThread)
                .observeOn(scheduler.mainThread)
                .subscribe {
                    when (it) {
                        is Event.Data -> {
                            totalPages = it.data.totalPages
                            hasNextPage.value = (currentPage < totalPages)
                            movies.value = ArrayList(it.data.movies ?: emptyList())
                            refreshLoading.value = false
                        }
                        is Event.Loading -> refreshLoading.value = true
                        is Event.Error -> error.value = it
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
                            //it.data.movies = movies.value.apply { this.addAll(it.data.movies) }
                        }
                        it
                    }
                    .observeOn(scheduler.mainThread)
                    .subscribe {
                        when (it) {
                            is Event.Data -> {
                                hasNextPage.value = (currentPage < totalPages)
                                movies.value = ArrayList(it.data.movies ?: emptyList())
                            }
                            is Event.Error -> error.value = it
                        }
                        checkIfCanDispose(it)
                    }
                    .addTo(disposable)
        } else {
            hasNextPage.value = false
        }
    }
}