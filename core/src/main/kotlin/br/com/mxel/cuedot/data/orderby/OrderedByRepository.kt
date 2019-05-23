package br.com.mxel.cuedot.data.orderby

import br.com.mxel.cuedot.data.orderby.remote.IOrderedByRemoteData
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.IOrderedByRepository
import br.com.mxel.cuedot.domain.orderby.Order
import io.reactivex.Single

class OrderedByRepository(
        private val remoteData: IOrderedByRemoteData
) : IOrderedByRepository {

    override fun getMoviesOrderedBy(orderBy: Order, page: Int): Single<State<MovieList>> {
        return remoteData.getMoviesOrderedBy(orderBy, page)
    }
}