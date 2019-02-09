package br.com.mxel.cuedot.domain.search

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Observable
import io.reactivex.Single

class SearchMovie(
        private val repository: ISearchRepository
) {

    fun execute(query: String): Observable<Event<MovieList>> {

        return Observable.concat(Observable.just(Event.loading()), search(query))
    }

    private fun search(query: String): Observable<Event<MovieList>> {

        return if (query.isEmpty()) {
            Single.just(
                    Event.error<MovieList>(IllegalArgumentException("Query shouldn't be empty"))
            ).toObservable()
        } else {
            repository.searchMovie(query)
                    .map { Event.data(it) }
                    .onErrorReturn { Event.error(it) }
                    .toObservable()
        }
    }
}