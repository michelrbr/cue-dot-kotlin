package br.com.mxel.cuedot.domain.search

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Observable

class SearchMovie(
        private val repository: ISearchRepository
) {

    fun execute(query: String): Observable<Event<MovieList>> {

        return Observable.concat(Observable.just(Event.loading()), search(query))
    }

    private fun search(query: String): Observable<Event<MovieList>> {

        return if (query.isEmpty()) {
            Observable.just(Event.error(SearchError.EMPTY_QUERY))
        } else {
            repository.searchMovie(query).toObservable()
        }
    }
}