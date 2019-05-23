package br.com.mxel.cuedot.data.orderby.remote

import br.com.mxel.cuedot.data.remote.ApiProvider
import br.com.mxel.cuedot.data.remote.mapToState
import br.com.mxel.cuedot.domain.State
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.Order
import io.reactivex.Single

class OrderedByRemoteData(
        private val client: IOrderedByClient
) : IOrderedByRemoteData {

    private val apiVersion = ApiProvider.API_VERSION

    override fun getMoviesOrderedBy(orderBy: Order, page: Int): Single<State<MovieList>> {

        return client.getMoviesOrderedBy(apiVersion, parseOrder(orderBy), page.toString())
                .mapToState()
    }

    private fun parseOrder(orderBy: Order): String {
        return when (orderBy) {
            Order.POPULAR -> "popular"
            Order.TOP_RATED -> "top_rated"
            Order.NOW_PLAYING -> "now_playing"
            Order.UPCOMING -> "upcoming"
        }
    }
}