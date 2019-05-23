package br.com.mxel.cuedot.domain.orderby

import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.MovieList
import io.reactivex.Single

interface IOrderedByRepository {

    fun getMoviesOrderedBy(orderBy: Order, page: Int): Single<State<MovieList>>
}