package br.com.mxel.cuedot.data.orderby.remote

import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.Order
import io.reactivex.Single

interface IOrderedByRemoteData {

    fun getMoviesOrderedBy(orderBy: Order, page: Int): Single<Event<MovieList>>
}