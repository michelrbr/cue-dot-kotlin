package br.com.mxel.cuedot.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mxel.cuedot.domain.detail.GetMovieDetail
import kotlinx.coroutines.CoroutineDispatcher

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(
    private val dispatcher: CoroutineDispatcher,
    private val getMovieDetail: GetMovieDetail
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MovieDetailViewModel(dispatcher, getMovieDetail) as T
}
