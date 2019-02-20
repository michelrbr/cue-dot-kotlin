package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Observable
import io.reactivex.Single

class GetMoviesOrderedBy(
        private val repository: IOrderedByRepository
) {

    fun execute(orderBy: Order, page: Int): Observable<Event<MovieList>> {

        return Observable.concat(Observable.just(Event.loading()), getMoviesOrderedBy(orderBy, page))
    }

    private fun getMoviesOrderedBy(orderBy: Order, page: Int): Observable<Event<MovieList>> {

        return if (page < 1) {
            Single.just(
                    Event.error<MovieList>(OrderByError.INVALID_PAGE)
            ).toObservable()
        } else {
            repository.getMoviesOrderedBy(orderBy, page).toObservable()
        }
    }
}