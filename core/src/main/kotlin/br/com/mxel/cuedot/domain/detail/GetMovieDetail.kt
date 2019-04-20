package br.com.mxel.cuedot.domain.detail

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Observable

class GetMovieDetail(
        private val repository: IMovieDetailRepository
) {

    fun execute(movieId: Long): Observable<Event<Movie>> {

        return Observable.concat(Observable.just(Event.loading()), getMovieDetail(movieId))
    }

    private fun getMovieDetail(movieId: Long): Observable<Event<Movie>> {

        return if (movieId < 1) {
            Observable.just(Event.error(DetailError.INVALID_ID))
        } else {
            repository.getMovie(movieId).toObservable()
        }
    }
}