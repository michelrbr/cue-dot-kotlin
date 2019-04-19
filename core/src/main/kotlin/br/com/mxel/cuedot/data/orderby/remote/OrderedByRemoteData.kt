package br.com.mxel.cuedot.data.orderby.remote

import br.com.mxel.cuedot.data.entity.remote.ErrorApi
import br.com.mxel.cuedot.data.remote.ApiProvider
import br.com.mxel.cuedot.data.remote.RemoteError
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.entity.MovieList
import br.com.mxel.cuedot.domain.orderby.Order
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class OrderedByRemoteData(
        private val client: IOrderedByClient
) : IOrderedByRemoteData {

    private val apiVersion = ApiProvider.API_VERSION

    private val moshi by lazy { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    override fun getMoviesOrderedBy(orderBy: Order, page: Int): Single<Event<MovieList>> {

        return client.getMoviesOrderedBy(apiVersion, parseOrder(orderBy), page.toString())
                .map { Event.data(it.toMovieList()) }
                .onErrorReturn {
                    when (it) {
                        is UnknownHostException -> Event.error(RemoteError.CONNECTION_LOST)
                        is HttpException -> Event.error(RemoteError.fromErrorApi(parseError(it)), it)
                        else -> Event.error()
                    }
                }
    }

    private fun parseError(error: HttpException): ErrorApi? {

        val json = error.response().errorBody()?.string() ?: ""

        if (json.isBlank()) {
            return null
        }

        return try {
            moshi.adapter<ErrorApi>(ErrorApi::class.java)
                    .fromJson(json)
        } catch (error: IOException) {
            null
        }
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