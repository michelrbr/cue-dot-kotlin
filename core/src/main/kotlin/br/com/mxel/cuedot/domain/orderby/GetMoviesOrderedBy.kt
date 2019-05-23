package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Observable

class GetMoviesOrderedBy(
        private val repository: IOrderedByRepository
) {

    fun execute(orderBy: Order, page: Int): Observable<State<MovieList>> {

        return Observable.concat(Observable.just(State.loading()), getMoviesOrderedBy(orderBy, page))
    }

    private fun getMoviesOrderedBy(orderBy: Order, page: Int): Observable<State<MovieList>> {

        // This is the accepted rage of pages from API
        return if (page < 1 || page > 1000) {
            Observable.just(State.error(OrderByError.INVALID_PAGE))
        } else {
            repository.getMoviesOrderedBy(orderBy, page).toObservable()
        }
    }
}