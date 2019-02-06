package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Observable

class GetMoviesOrderedBy(
        private val repository: IOrderedByRepository
) {

    fun execute(orderBy: Order, page: Int): Observable<Event<MovieList>> {
        return Observable.concat(Observable.just(Event.loading()), getMoviesOrderedBy(orderBy, page))
    }

    private fun getMoviesOrderedBy(orderBy: Order, page: Int): Observable<Event<MovieList>> {
        return repository.getMoviesOrderedBy(orderBy, page)
                .map { Event.data(it) }
                .onErrorReturn { Event.error(it) }
                .toObservable()
    }
}