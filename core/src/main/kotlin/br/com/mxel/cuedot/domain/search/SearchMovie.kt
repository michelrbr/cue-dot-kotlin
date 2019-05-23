package br.com.mxel.cuedot.domain.search

import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Observable

class SearchMovie(
        private val repository: ISearchRepository
) {

    fun execute(query: String): Observable<State<MovieList>> {

        return Observable.concat(Observable.just(State.loading()), search(query))
    }

    private fun search(query: String): Observable<State<MovieList>> {

        return if (query.isEmpty()) {
            Observable.just(State.error(SearchError.EMPTY_QUERY))
        } else {
            repository.searchMovie(query).toObservable()
        }
    }
}