package br.com.mxel.cuedot.domain.detail

import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Observable

class GetMovieDetail(
        private val repository: IMovieDetailRepository
) {

    fun execute(movieId: Long): Observable<State<Movie>> {

        return Observable.concat(Observable.just(State.loading()), getMovieDetail(movieId))
    }

    private fun getMovieDetail(movieId: Long): Observable<State<Movie>> {

        return if (movieId < 1) {
            Observable.just(State.error(DetailError.INVALID_ID))
        } else {
            repository.getMovie(movieId).toObservable()
        }
    }
}