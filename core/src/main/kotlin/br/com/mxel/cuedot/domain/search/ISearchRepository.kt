package br.com.mxel.cuedot.domain.search

import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Single

interface ISearchRepository {

    fun searchMovie(query: String): Single<State<MovieList>>
}