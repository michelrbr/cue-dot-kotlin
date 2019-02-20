package br.com.mxel.cuedot.domain.detail

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.Movie
import io.reactivex.Observable
import io.reactivex.Single

class GetMovieDetail(
        private val repository: IMovieDetailRepository
) {

    fun execute(movieId: Int): Observable<Event<Movie>> {

        return Observable.concat(Observable.just(Event.loading()), getMovieDetail(movieId))
    }

    private fun getMovieDetail(movieId: Int): Observable<Event<Movie>> {

        return if (movieId < 1) {
            Single.just(
                    Event.error<Movie>(DetailError.INVALID_ID)
            ).toObservable()
        } else {
            repository.getMovie(movieId).toObservable()
        }
    }
}