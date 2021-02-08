package br.com.mxel.cuedot.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.detail.GetMovieDetail
import br.com.mxel.cuedot.domain.entity.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitLast

class MovieDetailViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val getMovieDetail: GetMovieDetail
) : ViewModel() {

    private var currentJob: Job? = null

    private val _movie = MutableLiveData<State<Movie>>().apply { value = State.idle() }
    val movie: LiveData<State<Movie>>
        get() = _movie

    fun getMovieDetail(movieId: Long) {

        currentJob?.takeIf { it.isActive }?.cancel()

        currentJob = viewModelScope.launch(dispatcher) {
            val movie = getMovieDetail.execute(movieId).awaitLast()

            _movie.postValue(movie)
        }
    }

    override fun onCleared() {
        currentJob?.takeIf { it.isActive }?.cancel()
        super.onCleared()
    }
}
